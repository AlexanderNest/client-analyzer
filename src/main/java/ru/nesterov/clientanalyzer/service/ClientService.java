package ru.nesterov.clientanalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nesterov.clientanalyzer.dao.ClientDao;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.service.mapper.ClientMapper;

@Service
public class ClientService {
    private final ClientDao clientDao;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientService (ClientDao clientDao, ClientMapper clientMapper) {
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
    }

    public ClientDto getClientById(int id) {
        return clientMapper.mapToClientDto(clientDao.getClientById(id));
    }

    public ClientDto save(ClientDto clientDto) {
        Client client = clientMapper.mapToClient(clientDto);
        return clientMapper.mapToClientDto(clientDao.save(client));
    }
}