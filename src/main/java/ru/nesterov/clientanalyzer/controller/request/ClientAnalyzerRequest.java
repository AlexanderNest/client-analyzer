package ru.nesterov.clientanalyzer.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClientAnalyzerRequest {
    private int clientId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateFrom;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateTo;
}
