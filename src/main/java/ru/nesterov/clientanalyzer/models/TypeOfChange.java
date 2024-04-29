package ru.nesterov.clientanalyzer.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "type_of_change")
public class TypeOfChange {
    @Id
    int id;
    String name;
}
