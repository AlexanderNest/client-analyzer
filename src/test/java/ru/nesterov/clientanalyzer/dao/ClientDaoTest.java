package ru.nesterov.clientanalyzer.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nesterov.clientanalyzer.models.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientDaoTest extends BaseClientTest {
    @Test
    void getClientById () {
        Client client = createClient();
        Client client2 = createClient();
        Client client3 = createClient();
        Client client4 = createClient();

        Client actualClient = clientDao.getClientById(client.getId());
        assertEquals(client, actualClient);
    }

}
