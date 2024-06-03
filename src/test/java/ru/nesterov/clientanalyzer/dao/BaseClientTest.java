package ru.nesterov.clientanalyzer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.models.Communication;
import ru.nesterov.clientanalyzer.models.CommunicationType;

import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;

@SpringBootTest
public class BaseClientTest {
    @Autowired
    protected ClientDao clientDao;

    protected Client createClient(String name, boolean isActive) {
        return buildAndSaveClientWithModificator(client -> {
            client.setName(name);
            client.setActive(isActive);
        });
    }

    protected Client createClient(int countOfMeetingsPerWeek) {
        return buildAndSaveClientWithModificator(client -> client.setCountOfMeetingsPerWeek(countOfMeetingsPerWeek));
    }

    protected Client createClient (Date dateOfBeginning) {
        return buildAndSaveClientWithModificator(client -> client.setDateOfBeginning(dateOfBeginning));
    }

    protected Client createClient() {
        return buildAndSaveClientWithModificator(null);
    }


    private Client buildAndSaveClientWithModificator(Consumer<Client> clientModificator) {
        Client client = buildDefaultClient();

        if (clientModificator != null) {
            clientModificator.accept(client);
        }

        return clientDao.save(client);
    }

    protected Client buildDefaultClient() {
        Client client = new Client();
        client.setName("Client:" + UUID.randomUUID());
        client.setCostPerHour(1000);
        client.setCountOfHoursPerWeek(2);
        client.setCountOfMeetingsPerWeek(2);
        client.setActive(true);
        Communication communication = new Communication();
        communication.setCommunicationType(CommunicationType.TELEGRAM);
        communication.setContact("123");
        client.setCommunication(communication);
        client.setDateOfBeginning(new java.sql.Date(2024, 05, 20));
        return client;
    }
}
