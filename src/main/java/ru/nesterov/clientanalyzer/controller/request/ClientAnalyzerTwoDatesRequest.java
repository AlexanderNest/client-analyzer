package ru.nesterov.clientanalyzer.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ClientAnalyzerTwoDatesRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date dateFrom;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date dateTo;
}
