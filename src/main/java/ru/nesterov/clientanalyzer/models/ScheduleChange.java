package ru.nesterov.clientanalyzer.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table (name = "schedule_change")
public class ScheduleChange {
    @Id
    int id;
    int clientId;
    Date date;
    Date newDate;
    int planned;
    int typerOfChangeId;

}
