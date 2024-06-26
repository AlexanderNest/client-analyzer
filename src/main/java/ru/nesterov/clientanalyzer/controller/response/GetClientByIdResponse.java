package ru.nesterov.clientanalyzer.controller.response;

import lombok.Builder;
import lombok.Data;
import ru.nesterov.clientanalyzer.models.Communication;
import ru.nesterov.clientanalyzer.service.ClientDto;

import java.util.Date;

@Data
@Builder
public class GetClientByIdResponse {
    private int clientId;
    private String name;
    private int costPerHour;
    private float countOfHoursPerWeek;
    private int countOfMeetingsPerWeek;
    private boolean active;
    private Date dateOfBeginning;
    private Communication communication;
}
