package ru.nesterov.clientanalyzer.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.models.Communication;
import ru.nesterov.clientanalyzer.models.CommunicationType;

import java.util.HashMap;
import java.util.Map;


@Repository
public class ClientDaoImpl implements ClientDao{
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertClient;

    private static final RowMapper<Client> clientRowMapper = (rs, rowNum) -> {
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

    public ClientDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertClient = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName("client")
                .usingGeneratedKeyColumns("id");
    }

    public Client getClientById(int id) {
        return jdbcTemplate.getJdbcTemplate().queryForObject("SELECT client.id, client.name, client.cost_per_hour, client.count_of_hours_pr_week, " +
                "client.count_of_meetings_pr_week, client.active, client.date_of_beginning, communication_type.name as communication_type_name, communication.contact" +
                "  FROM client left join communication " +
                " on client.id = communication.client_id " +
                " left join communication_type on communication.communication_type_id = communication_type.id " +
                " WHERE client.id = ?", clientRowMapper, id);
    }

    public Client save(Client client) {
        Map<String, Object> values = new HashMap<>() {{
            put("name", client.getName());
            put("cost_per_hour", client.getCostPerHour());
            put("count_of_hours_pr_week", client.getCountOfHoursPerWeek());
            put("count_of_meetings_pr_week", client.getCountOfMeetingsPerWeek());
            put("active", client.isActive());
            put("date_of_beginning", client.getDateOfBeginning());
        }};


        int generatedId = insertClient.executeAndReturnKey(values).intValue();
        client.setId(generatedId);

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("clientId", client.getId())
                .addValue("name", client.getCommunication().getCommunicationType().name())
                .addValue("contact", client.getCommunication().getContact());
        String sql = " insert into communication values (:clientId, (select id from communication_type where name = :name), :contact)";
        jdbcTemplate.update(sql, parameterSource);

        return client;
    }

}
