package ru.nesterov.clientanalyzer.service;

import ru.nesterov.clientanalyzer.models.Communication;

import java.util.Date;

public class ClientDto {
    private long id;
    private String name;
    private int costPerHour;
    private float countOfHoursPerWeek;
    private int countOfMeetingsPerWeek;
    private boolean active;
    private Date dateOfBeginning;
    private Communication communication;
}
