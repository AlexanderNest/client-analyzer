package ru.nesterov.clientanalyzer.controller.request;

import lombok.Data;

import java.util.Date;

@Data
public class ClientAnalyzerTwoDatesRequest {
    Date dateFrom;
    Date dateTo;
}
