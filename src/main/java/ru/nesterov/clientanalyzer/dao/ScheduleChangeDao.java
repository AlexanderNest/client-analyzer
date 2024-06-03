package ru.nesterov.clientanalyzer.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.nesterov.clientanalyzer.models.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ScheduleChangeDao {

    private final SimpleJdbcInsert insertScheduleChange;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ScheduleChangeDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertScheduleChange = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName("schedule_change")
                .usingGeneratedKeyColumns("id");
    }

    private static final RowMapper<ScheduleChange> scRowMapper = (rs, rowNum) -> {
        ScheduleChange scheduleChange = new ScheduleChange();
        scheduleChange.setId(rs.getInt("id"));
        scheduleChange.setClientId(rs.getInt("client_id"));
        scheduleChange.setDate(rs.getDate("date"));
        scheduleChange.setNewDate(rs.getDate("new_date"));
        scheduleChange.setPlanned(rs.getBoolean("planned"));
        //scheduleChange.setTyperOfChange(getTypeOfChange(rs.getInt("id")));

        return scheduleChange;
    };

    public void addScheduleChange(long clientId, Date date, Date newDate, boolean isPlanned, TypeOfChange type) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource() {{
            addValue("clientId", clientId);
            addValue("date", date);
            addValue("newDate", newDate);
            addValue("isPlanned", isPlanned);
            addValue("typeName", type.name());
        }};

        String sql = "INSERT INTO schedule_change(client_id, date, new_date, planned, type_of_change_id) VALUES " +
                "(:clientId, :date, :newDate, :isPlanned, (select id from type_of_change where name = :typeName))";

        jdbcTemplate.update(sql, parameterSource);
    }
}
