package ru.nesterov.clientanalyzer.service.mapper;

import org.springframework.stereotype.Service;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.service.ClientDto;

@Service
public class ClientMapper {
    public ClientDto mapToClientDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .communication(client.getCommunication())
                .costPerHour(client.getCostPerHour())
                .countOfMeetingsPerWeek(client.getCountOfMeetingsPerWeek())
                .active(client.isActive())
                .dateOfBeginning(client.getDateOfBeginning())
                .countOfHoursPerWeek(client.getCountOfHoursPerWeek())
                .build();
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
