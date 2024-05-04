package ru.nesterov.clientanalyzer.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.nesterov.clientanalyzer.models.*;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClientAnalyzerDAO {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Client> clientRowMapper = (rs, rowNum) -> {
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setName(rs.getString("name"));
        client.setCostPerHour(rs.getInt("cost_per_hour"));
        client.setCountOfHoursPerWeek((rs.getInt("count_of_hours_pr_week")));
        client.setCountOfMeetingsPerWeek(rs.getInt("count_of_meetings_pr_week"));
        client.setActive(rs.getBoolean("active"));
        Communication communication = new Communication();
        communication.setContact(rs.getString("contact"));
        communication.setCommunicationType(CommunicationType.valueOf(rs.getString("communication_type")));
        client.setCommunication(communication);
        return client;
    };

    private final RowMapper<ScheduleChange> scheduleChangeRowMapper = (rs, rowNum) -> {
        ScheduleChange scheduleChange = new ScheduleChange();
        scheduleChange.setId(rs.getInt("id"));
        scheduleChange.setClientId(rs.getInt("client_id"));
        scheduleChange.setDate(rs.getDate("date"));
        scheduleChange.setNewDate(rs.getDate("new_date"));
        scheduleChange.setPlanned(rs.getBoolean("planned"));
        scheduleChange.setTyperOfChange(TypeOfChange.valueOf(rs.getString("type_of_change")));
        return scheduleChange;
    };

    public Client getClientById (int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM client WHERE id = ?", clientRowMapper, id);
    }

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
