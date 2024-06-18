package ru.nesterov.clientanalyzer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nesterov.clientanalyzer.models.Client;

@Service
public class ClientMapping {
    public ClientDto mapToClientDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        clientDto.setActive(client.isActive());
        clientDto.setCommunication(client.getCommunication());
        clientDto.setCostPerHour(client.getCostPerHour());
        clientDto.setCountOfHoursPerWeek(client.getCountOfHoursPerWeek());
        clientDto.setCountOfMeetingsPerWeek(client.getCountOfMeetingsPerWeek());
        clientDto.setDateOfBeginning(client.getDateOfBeginning());

        return clientDto;
    }

    public Client mapToClient(ClientDto clientDto) {
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setName(clientDto.getName());
        client.setCountOfHoursPerWeek(clientDto.getCountOfHoursPerWeek());
        client.setActive(clientDto.isActive());
        client.setCommunication(clientDto.getCommunication());
        client.setDateOfBeginning(clientDto.getDateOfBeginning());
        client.setCountOfMeetingsPerWeek(clientDto.getCountOfMeetingsPerWeek());
        client.setCostPerHour(clientDto.getCostPerHour());

        return client;
    }
}
