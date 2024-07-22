package ru.nesterov.clientanalyzer.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nesterov.clientanalyzer.controller.request.ClientAnalyzerRequest;
import ru.nesterov.clientanalyzer.controller.request.ClientAnalyzerTwoDatesRequest;
import ru.nesterov.clientanalyzer.controller.request.GetAverageLossesRequest;
import ru.nesterov.clientanalyzer.controller.request.GetClientByIdRequest;
import ru.nesterov.clientanalyzer.controller.response.ClientAnalyzerResponse;
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
    public ClientAnalyzerResponse getCountOfSuccessfulMeetings(@RequestBody @Valid ClientAnalyzerTwoDatesRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getCountOfSuccessfulMeetings(request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getCountOfSuccessfulMeetingsByClientId")
    public ClientAnalyzerResponse getCountOfSuccessfulMeetingsByClientId(@RequestBody @Valid GetClientByIdRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getCountOfSuccessfulMeetings(request.getClientId())).
                build();
    }

    @GetMapping("/getCountOfSuccessfulMeetingsWithDatesByClientId")
    public ClientAnalyzerResponse getCountOfSuccessfulMeetingsByClientId(@RequestBody @Valid ClientAnalyzerRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getCountOfSuccessfulMeetings(request.getClientId()))
                .build();
    }

    @GetMapping("/getCountOfUnplannedShifts")
    public ClientAnalyzerResponse getCountOfUnplannedShifts(@RequestBody @Valid ClientAnalyzerRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getCountOfUnplannedShifts(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getCountOfPlannedShifts")
    public ClientAnalyzerResponse getCountOfPlannedShifts(@RequestBody @Valid ClientAnalyzerRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getCountOfPlannedShifts(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getCountOfUnplannedCancels")
    public ClientAnalyzerResponse getCountOfUnplannedCancels(@RequestBody @Valid ClientAnalyzerRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getCountOfUnplannedCancels(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getCountOfPlannedCancels")
    public ClientAnalyzerResponse getCountOfPlannedCancels(@RequestBody @Valid ClientAnalyzerRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getCountOfPlannedCancels(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getAverageCancellationsPerMonth")
    public ClientAnalyzerResponse getAverageCancellationsPerMonth(@RequestBody @Valid ClientAnalyzerRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getAverageCancellationsPerMonth(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getAverageShiftsPerMonth")
    public ClientAnalyzerResponse getAverageShiftsPerMonth(@RequestBody @Valid ClientAnalyzerRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getAverageShiftsPerMonth(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getExpectedIncoming")
    public ClientAnalyzerResponse getExpectedIncoming(@RequestBody @Valid ClientAnalyzerRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getExpectedIncoming(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getActualIncoming")
    public ClientAnalyzerResponse getActualIncoming(@RequestBody @Valid ClientAnalyzerRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getActualIncoming(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getAverageLosses")
    public ClientAnalyzerResponse getAverageLosses(@RequestBody @Valid GetAverageLossesRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getAverageLosses(request.getClientId(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getActualLosses")
    public ClientAnalyzerResponse getActualLosses(@RequestBody @Valid ClientAnalyzerRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getActualLosses(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getCancellationsPercentage")
    public ClientAnalyzerResponse getCancellationsPercentage(@RequestBody @Valid ClientAnalyzerRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getCancellationsPercentage(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getShiftsPercentage")
    public ClientAnalyzerResponse getShiftsPercentage(@RequestBody @Valid ClientAnalyzerRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getShiftsPercentage(request.getClientId(), request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getMostFrequentCancellationDay")
    public ClientAnalyzerResponse getMostFrequentCancellationDay() {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getMostFrequentCancellationDay())
                .build();
    }

    @GetMapping("/getMostFrequentShiftDay")
    public ClientAnalyzerResponse getMostFrequentShiftDay() {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getMostFrequentShiftDay())
                .build();
    }

    @GetMapping("/getMostFrequentCancellationMonth")
    public ClientAnalyzerResponse getMostFrequentCancellationMonth() {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getMostFrequentCancellationMonth())
                .build();
    }

    @GetMapping("/getMostFrequentShiftMonth")
    public ClientAnalyzerResponse getMostFrequentShiftMonth() {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getMostFrequentShiftMonth())
                .build();
    }

    @GetMapping("/getSuccessfulMeetingsPercentage")
    public ClientAnalyzerResponse getSuccessfulMeetingsPercentage(@RequestBody @Valid ClientAnalyzerTwoDatesRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getSuccessfulMeetingsPercentage(request.getDateFrom(), request.getDateTo()))
                .build();
    }

    @GetMapping("/getAllClientsIncoming")
    public ClientAnalyzerResponse getAllClientsIncoming(@RequestBody @Valid ClientAnalyzerTwoDatesRequest request) {
        return ClientAnalyzerResponse.builder()
                .value(clientAnalyzerService.getAllClientsIncoming(request.getDateFrom(), request.getDateTo()))
                .build();
    }
}
