package ru.nesterov.clientanalyzer.controller.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Builder
@Data
public class ClientAnalyzerIntegerResponse {
    private Integer response;
}
