package ru.nesterov.clientanalyzer.controller.request;

import lombok.Data;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.models.CommunicationType;
import ru.nesterov.clientanalyzer.service.ClientDto;

import java.util.Date;

@Data
public class CreateClientRequest {
    ClientDto clientDto;
}
