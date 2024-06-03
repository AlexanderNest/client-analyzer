package ru.nesterov.clientanalyzer.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.stereotype.Repository;
import ru.nesterov.clientanalyzer.models.TypeOfChange;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Repository
@RequiredArgsConstructor
public class ClientAnalyzerDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public int getCountOfSuccessfulMeetings() {
        return jdbcTemplate.queryForObject("SELECT SUM(FLOOR(DATEDIFF(NOW(), date_of_beginning) / 7) * " +
                "count_of_meetings_pr_week) AS weeks_passed_total_meetings " +
                "FROM client;", Integer.class);
    }

    public int getCountOfSuccessfulMeetings(long clientId) {
        return jdbcTemplate.queryForObject("SELECT FLOOR(DATEDIFF('DAY', CURRENT_TIMESTAMP, date_of_beginning) / 7) * " +
                "count_of_meetings_pr_week AS total_meetings " +
                "FROM client " +
                "WHERE client.id  = ?", Integer.class, clientId);
    }

    public int getCountOfSuccessfulMeetings(long clientId, Date dateFrom, Date dateTo) {

        String sqlCountOfTotalMeetings = "SELECT ROUND(TIMESTAMPDIFF(day, :dateFrom, :dateTo) / 7 * count_of_meetings_pr_week)" +
                "                     FROM client c" +
                "                      WHERE c.id = :clientId";

        String sqlCountOfUnsuccessfulMeetings = "SELECT count(sc.id) " +
                "         FROM schedule_change sc " +
                "                  INNER JOIN type_of_change toc ON sc.type_of_change_id = toc.id " +
                "         WHERE toc.name = 'CANCELLED' " +
                "           AND :dateFrom < sc.date " +
                "           AND sc.date < :dateTo " +
                "           AND sc.id = :clientId";
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
        return jdbcTemplate.queryForObject("SELECT count(sc.id) from schedule_change sc inner join" +
                        " type_of_change toc on sc.type_of_change_id = toc.id" +
                        " where sc.date > ? and sc.date < ? and toc.name = ? and sc.planned=? and sc.client_id = ?",
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

    RowMapper<String> rm = new RowMapper<String>() {
        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("kek");
        }
    };

    private double getAverageChangesPerMonth(TypeOfChange typeOfChange, long clientId, Date dateFrom, Date dateTo) {
        return jdbcTemplate.queryForObject("SELECT COUNT(sc.id) / TIMESTAMPDIFF(MONTH, ?, ?) " +
                        "FROM schedule_change sc left join client c on sc.client_id = c.id" +
                " inner join type_of_change toc on sc.type_of_change_id = toc.id WHERE client_id = ? and toc.name = ?;",
                Double.class, dateFrom, dateTo, clientId, typeOfChange.name());
    }


    public int getExpectedIncoming(long clientId, Date dateFrom, Date dateTo) {
        return jdbcTemplate.queryForObject("SELECT cost_per_hour * count_of_hours_pr_week * FLOOR(TIMESTAMPDIFF('DAY', ?, ?)) / 7 " +
                "FROM client WHERE id = ?", ResultSet::getInt, dateTo, dateFrom, clientId);
    }

    public int getActualIncoming(long clientId, Date dateFrom, Date dateTo) {
        return jdbcTemplate.queryForObject("SELECT cost_per_hour * count_of_hours_pr_week * FLOOR" +
                "(TIMESTAMPDIFF('DAY', ?, ?))/7 - count(query.client_id) * cost_per_hour " +
                "FROM client join schedule_change sc on client.id = sc.client_id, (select client_id from schedule_change inner join type_of_change toc " +
                "    on type_of_change_id = toc.id" +
                "     where client_id = ? and toc.name = 'CANCELLED') as query " +
                "WHERE client_id = ?", ResultSet::getInt, dateTo, dateFrom, clientId, clientId);
    }

    public int getAverageLosses(long clientId) {
        return jdbcTemplate.queryForObject("SELECT c.cost_per_hour*SUM(sc.id)/ TIMESTAMPDIFF('month', c.date_of_beginning, " +
                "CURRENT_TIMESTAMP FROM schedule_change sc left join client c on sc.client_id = c.id " +
                "                inner join type_of_change toc on sc.type_of_change_id = toc.id " +
                "                WHERE client_id = ? and toc.name = 'CANCELLED';", ResultSet::getInt, clientId);
    }

    public int getActualLosses(long clientId, Date dateFrom, Date dateTo) {
        return jdbcTemplate.queryForObject("SELECT c.cost_per_hour*count(sc.id) from client c left join schedule_change sc " +
                "on c.id = sc.client_id " +
                "inner join type_of_change toc on sc.type_of_change_id = toc.id " +
                "where toc.name = 'CANCELLED' and c.id = ? and sc.date between ? and ?",
                ResultSet::getInt, clientId, dateFrom, dateTo);
    }

    public int getCancellationsPercentage(long clientId, Date dateFrom, Date dateTo) {
        return getChangesPercentage(TypeOfChange.CANCELLED, clientId, dateFrom, dateTo);
    }

    public int getShiftsPercentage(long clientId, Date dateFrom, Date dateTo) {
        return getChangesPercentage(TypeOfChange.SHIFTED, clientId, dateFrom, dateTo);
    }

    private int getChangesPercentage(TypeOfChange type, long clientId, Date dateFrom, Date dateTo) {
        return jdbcTemplate.queryForObject("SELECT 100 * (count(query.id)) / (c.count_of_meetings_pr_week * FLOOR(abs(TIMESTAMPDIFF('day', ?, ?))) / 7) " +
                "from (select sc.id from schedule_change sc inner join type_of_change toc on sc.type_of_change_id = toc.id " +
                "where sc.date between ? and ? and toc.name = ? and sc.client_id = ?) as query, client c " +
                "where c.id = ?", ResultSet::getInt, dateFrom, dateTo, dateFrom, dateTo, type.name(), clientId, clientId);
    }


}
