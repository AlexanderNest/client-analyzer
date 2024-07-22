package ru.nesterov.clientanalyzer.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Positive
    private float countOfHoursPerWeek;
    @Positive
    private int countOfMeetingsPerWeek;
    @NotNull
    private boolean active;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBeginning;
    @NotNull
    private Communication communication;
}
