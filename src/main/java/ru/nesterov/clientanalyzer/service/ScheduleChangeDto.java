package ru.nesterov.clientanalyzer.service;

import lombok.Builder;
import lombok.Data;
import ru.nesterov.clientanalyzer.models.TypeOfChange;

import java.util.Date;

@Data
@Builder
public class ScheduleChangeDto {
    private int id;
    private int clientId;
    private Date date;
    private Date newDate;
    private boolean planned;
    private TypeOfChange typerOfChange;
}
