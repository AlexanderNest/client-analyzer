package ru.nesterov.clientanalyzer.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Date;

@Repository
@RequiredArgsConstructor
public class ClientAnalyzerDao {
    private final JdbcTemplate jdbcTemplate;
    public int getCountOfSuccessfulMeetings() {
        return jdbcTemplate.queryForObject("SELECT SUM(FLOOR(DATEDIFF(NOW(), date_of_beginning) / 7) * " +
                "count_of_meetings_pr_week) AS weeks_passed_total_meetings " +
                "FROM client;", ResultSet::getInt);
    }

    public int getCountOfSuccessfulMeetings(long clientId) {
        return jdbcTemplate.queryForObject("SELECT FLOOR(DATEDIFF(NOW(), date_of_beginning) / 7) * " +
                "count_of_meetings_pr_week AS total_meetings\n" +
                "FROM client\n" +
                "WHERE client.id  = ?", ResultSet::getInt, clientId);
    }

    public int getCountOfSuccessfulMeetings(long clientId, Date dateFrom, Date dateTo) {
        return jdbcTemplate.queryForObject("SELECT FLOOR(DATEDIFF(?, ?) / 7) * count_of_meetings_pr_week AS " +
                "total_meetings " +
                "FROM client WHERE client.id  = ?", ResultSet::getInt, dateTo, dateFrom, clientId);
    }

    public int getCountOfUnplannedShifts(long clientId, Date dateFrom, Date dateTo) {
        return jdbcTemplate.queryForObject("SELECT count(sc.id) from schedule_change sc inner join" +
                " client_analyzer.type_of_change toc  on sc .type_of_change_id = toc.id" +
                " where sc.date > ? and sc.date < ? and toc.name = 'SHIFTED' and not sc.planned and sc.client_id = ?",
                ResultSet::getInt, dateFrom, dateTo,  clientId);
    }

    public int getCountOfPlannedShifts(long clientId, Date dateFrom, Date dateTo) {
        return jdbcTemplate.queryForObject("SELECT count(sc.id) from schedule_change sc inner join" +
                        " client_analyzer.type_of_change toc  on sc .type_of_change_id = toc.id" +
                        " where sc.date > ? and sc.date < ? and toc.name = 'SHIFTED' and sc.planned and sc.client_id = ?",
                ResultSet::getInt, dateFrom, dateTo,  clientId);
    }

    public int getCountOfUnplannedCancels(long clientId, Date dateFrom, Date dateTo) {
        return jdbcTemplate.queryForObject("SELECT count(sc.id) from schedule_change sc inner join" +
                        " client_analyzer.type_of_change toc  on sc .type_of_change_id = toc.id" +
                        " where sc.date between ? and ? and toc.name = 'CANCELLED' and not sc.planned and sc.client_id = ?",
                ResultSet::getInt, dateFrom, dateTo,  clientId);
    }

    public int getCountOfPlannedCancels(long clientId, Date dateFrom, Date dateTo) {
        return jdbcTemplate.queryForObject("SELECT count(sc.id) from schedule_change sc inner join" +
                        " client_analyzer.type_of_change toc  on sc .type_of_change_id = toc.id" +
                        " where sc.date > ? and sc.date < ? and toc.name = 'CANCELLED' and sc.planned and sc.client_id = ?",
                ResultSet::getInt, dateFrom, dateTo,  clientId);
    }

    public double getAverageCancellationsPerMonth(long clientId) {
        return jdbcTemplate.queryForObject("SELECT SUM(sc.id)/ TIMESTAMPDIFF(month, c.date_of_beginning, current_date) FROM schedule_change sc left join client c on sc.client_id = c.id\n" +
                "    inner join type_of_change toc on sc.type_of_change_id = toc.id\n" +
                "             WHERE client_id = ? and toc.name = 'CANCELLED';", ResultSet::getDouble, clientId);
    }

    public double getAverageShiftsPerMonth(long clientId) {
        return jdbcTemplate.queryForObject("SELECT SUM(sc.id)/ TIMESTAMPDIFF(month, c.date_of_beginning, current_date) FROM schedule_change sc left join client c on sc.client_id = c.id\n" +
                "    inner join type_of_change toc on sc.type_of_change_id = toc.id\n" +
                "             WHERE client_id = ? and toc.name = 'SHIFTED';", ResultSet::getDouble, clientId);
    }

    public int getExpectedIncoming(long clientId, Date dateFrom, Date dateTo) {
        return jdbcTemplate.queryForObject("SELECT SUM(cost_per_hour * count_of_hours_pr_week)* FLOOR(DATEDIFF(?, ?))/7\n" +
                "FROM client WHERE id = ?", ResultSet::getInt, dateTo, dateFrom, clientId);
    }

    public int getActualIncoming(long clientId, Date dateFrom, Date dateTo) {
        return jdbcTemplate.queryForObject("SELECT SUM(cost_per_hour * count_of_hours_pr_week)* FLOOR" +
                "(datediff(?, ?))/7 - count(query.client_id)*cost_per_hour\n" +
                "FROM client join schedule_change sc on client.id = sc.client_id, (select client_id from schedule_change inner join type_of_change toc\n" +
                "    on type_of_change_id = toc.id\n" +
                "     where client_id = ? and toc.name = 'CANCELLED') as query\n" +
                "WHERE client_id = ?", ResultSet::getInt, dateTo, dateFrom, clientId, clientId);
    }

    public int getAverageLosses(long clientId) {
        return jdbcTemplate.queryForObject("SELECT c.cost_per_hour*SUM(sc.id)/ TIMESTAMPDIFF(month, c.date_of_beginning, " +
                "current_date FROM schedule_change sc left join client c on sc.client_id = c.id\n" +
                "                inner join type_of_change toc on sc.type_of_change_id = toc.id\n" +
                "                WHERE client_id = ? and toc.name = 'CANCELLED';", ResultSet::getInt, clientId);
    }

    public int getActualLosses(long clientId, Date dateFrom, Date dateTo) {
        return jdbcTemplate.queryForObject("SELECT c.cost_per_hour*count(sc.id) from client c left join schedule_change sc " +
                "on c.id = sc.client_id\n" +
                "inner join type_of_change toc on sc.type_of_change_id = toc.id\n" +
                "where toc.name = 'CANCELLED' and c.id = ? and sc.date between ? and ?",
                ResultSet::getInt, clientId, dateFrom, dateTo);
    }

    public int getCancellationsPercentage(long clientId, Date dateFrom, Date dateTo) {
        return jdbcTemplate.queryForObject("SELECT 100*(count(query.id))/(c.count_of_meetings_pr_week * FLOOR(abs(datediff(?, ?)))/7)\n" +
                "from (select sc.id from schedule_change sc inner join type_of_change toc on sc.type_of_change_id = toc.id\n" +
                "    where sc.date between ? and ? and toc.name = 'CANCELLED' and sc.client_id = ?) as query, client c\n" +
                " where c.id = ?", ResultSet::getInt, dateFrom, dateTo, dateFrom, dateTo, clientId, clientId);
    }

    public int getShiftsPercentage(long clientId, Date dateFrom, Date dateTo) {
        return jdbcTemplate.queryForObject("SELECT 100*(count(query.id))/(c.count_of_meetings_pr_week * FLOOR(abs(datediff(?, ?)))/7)\n" +
                "from (select sc.id from schedule_change sc inner join type_of_change toc on sc.type_of_change_id = toc.id\n" +
                "    where sc.date between ? and ? and toc.name = 'SHIFTED' and sc.client_id = ?) as query, client c\n" +
                " where c.id = ?", ResultSet::getInt, dateFrom, dateTo, dateFrom, dateTo, clientId, clientId);
    }


}
