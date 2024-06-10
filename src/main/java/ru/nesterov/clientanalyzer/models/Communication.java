package ru.nesterov.clientanalyzer.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Communication {
    private String contact;
    private CommunicationType communicationType;
}
