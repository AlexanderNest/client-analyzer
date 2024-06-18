package ru.nesterov.clientanalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nesterov.clientanalyzer.dao.ClientDao;
import ru.nesterov.clientanalyzer.models.Client;

@Service
public class ClientService {
    private final ClientDao clientDao;
    private final ClientMapping clientMapping;

    @Autowired
    public ClientService (ClientDao clientDao, ClientMapping clientMapping) {
        this.clientDao = clientDao;
        this.clientMapping = clientMapping;
    }

    public ClientDto getClientById(int id) {
        return clientMapping.mapToClientDto(clientDao.getClientById(id));
    }

    public ClientDto save(ClientDto clientDto) {
        Client client = clientMapping.mapToClient(clientDto);
        return clientMapping.mapToClientDto(clientDao.save(client));
    }

    public ClientDto createClient(ClientDto clientDto) {
        Client client = clientMapping.mapToClient(clientDto);
        return clientMapping.mapToClientDto(clientDao.save(client));
    }
}