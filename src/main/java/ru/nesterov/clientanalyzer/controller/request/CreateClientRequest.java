package ru.nesterov.clientanalyzer.controller.request;

import lombok.Data;
import ru.nesterov.clientanalyzer.models.Communication;

import java.util.Date;

@Data
public class CreateClientRequest {
    private int clientId;
    private String name;
    private int costPerHour;
    private float countOfHoursPerWeek;
    private int countOfMeetingsPerWeek;
    private boolean active;
    private Date dateOfBeginning;
    private Communication communication;
}
