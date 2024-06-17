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
    static final RowMapper<ScheduleChange> scheduleChangeRowMapper = (rs, rowNum) -> {
        ScheduleChange scheduleChange = new ScheduleChange();
        scheduleChange.setId(rs.getInt("id"));
        scheduleChange.setClientId(rs.getInt("client_id"));
        scheduleChange.setDate(rs.getDate("date"));
        scheduleChange.setNewDate(rs.getDate("new_date"));
        scheduleChange.setPlanned(rs.getBoolean("planned"));
        scheduleChange.setTyperOfChange(TypeOfChange.valueOf(rs.getString("type_of_change")));
        return scheduleChange;
    };

     static final RowMapper<ClientScheduleChanges> clientScheduleChangesRowMapper = (rs, rowNum) -> {
        ClientScheduleChanges clientScheduleChanges = new ClientScheduleChanges();
        clientScheduleChanges.setClientId(rs.getInt("client_id"));
        clientScheduleChanges.setShiftCount(rs.getInt("count_of_changes"));
        return clientScheduleChanges;
    };

    List<ClientScheduleChanges> getClientsOfUnplannedShifts ();

    List<ClientScheduleChanges> getClientsOfPlannedShifts ();

    List<ClientScheduleChanges> getClientsWithUnplannedCancellations ();

    List<ClientScheduleChanges> getClientsWithPlannedCancellations ();

}
