package ru.nesterov.clientanalyzer.controller.request;

import lombok.Builder;
import lombok.Data;
import ru.nesterov.clientanalyzer.models.TypeOfChange;

import java.sql.Date;

@Data
@Builder
public class AddScheduleChangeRequest {
    private int clientId;
    private Date date;
    private Date newDate;
    private boolean planned;
    private TypeOfChange typeOfChange;
}
