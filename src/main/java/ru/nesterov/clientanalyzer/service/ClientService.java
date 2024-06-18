package ru.nesterov.clientanalyzer.service;

import org.springframework.stereotype.Service;
import ru.nesterov.clientanalyzer.dao.ClientDao;
import ru.nesterov.clientanalyzer.models.Client;

@Service
public class ClientService {
    private final ClientDao clientDao;
    private final ClientMapping clientMapping;

    public ClientService (ClientDao clientDao, ClientMapping clientMapping) {
        this.clientDao = clientDao;
        this.clientMapping = clientMapping;
    }

    public ClientDto getClientById(int id) {
        return clientMapping.mapToClientDto(clientDao.getClientById(id));
    }

    public ClientDto save(Client client) {
        return clientMapping.mapToClientDto(clientDao.save(client));
    }
}
