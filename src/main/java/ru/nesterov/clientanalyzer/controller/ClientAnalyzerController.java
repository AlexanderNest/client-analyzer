package ru.nesterov.clientanalyzer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nesterov.clientanalyzer.controller.request.ClientAnalyzerRequest;
import ru.nesterov.clientanalyzer.controller.request.GetAverageLossesRequest;
import ru.nesterov.clientanalyzer.controller.response.ClientAnalyzerDoubleResponse;
import ru.nesterov.clientanalyzer.controller.response.ClientAnalyzerIntegerResponse;
import ru.nesterov.clientanalyzer.service.ClientAnalyzerService;
import ru.nesterov.clientanalyzer.service.mapper.ClientMapper;

@RestController
@RequestMapping("/clientController")
public class ClientAnalyzerController {
    private final ClientAnalyzerService clientAnalyzerService;

    public ClientAnalyzerController(ClientAnalyzerService clientAnalyzerService, ClientMapper clientMapper) {
        this.clientAnalyzerService = clientAnalyzerService;
    }

    @GetMapping("/getCountOfSuccessfulMeetings")
    public ClientAnalyzerIntegerResponse getCountOfSuccessfulMeetings() {
        return ClientAnalyzerIntegerResponse.builder()
                .response(clientAnalyzerService.getCountOfSuccessfulMeetings())
                .build();
    }

    @GetMapping("/getCountOfSuccessfulMeetingsByClientId")
    public ClientAnalyzerIntegerResponse getCountOfSuccessfulMeetingsByClientId(@RequestBody long id) {
        return ClientAnalyzerIntegerResponse.builder()
                .response(clientAnalyzerService.getCountOfSuccessfulMeetings(id)).
                build();
    }

    @GetMapping("/getCountOfSuccessfulMeetingsWithDatesByClientId")
    public ClientAnalyzerIntegerResponse getCountOfSuccessfulMeetingsByClientId(@RequestBody ClientAnalyzerRequest request) {
        return ClientAnalyzerIntegerResponse.builder()
                .response(clientAnalyzerService.getCountOfSuccessfulMeetings(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getCountOfUnplannedShifts")
    public ClientAnalyzerIntegerResponse getCountOfUnplannedShifts(@RequestBody ClientAnalyzerRequest request) {
        return ClientAnalyzerIntegerResponse.builder()
                .response(clientAnalyzerService.getCountOfUnplannedShifts(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getCountOfPlannedShifts")
    public ClientAnalyzerIntegerResponse getCountOfPlannedShifts(@RequestBody ClientAnalyzerRequest request) {
        return ClientAnalyzerIntegerResponse.builder()
                .response(clientAnalyzerService.getCountOfPlannedShifts(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getCountOfUnplannedCancels")
    public ClientAnalyzerIntegerResponse getCountOfUnplannedCancels(@RequestBody ClientAnalyzerRequest request) {
        return ClientAnalyzerIntegerResponse.builder()
                .response(clientAnalyzerService.getCountOfUnplannedCancels(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getCountOfPlannedCancels")
    public ClientAnalyzerIntegerResponse getCountOfPlannedCancels(@RequestBody ClientAnalyzerRequest request) {
        return ClientAnalyzerIntegerResponse.builder()
                .response(clientAnalyzerService.getCountOfPlannedCancels(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getAverageCancellationsPerMonth")
    public ClientAnalyzerDoubleResponse getAverageCancellationsPerMonth(@RequestBody ClientAnalyzerRequest request) {
        return ClientAnalyzerDoubleResponse.builder()
                .response(clientAnalyzerService.getAverageCancellationsPerMonth(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getAverageShiftsPerMonth")
    public ClientAnalyzerDoubleResponse getAverageShiftsPerMonth(@RequestBody ClientAnalyzerRequest request) {
        return ClientAnalyzerDoubleResponse.builder()
                .response(clientAnalyzerService.getAverageShiftsPerMonth(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getExpectedIncoming")
    public ClientAnalyzerIntegerResponse getExpectedIncoming(@RequestBody ClientAnalyzerRequest request) {
        return ClientAnalyzerIntegerResponse.builder()
                .response(clientAnalyzerService.getExpectedIncoming(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getActualIncoming")
    public ClientAnalyzerIntegerResponse getActualIncoming(@RequestBody ClientAnalyzerRequest request) {
        return ClientAnalyzerIntegerResponse.builder()
                .response(clientAnalyzerService.getActualIncoming(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getAverageLosses")
    public ClientAnalyzerIntegerResponse getAverageLosses(@RequestBody GetAverageLossesRequest request) {
        return ClientAnalyzerIntegerResponse.builder()
                .response(clientAnalyzerService.getAverageLosses(request.getClientId(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getActualLosses")
    public ClientAnalyzerIntegerResponse getActualLosses(@RequestBody ClientAnalyzerRequest request) {
        return ClientAnalyzerIntegerResponse.builder()
                .response(clientAnalyzerService.getActualLosses(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getCancellationsPercentage")
    public ClientAnalyzerDoubleResponse getCancellationsPercentage(@RequestBody ClientAnalyzerRequest request) {
        return ClientAnalyzerDoubleResponse.builder()
                .response(clientAnalyzerService.getCancellationsPercentage(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getShiftsPercentage")
    public ClientAnalyzerDoubleResponse getShiftsPercentage(@RequestBody ClientAnalyzerRequest request) {
        return ClientAnalyzerDoubleResponse.builder()
                .response(clientAnalyzerService.getShiftsPercentage(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }
}
