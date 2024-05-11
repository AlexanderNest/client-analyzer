package ru.nesterov.clientanalyzer.models;

import lombok.Data;

@Data
public class ClientScheduleChanges {
    private long clientId;
    private int shiftCount;
}
