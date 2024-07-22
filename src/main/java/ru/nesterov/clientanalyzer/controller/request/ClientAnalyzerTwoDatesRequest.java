package ru.nesterov.clientanalyzer.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.util.Date;

@Data
public class ClientAnalyzerTwoDatesRequest {
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateFrom;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private Date dateTo;
}
