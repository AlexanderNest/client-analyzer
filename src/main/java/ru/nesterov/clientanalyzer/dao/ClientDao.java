package ru.nesterov.clientanalyzer.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.models.Communication;
import ru.nesterov.clientanalyzer.models.CommunicationType;

@Repository
public interface ClientDao {
    Client getClientById(int id);

    Client save(Client client);




}
