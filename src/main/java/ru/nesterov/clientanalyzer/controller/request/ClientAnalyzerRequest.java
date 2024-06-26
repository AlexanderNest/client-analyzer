package ru.nesterov.clientanalyzer.controller.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClientAnalyzerRequest {
    private int clientId;
    private Date dateFrom;
    private Date dateTo;
}
