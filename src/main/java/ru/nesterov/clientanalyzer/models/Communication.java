package ru.nesterov.clientanalyzer.models;

import lombok.Data;

@Data
public class Communication {
    private String contact;
    private CommunicationType communicationType;
}
