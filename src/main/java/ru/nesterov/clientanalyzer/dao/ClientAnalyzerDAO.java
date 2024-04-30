package ru.nesterov.clientanalyzer.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.models.ScheduleChange;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClientAnalyzerDAO {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Client> clientRowMapper = (rs, rowNum) -> {
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setName(rs.getString("name"));
        client.setCost_per_hour(rs.getInt("cost_per_hour"));
        client.setCommunication_type_id(rs.getInt("communication_type_id"));
        client.setCount_of_hours_pr_week(rs.getInt("count_of_hours_pr_week"));
        client.setCount_of_meetings_pr_week(rs.getInt("count_of_meetings_pr_week"));
        client.setActive(rs.getInt("active"));
        return client;
    };

    private final RowMapper<ScheduleChange> scheduleChangeRowMapper = (rs, rowNum) -> {
        ScheduleChange scheduleChange = new ScheduleChange();
        scheduleChange.setId(rs.getInt("id"));
        scheduleChange.setClientId(rs.getInt("client_id"));
        scheduleChange.setDate(rs.getDate("date"));
        scheduleChange.setNewDate(rs.getDate("new_date"));
        scheduleChange.setPlanned(rs.getInt("planned"));
        scheduleChange.setTyperOfChangeId(rs.getInt("type_of_change_id"));
        return scheduleChange;
    };

    public List<Client> getCountOfMeetingsHeld () {
        return jdbcTemplate.query("SELECT SUM(client.count_of_meetings_pr_week) as countOfMeetings\n" +
                "FROM client\n" +
                "         LEFT JOIN schedule_change ON client.id = schedule_change.client_id\n" +
                "         INNER JOIN type_of_change ON schedule_change.type_of_change_id = type_of_change.id\n" +
                "WHERE type_of_change.name <> 'CANCELLED' and client.active;", clientRowMapper);
    }

    public List<Client> getCountOfUnplannedShifts () {
        return jdbcTemplate.query("SELECT client.name, COUNT(schedule_change.id) as unplannedShifts " +
                "FROM client LEFT JOIN schedule_change ON client.id = schedule_change_id" +
                "INNER INNER JOIN type_of_change ON schedule_change.type_of_change_id = type_of_change.id" +
                "WHERE schedule_change.planned = 0 AND type_of_change.name = 'SHIFTED';", clientRowMapper);
    }

    public List<Client> getCountOfPlannedShifts () {
        return jdbcTemplate.query("SELECT client.name, COUNT(schedule_change.id) as unplannedShifts " +
                "FROM client LEFT JOIN schedule_change ON client.id = schedule_change_id" +
                "INNER INNER JOIN type_of_change ON schedule_change.type_of_change_id = type_of_change.id" +
                "WHERE schedule_change.planned = 1 AND type_of_change.name = 'SHIFTED';", clientRowMapper);
    }

    public List<Client> getCountOfUnplannedCancellations () {
        return jdbcTemplate.query("SELECT client.name, COUNT(schedule_change.id) as unplannedShifts " +
                "FROM client LEFT JOIN schedule_change ON client.id = schedule_change_id" +
                "INNER INNER JOIN type_of_change ON schedule_change.type_of_change_id = type_of_change.id" +
                "WHERE schedule_change.planned = 0 AND type_of_change.name = 'CANCELLED';", clientRowMapper);
    }

    public List<Client> getCountOfPlannedCancellations () {
        return jdbcTemplate.query("SELECT client.name, COUNT(schedule_change.id) as unplannedShifts " +
                "FROM client LEFT JOIN schedule_change ON client.id = schedule_change_id" +
                "INNER INNER JOIN type_of_change ON schedule_change.type_of_change_id = type_of_change.id" +
                "WHERE schedule_change.planned = 1 AND type_of_change.name = 'CANCELLED';", clientRowMapper);
    }
}
