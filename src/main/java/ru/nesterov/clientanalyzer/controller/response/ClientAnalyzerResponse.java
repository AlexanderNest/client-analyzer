package ru.nesterov.clientanalyzer.controller.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientAnalyzerResponse {
    private Object value;
}
