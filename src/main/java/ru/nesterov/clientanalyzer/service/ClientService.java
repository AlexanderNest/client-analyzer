package ru.nesterov.clientanalyzer.service;

import org.springframework.stereotype.Service;
import ru.nesterov.clientanalyzer.dao.ClientDao;
import ru.nesterov.clientanalyzer.dao.ClientDaoImpl;
import ru.nesterov.clientanalyzer.models.Client;

@Service
public class ClientService {
    private final ClientDao clientDao;
    public ClientService (ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public Client getClientById(int id) {
        return clientDao.getClientById(id);
    }

    public Client save(Client client) {
        return clientDao.save(client);
    }
}
