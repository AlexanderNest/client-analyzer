package ru.nesterov.clientanalyzer.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "communication_type")
public class CommunicationType {
    @Id
    int id;
    String name;
}
