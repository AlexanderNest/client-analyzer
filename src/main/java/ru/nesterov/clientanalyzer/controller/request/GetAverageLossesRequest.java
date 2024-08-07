package ru.nesterov.clientanalyzer.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GetAverageLossesRequest {
    private int clientId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private Date dateTo;
}
