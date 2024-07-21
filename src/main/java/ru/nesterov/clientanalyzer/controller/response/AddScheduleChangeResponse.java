package ru.nesterov.clientanalyzer.controller.response;

import lombok.Builder;
import lombok.Data;
import ru.nesterov.clientanalyzer.models.TypeOfChange;

import java.util.Date;

@Data
@Builder
public class AddScheduleChangeResponse {
    private int id;
    private int clientId;
    private java.util.Date date;
    private Date newDate;
    private boolean planned;
    private TypeOfChange typerOfChange;
}
