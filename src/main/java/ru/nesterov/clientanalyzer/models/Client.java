package ru.nesterov.clientanalyzer.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@Data
public class Client {
    private int id;
    private String name;
    private int costPerHour;
    private float countOfHoursPerWeek;
    private int countOfMeetingsPerWeek;
    private boolean active;
    private Date dateOfBeginning;
    private Communication communication;
}

