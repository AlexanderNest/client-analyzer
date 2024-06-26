package ru.nesterov.clientanalyzer.controller.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GetAverageLossesRequest {
    private int clientId;
    private Date dateTo;
}
