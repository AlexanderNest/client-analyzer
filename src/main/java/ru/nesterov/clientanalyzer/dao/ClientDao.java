package ru.nesterov.clientanalyzer.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.models.Communication;
import ru.nesterov.clientanalyzer.models.CommunicationType;

@Repository
public interface ClientDao {
    static final RowMapper<Client> clientRowMapper = (rs, rowNum) -> {
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setName(rs.getString("name"));
        client.setCostPerHour(rs.getInt("cost_per_hour"));
        client.setCountOfHoursPerWeek((rs.getInt("count_of_hours_pr_week")));
        client.setCountOfMeetingsPerWeek(rs.getInt("count_of_meetings_pr_week"));
        client.setActive(rs.getBoolean("active"));
        client.setDateOfBeginning(rs.getDate("date_of_beginning"));
        Communication communication = new Communication();
        communication.setContact(rs.getString("contact"));
        communication.setCommunicationType(CommunicationType.valueOf(rs.getString("communication_type_name")));
        client.setCommunication(communication);
        return client;
    };

    Client getClientById(int id);

    Client save(Client client);




}
