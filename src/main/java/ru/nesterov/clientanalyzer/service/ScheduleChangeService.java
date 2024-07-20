package ru.nesterov.clientanalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nesterov.clientanalyzer.dao.ScheduleChangeDao;
import ru.nesterov.clientanalyzer.models.TypeOfChange;
import ru.nesterov.clientanalyzer.service.mapper.ScheduleChangeMapper;

import java.sql.Date;

@Service
public class ScheduleChangeService {
    private final ScheduleChangeDao scheduleChangeDao;
    private final ScheduleChangeMapper scheduleChangeMapper;

    @Autowired
    public ScheduleChangeService(ScheduleChangeDao scheduleChangeDao, ScheduleChangeMapper scheduleChangeMapper) {
        this.scheduleChangeDao = scheduleChangeDao;
        this.scheduleChangeMapper = scheduleChangeMapper;
    }

    public ScheduleChangeDto addScheduleChange(int clientId, Date date, Date newDate, boolean planned, TypeOfChange typerOfChange) {
        return scheduleChangeMapper.mapToSCDto(scheduleChangeDao.addScheduleChange(clientId, date, newDate, planned, typerOfChange));
    }
}
