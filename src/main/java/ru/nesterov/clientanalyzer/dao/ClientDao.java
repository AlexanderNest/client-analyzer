package ru.nesterov.clientanalyzer.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.models.Communication;
import ru.nesterov.clientanalyzer.models.CommunicationType;

@Repository
@RequiredArgsConstructor
public class ClientDao {
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Client> clientRowMapper = (rs, rowNum) -> {
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setName(rs.getString("name"));
        client.setCostPerHour(rs.getInt("cost_per_hour"));
        client.setCountOfHoursPerWeek((rs.getInt("count_of_hours_pr_week")));
        client.setCountOfMeetingsPerWeek(rs.getInt("count_of_meetings_pr_week"));
        client.setActive(rs.getBoolean("active"));
        Communication communication = new Communication();
        communication.setContact(rs.getString("contact"));
        communication.setCommunicationType(CommunicationType.valueOf(rs.getString("communication_type_name")));
        client.setCommunication(communication);
        return client;
    };
    public Client getClientById(int id) {
        return jdbcTemplate.queryForObject("SELECT client.id, client.name, client.cost_per_hour, client.count_of_hours_pr_week, " +
                "client.count_of_meetings_pr_week, client.active, communication_type.name as communication_type_name, communication.contact" +
                "  FROM client inner join communication " +
                "on client.id = communication.client_id " +
                "inner join communication_type on communication.communication_type_id = communication_type.id " +
                "WHERE client.id = ?", clientRowMapper, id);
    }
}
