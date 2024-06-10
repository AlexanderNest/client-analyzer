package ru.nesterov.clientanalyzer.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.nesterov.clientanalyzer.models.*;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnalyzerDAO {
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<ScheduleChange> scheduleChangeRowMapper = (rs, rowNum) -> {
        ScheduleChange scheduleChange = new ScheduleChange();
        scheduleChange.setId(rs.getInt("id"));
        scheduleChange.setClientId(rs.getInt("client_id"));
        scheduleChange.setDate(rs.getDate("date"));
        scheduleChange.setNewDate(rs.getDate("new_date"));
        scheduleChange.setPlanned(rs.getBoolean("planned"));
        scheduleChange.setTyperOfChange(TypeOfChange.valueOf(rs.getString("type_of_change")));
        return scheduleChange;
    };

    private static final RowMapper<ClientScheduleChanges> clientScheduleChangesRowMapper = (rs, rowNum) -> {
        ClientScheduleChanges clientScheduleChanges = new ClientScheduleChanges();
        clientScheduleChanges.setClientId(rs.getInt("client_id"));
        clientScheduleChanges.setShiftCount(rs.getInt("count_of_changes"));
        return clientScheduleChanges;
    };

    public List<ClientScheduleChanges> getClientsOfUnplannedShifts () {
        String sql = "select client_id, count(client_id) as count_of_changes " +
                "from schedule_change inner join type_of_change on schedule_change.type_of_change_id = type_of_change.id " +
                "where schedule_change.planned = 0 and type_of_change.name = 'SHIFTED' " +
                "group by client_id;";
        return jdbcTemplate.query(sql, clientScheduleChangesRowMapper);
    }

    public List<ClientScheduleChanges> getClientsOfPlannedShifts () {
        String sql = "select client_id, count(client_id) as count_of_changes " +
                "from schedule_change inner join type_of_change on schedule_change.type_of_change_id = type_of_change.id " +
                "where schedule_change.planned and type_of_change.name = 'SHIFTED' " +
                "group by client_id;";
        return jdbcTemplate.query(sql, clientScheduleChangesRowMapper);
    }

    public List<ClientScheduleChanges> getClientsWithUnplannedCancellations () {
        String sql = "select client_id, count(client_id) as count_of_changes " +
                "from schedule_change inner join type_of_change on schedule_change.type_of_change_id = type_of_change.id " +
                "where schedule_change.planned = 0 and type_of_change.name = 'CANCELLED' " +
                "group by client_id;";
        return jdbcTemplate.query(sql, clientScheduleChangesRowMapper);
    }

    public List<ClientScheduleChanges> getClientsWithPlannedCancellations () {
        String sql = "select client_id, count(client_id) as count_of_changes " +
                "from schedule_change inner join type_of_change on schedule_change.type_of_change_id = type_of_change.id " +
                "where schedule_change.planned and type_of_change.name = 'CANCELLED' " +
                "group by client_id;";
        return jdbcTemplate.query(sql, clientScheduleChangesRowMapper);
    }
}
