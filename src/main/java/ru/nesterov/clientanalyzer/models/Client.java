package ru.nesterov.clientanalyzer.models;

import lombok.Data;


@Data
public class Client {
    private int id;
    private String name;
    private int costPerHour;
    private float countOfHoursPerWeek;
    private int countOfMeetingsPerWeek;
    private boolean active;
    private Communication communication;
}

