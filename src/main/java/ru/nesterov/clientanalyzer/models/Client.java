package ru.nesterov.clientanalyzer.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table (name = "client")
public class Client {
    @Id
    int id;
    String name;
    int communication_type_id;
    int cost_per_hour;
    float count_of_hours_pr_week;
    int count_of_meetings_pr_week;
    int active;

}

