package ru.nesterov.clientanalyzer.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nesterov.clientanalyzer.models.TypeOfChange;

import java.util.Date;

@Repository
@RequiredArgsConstructor
public class ClientAnalyzerDaoImpl implements ClientAnalyzerDao{
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public int getCountOfSuccessfulMeetings() {
        String sql = "SELECT SUM(FLOOR(DATEDIFF(NOW(), date_of_beginning) / 7) * " +
                "count_of_meetings_pr_week) AS weeks_passed_total_meetings " +
                "FROM client;";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int getCountOfSuccessfulMeetings(long clientId) {
        String sql = "SELECT FLOOR(timestampdiff(DAY, CURRENT_TIMESTAMP, date_of_beginning) / 7) * " +
                "count_of_meetings_pr_week AS total_meetings " +
                "FROM client " +
                "WHERE client.id  = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, clientId);
    }

    public int getCountOfSuccessfulMeetings(long clientId, Date dateFrom, Date dateTo) {

        String sqlCountOfTotalMeetings = "SELECT ROUND(TIMESTAMPDIFF(day, :dateFrom, :dateTo) / 7) * count_of_meetings_pr_week" +
                "        FROM client c" +
                "        WHERE c.id = :clientId";

        String sqlCountOfUnsuccessfulMeetings = "SELECT count(sc.id) " +
                "         FROM schedule_change sc " +
                "                  INNER JOIN type_of_change toc ON sc.type_of_change_id = toc.id " +
                "         WHERE toc.name = 'CANCELLED' " +
                "           AND :dateFrom < sc.date " +
                "           AND sc.date < :dateTo " +
                "           AND sc.client_id = :clientId";
        String sqlCountOfSuccessfulMeetings = "SELECT (" + sqlCountOfTotalMeetings + ") - (" + sqlCountOfUnsuccessfulMeetings + ") as difference;";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("clientId", clientId)
                .addValue("dateTo", dateTo)
                .addValue("dateFrom", dateFrom);

        return namedParameterJdbcTemplate.queryForObject(sqlCountOfSuccessfulMeetings, parameterSource, Integer.class);
    }

    public int getCountOfUnplannedShifts(long clientId, Date dateFrom, Date dateTo) {
        return getCountOfChanges(TypeOfChange.SHIFTED, false, clientId, dateFrom, dateTo);
    }

    public int getCountOfPlannedShifts(long clientId, Date dateFrom, Date dateTo) {
        return getCountOfChanges(TypeOfChange.SHIFTED, true, clientId, dateFrom, dateTo);
    }

    private int getCountOfChanges(TypeOfChange typeOfChange, boolean isPlanned, long clientId, Date dateFrom, Date dateTo) {
        String sql = "SELECT count(sc.id) from schedule_change sc inner join" +
                " type_of_change toc on sc.type_of_change_id = toc.id" +
                " where sc.date > ? and sc.date < ? and toc.name = ? and sc.planned=? and sc.client_id = ?";
        return jdbcTemplate.queryForObject(sql,
                Integer.class, dateFrom, dateTo, typeOfChange.name(), isPlanned, clientId);
    }

    public int getCountOfUnplannedCancels(long clientId, Date dateFrom, Date dateTo) {
        return getCountOfChanges(TypeOfChange.CANCELLED, false, clientId, dateFrom, dateTo);
    }

    public int getCountOfPlannedCancels(long clientId, Date dateFrom, Date dateTo) {
        return getCountOfChanges(TypeOfChange.CANCELLED, true, clientId, dateFrom, dateTo);
    }

    public double getAverageCancellationsPerMonth(long clientId, Date dateFrom, Date dateTo) {
        return getAverageChangesPerMonth(TypeOfChange.CANCELLED, clientId, dateFrom, dateTo);
    }

    public double getAverageShiftsPerMonth(long clientId, Date dateFrom, Date dateTo) {
        return getAverageChangesPerMonth(TypeOfChange.SHIFTED, clientId, dateFrom, dateTo);
    }

    private double getAverageChangesPerMonth(TypeOfChange typeOfChange, long clientId, Date dateFrom, Date dateTo) {
        String sql = "SELECT COUNT(sc.id) / TIMESTAMPDIFF(MONTH, ?, ?) " +
                "FROM schedule_change sc left join client c on sc.client_id = c.id" +
                " inner join type_of_change toc on sc.type_of_change_id = toc.id WHERE client_id = ? and toc.name = ?;";
        return jdbcTemplate.queryForObject(sql,
                Double.class, dateFrom, dateTo, clientId, typeOfChange.name());
    }

    public int getExpectedIncoming(long clientId, Date dateFrom, Date dateTo) {
        String sql = "SELECT cost_per_hour * count_of_hours_pr_week * FLOOR(TIMESTAMPDIFF(DAY, ?, ?) / 7) " +
                "FROM client WHERE id = ?;";
        return jdbcTemplate.queryForObject(sql, Integer.class, dateFrom, dateTo, clientId);
    }

    public int getActualIncoming(long clientId, Date dateFrom, Date dateTo) {
        String sql = "SELECT cost_per_hour * count_of_hours_pr_week * FLOOR(TIMESTAMPDIFF(DAY, ?, ?)/7) - count(query.client_id) * cost_per_hour" +
                "                FROM client c join schedule_change sc on c.id = sc.client_id, (select sc.client_id from schedule_change sc inner join type_of_change toc" +
                "                on sc.type_of_change_id = toc.id" +
                "                where sc.client_id = ? and toc.name = 'CANCELLED') as query" +
                "                WHERE c.id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class , dateFrom, dateTo, clientId, clientId);
    }

    public int getAverageLosses(long clientId, Date dateTo) {
        String sql = "SELECT c.cost_per_hour * COUNT(sc.id)/ TIMESTAMPDIFF(month, c.date_of_beginning, ?) " +
                " FROM schedule_change sc left join client c on sc.client_id = c.id " +
                "                inner join type_of_change toc on sc.type_of_change_id = toc.id " +
                "                WHERE c.id = ? and toc.name = 'CANCELLED';";
        return jdbcTemplate.queryForObject(sql, Integer.class, dateTo, clientId);
    }

    public int getActualLosses(long clientId, Date dateFrom, Date dateTo) {
        String sql = "SELECT c.cost_per_hour*count(sc.id) from client c left join schedule_change sc " +
                "on c.id = sc.client_id " +
                "inner join type_of_change toc on sc.type_of_change_id = toc.id " +
                "where toc.name = 'CANCELLED' and c.id = ? and sc.date between ? and ?";
        return jdbcTemplate.queryForObject(sql,
                Integer.class, clientId, dateFrom, dateTo);
    }

    public double getCancellationsPercentage(long clientId, Date dateFrom, Date dateTo) {
        return getChangesPercentage(TypeOfChange.CANCELLED, clientId, dateFrom, dateTo);
    }

    public double getShiftsPercentage(long clientId, Date dateFrom, Date dateTo) {
        return getChangesPercentage(TypeOfChange.SHIFTED, clientId, dateFrom, dateTo);
    }

    private double getChangesPercentage(TypeOfChange type, long clientId, Date dateFrom, Date dateTo) {
        String changesCount = "select count(sc.id) from schedule_change sc inner join type_of_change toc on sc.type_of_change_id = toc.id " +
                "        where sc.date between ? and ? and toc.name = ? and sc.client_id = ?";
        String meetingsCount = "SELECT c.count_of_meetings_pr_week * FLOOR(abs(TIMESTAMPDIFF(day, ?, ?))/7) FROM client c WHERE c.id = ?";
        String sql = "SELECT 100 * (" + changesCount + ")/(" + meetingsCount + ")";

        return jdbcTemplate.queryForObject(sql,
                Double.class, dateFrom, dateTo, type.name(), clientId, dateTo, dateFrom, clientId);
    }
}
