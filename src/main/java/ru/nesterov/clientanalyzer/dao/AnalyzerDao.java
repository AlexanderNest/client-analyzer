package ru.nesterov.clientanalyzer.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.nesterov.clientanalyzer.models.ClientScheduleChanges;
import ru.nesterov.clientanalyzer.models.ScheduleChange;
import ru.nesterov.clientanalyzer.models.TypeOfChange;

import java.sql.ResultSet;
import java.util.List;
@Repository
public interface AnalyzerDao {
    List<ClientScheduleChanges> getClientsOfUnplannedShifts ();
    List<ClientScheduleChanges> getClientsOfPlannedShifts ();
    List<ClientScheduleChanges> getClientsWithUnplannedCancellations ();
    List<ClientScheduleChanges> getClientsWithPlannedCancellations ();
}
