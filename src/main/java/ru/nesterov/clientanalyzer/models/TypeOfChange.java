package ru.nesterov.clientanalyzer.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
public class TypeOfChange {
    int id;
    String name;
}
