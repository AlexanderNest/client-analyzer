package ru.nesterov.clientanalyzer.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.nesterov.clientanalyzer.models.*;
import ru.nesterov.clientanalyzer.service.mapper.ScheduleChangeMapper;

import java.util.Date;

@Repository
public class ScheduleChangeDao {
    private final ScheduleChangeMapper scheduleChangeMapper;
    private final Logger logger = LoggerFactory.getLogger(ScheduleChangeDao.class);
    private final SimpleJdbcInsert insertScheduleChange;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ScheduleChangeDao(ScheduleChangeMapper scheduleChangeMapper, NamedParameterJdbcTemplate jdbcTemplate) {
        this.scheduleChangeMapper = scheduleChangeMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.insertScheduleChange = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName("schedule_change")
                .usingGeneratedKeyColumns("id");
    }

    public ScheduleChange addScheduleChange(long clientId, Date date, Date newDate, boolean isPlanned, TypeOfChange type) {
        MapSqlParameterSource parameterSourceForTypeId = new MapSqlParameterSource() {{
            addValue("typeName", type.name());
        }};
        String getTypeId = "select id from type_of_change where name = :typeName";
        long typeId = jdbcTemplate.queryForObject(getTypeId, parameterSourceForTypeId, Long.class);

        MapSqlParameterSource parameterSource = new MapSqlParameterSource() {{
            addValue("client_Id", clientId);
            addValue("date", date);
            addValue("new_date", newDate);
            addValue("planned", isPlanned);
            addValue("type_of_change_id", typeId);
        }};

        Number key = insertScheduleChange.executeAndReturnKey(parameterSource);

        return scheduleChangeMapper.mapToSC(key.longValue(), clientId, date, newDate, isPlanned, type);
    }
}
