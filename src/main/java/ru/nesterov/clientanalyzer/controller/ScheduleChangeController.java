package ru.nesterov.clientanalyzer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nesterov.clientanalyzer.controller.request.AddScheduleChangeRequest;
import ru.nesterov.clientanalyzer.controller.response.AddScheduleChangeResponse;
import ru.nesterov.clientanalyzer.service.ScheduleChangeService;
import ru.nesterov.clientanalyzer.service.mapper.ScheduleChangeMapper;

@RestController
@RequestMapping("/scheduleChange")
public class ScheduleChangeController {
    private final ScheduleChangeService scheduleChangeService;
    private final ScheduleChangeMapper scheduleChangeMapper;

    public ScheduleChangeController(ScheduleChangeService scheduleChangeService, ScheduleChangeMapper scheduleChangeMapper) {
        this.scheduleChangeService = scheduleChangeService;
        this.scheduleChangeMapper = scheduleChangeMapper;
    }

    @PostMapping("/addScheduleChange")
    public AddScheduleChangeResponse addScheduleChange(@RequestBody AddScheduleChangeRequest request) {
        return scheduleChangeMapper.mapToScheduleChangeResponse(scheduleChangeService.addScheduleChange(request.getClientId(), request.getDate(), request.getNewDate(), request.isPlanned(), request.getTypeOfChange()));
    }
}
