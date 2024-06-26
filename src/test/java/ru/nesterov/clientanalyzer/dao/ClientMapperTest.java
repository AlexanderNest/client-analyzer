package ru.nesterov.clientanalyzer.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.models.Communication;
import ru.nesterov.clientanalyzer.models.CommunicationType;
import ru.nesterov.clientanalyzer.service.AnalyzerService;
import ru.nesterov.clientanalyzer.service.ClientDto;
import ru.nesterov.clientanalyzer.service.mapper.ClientMapper;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ClientMapperTest extends BaseClientTest{
    @Autowired
    protected ClientMapper clientMapper;

    @Test
    protected void mapToClientDto() {
        Client client = createClient();

        ClientDto clientDto = ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .active(client.isActive())
                .costPerHour(client.getCostPerHour())
                .dateOfBeginning(client.getDateOfBeginning())
                .countOfMeetingsPerWeek(client.getCountOfMeetingsPerWeek())
                .communication(client.getCommunication())
                .countOfHoursPerWeek(client.getCountOfHoursPerWeek())
                .build();

        ClientDto actualClientDto = clientMapper.mapToClientDto(client);
        assertEquals(clientDto, actualClientDto);
    }

    @Test
    protected void mapToClient() {
        Communication communication = new Communication();
        communication.setCommunicationType(CommunicationType.TELEGRAM);
        communication.setContact("123");
        ClientDto clientDto = ClientDto.builder()
                .active(true)
                .name("Client:" + UUID.randomUUID())
                .costPerHour(100)
                .countOfMeetingsPerWeek(2)
                .countOfHoursPerWeek(2)
                .communication(communication)
                .dateOfBeginning(new java.sql.Date(2024, 05, 20))
                .build();

        Client client = new Client();
        client.setId(clientDto.getId());
        client.setName(clientDto.getName());
        client.setActive(clientDto.isActive());
        client.setCommunication(communication);
        client.setCostPerHour(clientDto.getCostPerHour());
        client.setDateOfBeginning(clientDto.getDateOfBeginning());
        client.setCountOfMeetingsPerWeek(clientDto.getCountOfMeetingsPerWeek());
        client.setCountOfHoursPerWeek(clientDto.getCountOfHoursPerWeek());

        Client actualClient = clientMapper.mapToClient(clientDto);
        assertEquals(client, actualClient);
    }
}
