package ru.nesterov.clientanalyzer.controller.request;

import lombok.Data;
import java.util.Date;

@Data
public class CreateClientRequest {
    private String name;
    private int costPerHour;
    private int countOfHoursPerWeek;
    private boolean active;
    private Date dateOfBeginning;
}
