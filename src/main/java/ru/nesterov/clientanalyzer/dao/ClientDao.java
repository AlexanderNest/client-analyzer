package ru.nesterov.clientanalyzer.dao;

import org.springframework.stereotype.Repository;
import ru.nesterov.clientanalyzer.models.Client;

@Repository
public interface ClientDao {
    Client getClientById(int id);
    Client save(Client client);
}
