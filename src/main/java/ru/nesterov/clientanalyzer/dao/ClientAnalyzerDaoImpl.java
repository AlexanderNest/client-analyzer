package ru.nesterov.clientanalyzer.dao;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nesterov.clientanalyzer.models.TypeOfChange;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Repository
@RequiredArgsConstructor
public class ClientAnalyzerDaoImpl implements ClientAnalyzerDao {
    private static final Logger logger = LoggerFactory.getLogger(ClientAnalyzerDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public int getAllClientsIncoming(Date dateFrom, Date dateTo) {
        String getAllClientsExpectedIncoming = "(SELECT SUM(cost_per_hour * count_of_hours_pr_week * FLOOR(TIMESTAMPDIFF(DAY, ?, ?) / 7))" +
                "                FROM client)";
        String getAllClientsLosses = "COALESCE(" +
                "                       (SELECT SUM(cost_per_hour)" +
                "                        FROM client" +
                "                                 INNER JOIN schedule_change" +
                "                                            ON client.id = schedule_change.client_id" +
                "                                 INNER JOIN type_of_change" +
                "                                            ON schedule_change.type_of_change_id = type_of_change.id" +
                "                        WHERE date BETWEEN ? AND ?" +
                "                          AND type_of_change.name = 'CANCELLED'" +
                "                       )," +
                "                       0)";
        String sql = "SELECT (" + getAllClientsExpectedIncoming + " - " + getAllClientsLosses + ");";

        return jdbcTemplate.queryForObject(sql, Integer.class, dateFrom, dateTo, dateFrom, dateTo);
    }

    public double getSuccessfulMeetingsPercentage(Date dateFrom, Date dateTo) {
        String plannedMeetings = "SELECT SUM(ROUND(TIMESTAMPDIFF(day, ?, ?) / 7) * count_of_meetings_pr_week) FROM client";
        return (double) getCountOfSuccessfulMeetings(dateFrom, dateTo)/jdbcTemplate.queryForObject(plannedMeetings, Integer.class, dateFrom, dateTo) * 100;
    }

    public String getMostFrequentChangeDay(TypeOfChange typeOfChange) {
//        String sql = "SELECT DATE_FORMAT(sc.date, '%Y-%m-%d') AS cancellation_day " +
//                "FROM schedule_change sc " +
//                "         JOIN type_of_change tc ON sc.type_of_change_id = tc.id " +
//                "WHERE tc.name = ? " +
//                "GROUP BY DATE_FORMAT(sc.date, '%Y-%m-%d') " +
//                "ORDER BY COUNT(sc.client_id) DESC " +
//                "LIMIT 1;";
            String sql = "SELECT sc.date AS cancellation_day " +
                    "FROM schedule_change sc " +
                    "         JOIN type_of_change tc ON sc.type_of_change_id = tc.id " +
                    "WHERE tc.name = ? " +
                    "GROUP BY cancellation_day " +
                    "ORDER BY COUNT(sc.client_id) DESC " +
                    "LIMIT 1";
        return jdbcTemplate.queryForObject(sql, String.class, typeOfChange.name());
    }

    public String getMostFrequentCancellationDay() {
        return getMostFrequentChangeDay(TypeOfChange.CANCELLED);
    }

    public String getMostFrequentShiftDay() {
        return getMostFrequentChangeDay(TypeOfChange.SHIFTED);
    }

    public String getMostFrequentChangeMonth(TypeOfChange typeOfChange) {
        String sql = "SELECT MONTH(sc.date) AS cancellation_month " +
                "FROM schedule_change sc " +
                "         JOIN type_of_change tc ON sc.type_of_change_id = tc.id " +
                "WHERE tc.name = ? " +
                "GROUP BY MONTH(sc.date) " +
                "ORDER BY COUNT(sc.client_id) DESC " +
                "LIMIT 1;";
        return getMonthName(jdbcTemplate.queryForObject(sql, Integer.class, typeOfChange.name()));
    }

    public String getMonthName(int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month - 1);
        return cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH).toUpperCase();
    }

    public String getMostFrequentCancellationMonth() {
        return getMostFrequentChangeMonth(TypeOfChange.CANCELLED);
    }

    public String getMostFrequentShiftMonth() {
        return getMostFrequentChangeMonth(TypeOfChange.SHIFTED);
    }

    public int getCountOfSuccessfulMeetings(Date dateFrom, Date dateTo) {
        String plannedMeetings = "SELECT SUM(ROUND(TIMESTAMPDIFF(day, ?, ?) / 7) * count_of_meetings_pr_week) FROM client";
        String cancelledMeetings = "SELECT COUNT(sc.id) FROM schedule_change sc INNER JOIN type_of_change toc ON sc.type_of_change_id = toc.id WHERE toc.name = 'CANCELLED' and sc.date between ? and ?";
        String sql = "SELECT (" + plannedMeetings + ") - (" + cancelledMeetings + ")";

        return jdbcTemplate.queryForObject(sql, Integer.class, dateFrom, dateTo, dateFrom, dateTo);
    }

    public int getCountOfSuccessfulMeetings(long clientId) {
        String plannedMeetings = "SELECT SUM(ROUND(TIMESTAMPDIFF(day, date_of_beginning, current_timestamp) / 7) * count_of_meetings_pr_week) FROM client where client.id = ?";
        String cancelledMeetings = "SELECT COUNT(sc.id) FROM schedule_change sc INNER JOIN type_of_change toc ON sc.type_of_change_id = toc.id WHERE toc.name = 'CANCELLED' and sc.client_id = ?";
        String sql = "SELECT (" + plannedMeetings + ") - (" + cancelledMeetings + ")";
        return jdbcTemplate.queryForObject(sql, Integer.class, clientId, clientId);
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
        String getLosses = "SELECT COALESCE(" +
                "            (SELECT cost_per_hour * count(client_id)" +
                "             FROM client " +
                "                      INNER JOIN schedule_change " +
                "                                 ON client.id = schedule_change.client_id " +
                "                      INNER JOIN type_of_change " +
                "                                 ON schedule_change.type_of_change_id = type_of_change.id " +
                "             WHERE date BETWEEN ? AND ?" +
                "               AND client_id = ?" +
                "               AND type_of_change.name = 'CANCELLED' " +
                "            )," +
                "                0)";
        return getExpectedIncoming(clientId, dateFrom, dateTo) - jdbcTemplate.queryForObject(getLosses, Integer.class , dateFrom, dateTo, clientId);
    }

    public int getAverageLosses(long clientId, Date dateTo) {
        String sql = "SELECT COALESCE ((SELECT c.cost_per_hour * COUNT(sc.id)/ TIMESTAMPDIFF(month, c.date_of_beginning, ?) " +
                " FROM schedule_change sc left join client c on sc.client_id = c.id " +
                "                inner join type_of_change toc on sc.type_of_change_id = toc.id " +
                "                WHERE c.id = ? and toc.name = 'CANCELLED'), 0);";
        return jdbcTemplate.queryForObject(sql, Integer.class, dateTo, clientId);
    }

    public int getActualLosses(long clientId, Date dateFrom, Date dateTo) {
        String sql = "SELECT COALESCE((SELECT c.cost_per_hour*count(sc.id) from client c left join schedule_change sc " +
                "on c.id = sc.client_id " +
                "inner join type_of_change toc on sc.type_of_change_id = toc.id " +
                "where toc.name = 'CANCELLED' and c.id = ? and sc.date between ? and ?), 0)";
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
