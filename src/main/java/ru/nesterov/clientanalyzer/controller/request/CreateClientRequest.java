package ru.nesterov.clientanalyzer.controller.request;

import lombok.Data;
import ru.nesterov.clientanalyzer.models.Client;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Data
public class CreateClientRequest {
    String name;
    int costPerHour;
    int countOfHoursPerWeek;
    boolean active;
    Date dateOfBeginning;
}
