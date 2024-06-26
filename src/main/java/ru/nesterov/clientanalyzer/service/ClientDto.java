package ru.nesterov.clientanalyzer.service;

import lombok.Builder;
import lombok.Data;
import ru.nesterov.clientanalyzer.models.Communication;
import java.util.Date;

@Data
@Builder
public class ClientDto {
    private int id;
    private String name;
    private int costPerHour;
    private float countOfHoursPerWeek;
    private int countOfMeetingsPerWeek;
    private boolean active;
    private Date dateOfBeginning;
    private Communication communication;
}
