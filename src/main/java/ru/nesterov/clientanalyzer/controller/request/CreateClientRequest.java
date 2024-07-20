package ru.nesterov.clientanalyzer.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ru.nesterov.clientanalyzer.models.Communication;

import java.util.Date;

@Data
public class CreateClientRequest {
    private int clientId;
    @NotNull
    private String name;
    @Positive
    private int costPerHour;
    private float countOfHoursPerWeek;
    private int countOfMeetingsPerWeek;
    private boolean active;
    private Date dateOfBeginning;
    private Communication communication;
}
