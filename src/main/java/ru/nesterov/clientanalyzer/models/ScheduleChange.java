package ru.nesterov.clientanalyzer.models;

import lombok.Data;

import java.util.Date;

@Data
public class ScheduleChange {
    private int id;
    private int clientId;
    private java.util.Date date;
    private Date newDate;
    private boolean planned;
    private TypeOfChange typerOfChange;
}
