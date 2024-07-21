package ru.nesterov.clientanalyzer.service.mapper;

import org.springframework.stereotype.Service;
import ru.nesterov.clientanalyzer.controller.response.AddScheduleChangeResponse;
import ru.nesterov.clientanalyzer.models.ScheduleChange;
import ru.nesterov.clientanalyzer.models.TypeOfChange;
import ru.nesterov.clientanalyzer.service.ScheduleChangeDto;

import java.util.Date;

@Service
public class ScheduleChangeMapper {
    public AddScheduleChangeResponse mapToScheduleChangeResponse(ScheduleChangeDto scheduleChangeDto) {
        return AddScheduleChangeResponse.builder()
                .typerOfChange(scheduleChangeDto.getTyperOfChange())
                .clientId(scheduleChangeDto.getClientId())
                .date(scheduleChangeDto.getDate())
                .newDate(scheduleChangeDto.getNewDate())
                .id(scheduleChangeDto.getId())
                .planned(scheduleChangeDto.isPlanned())
                .build();
    }

    public ScheduleChangeDto mapToSCDto(ScheduleChange scheduleChange) {
        return ScheduleChangeDto.builder()
                .id(scheduleChange.getId())
                .typerOfChange(scheduleChange.getTyperOfChange())
                .newDate(scheduleChange.getNewDate())
                .planned(scheduleChange.isPlanned())
                .date(scheduleChange.getDate())
                .clientId(scheduleChange.getClientId())
                .build();
    }

    public ScheduleChange mapToSC(ScheduleChangeDto scheduleChangeDto) {
        ScheduleChange scheduleChange = new ScheduleChange();

        scheduleChange.setPlanned(scheduleChangeDto.isPlanned());
        scheduleChange.setTyperOfChange(scheduleChangeDto.getTyperOfChange());
        scheduleChange.setDate(scheduleChangeDto.getDate());
        scheduleChange.setNewDate(scheduleChangeDto.getNewDate());
        scheduleChange.setId(scheduleChangeDto.getId());
        scheduleChange.setClientId(scheduleChangeDto.getClientId());

        return scheduleChange;
    }

    public ScheduleChange mapToSC(long id, long clientId, Date date, Date newDate, boolean planned, TypeOfChange typeOfChange) {
        ScheduleChange scheduleChange = new ScheduleChange();

        scheduleChange.setId((int)id);
        scheduleChange.setPlanned(planned);
        scheduleChange.setTyperOfChange(typeOfChange);
        scheduleChange.setClientId((int)clientId);
        scheduleChange.setDate(date);
        scheduleChange.setNewDate(newDate);

        return scheduleChange;
    }
}
