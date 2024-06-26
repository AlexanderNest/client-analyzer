package ru.nesterov.clientanalyzer.service.mapper;

import org.springframework.stereotype.Service;
import ru.nesterov.clientanalyzer.controller.request.CreateClientRequest;
import ru.nesterov.clientanalyzer.controller.response.CreateClientResponse;
import ru.nesterov.clientanalyzer.controller.response.GetClientByIdResponse;
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

    public ClientDto mapToClientDto(CreateClientRequest request) {
        return ClientDto.builder()
                .id(request.getClientId())
                .dateOfBeginning(request.getDateOfBeginning())
                .communication(request.getCommunication())
                .name(request.getName())
                .countOfHoursPerWeek(request.getCountOfHoursPerWeek())
                .countOfMeetingsPerWeek(request.getCountOfMeetingsPerWeek())
                .costPerHour(request.getCostPerHour())
                .active(request.isActive())
                .build();
    }

    public CreateClientResponse mapToCreateClientResponse(ClientDto clientDto) {
        return CreateClientResponse.builder()
                .clientId(clientDto.getId())
                .name(clientDto.getName())
                .active(clientDto.isActive())
                .communication(clientDto.getCommunication())
                .costPerHour(clientDto.getCostPerHour())
                .countOfHoursPerWeek(clientDto.getCountOfHoursPerWeek())
                .dateOfBeginning(clientDto.getDateOfBeginning())
                .countOfMeetingsPerWeek(clientDto.getCountOfMeetingsPerWeek())
                .build();
    }

    public GetClientByIdResponse mapToGetClientByIdResponse(ClientDto clientDto) {
        return GetClientByIdResponse.builder()
                .clientId(clientDto.getId())
                .name(clientDto.getName())
                .active(clientDto.isActive())
                .communication(clientDto.getCommunication())
                .costPerHour(clientDto.getCostPerHour())
                .countOfHoursPerWeek(clientDto.getCountOfHoursPerWeek())
                .dateOfBeginning(clientDto.getDateOfBeginning())
                .countOfMeetingsPerWeek(clientDto.getCountOfMeetingsPerWeek())
                .build();
    }
}
