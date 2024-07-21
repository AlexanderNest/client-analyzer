package ru.nesterov.clientanalyzer.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.nesterov.clientanalyzer.models.TypeOfChange;

import java.sql.Date;

@Data
@Builder
public class AddScheduleChangeRequest {
    private int clientId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date newDate;
    private boolean planned;
    @NotNull
    private TypeOfChange typeOfChange;
}
