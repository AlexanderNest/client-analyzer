package ru.nesterov.clientanalyzer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nesterov.clientanalyzer.service.ClientAnalyzerService;

import java.util.Date;

@RestController
@RequestMapping("/clientController")
public class ClientAnalyzerController {
    private final ClientAnalyzerService clientAnalyzerService;
    public ClientAnalyzerController(ClientAnalyzerService clientAnalyzerService) {
        this.clientAnalyzerService = clientAnalyzerService;
    }
    @GetMapping("/getCountOfSuccessfulMeetings")
    public int getCountOfSuccessfulMeetings() {
        return clientAnalyzerService.getCountOfSuccessfulMeetings();
    }

    @GetMapping("/getCountOfSuccessfulMeetingsByClientId")
    public int getCountOfSuccessfulMeetings(@RequestBody long id) {
        return clientAnalyzerService.getCountOfSuccessfulMeetings(id);
    }

    @GetMapping("/getCountOfSuccessfulMeetingsWithDates")
    public int getCountOfSuccessfulMeetings(@RequestBody long clientId, @RequestBody Date dateFrom, @RequestBody Date dateTo) {
        return clientAnalyzerService.getCountOfSuccessfulMeetings(clientId, dateFrom, dateTo);
    }

    @GetMapping("/getCountOfUnplannedShifts")
    public int getCountOfUnplannedShifts(@RequestBody long clientId, @RequestBody Date dateFrom, @RequestBody Date dateTo) {
        return  clientAnalyzerService.getCountOfUnplannedShifts(clientId, dateFrom, dateTo);
    }

    @GetMapping("/getCountOfPlannedShifts")
    public int getCountOfPlannedShifts(@RequestBody long clientId, @RequestBody Date dateFrom, @RequestBody Date dateTo) {
        return  clientAnalyzerService.getCountOfPlannedShifts(clientId, dateFrom, dateTo);
    }

    @GetMapping("/getCountOfUnplannedCancels")
    public int getCountOfUnplannedCancels(@RequestBody long clientId, @RequestBody Date dateFrom, @RequestBody Date dateTo) {
        return  clientAnalyzerService.getCountOfUnplannedCancels(clientId, dateFrom, dateTo);
    }

    @GetMapping("/getCountOfPlannedCancels")
    public int getCountOfPlannedCancels(@RequestBody long clientId, @RequestBody Date dateFrom, @RequestBody Date dateTo) {
        return  clientAnalyzerService.getCountOfPlannedCancels(clientId, dateFrom, dateTo);
    }
    @GetMapping("/getAverageCancellationsPerMonth")
    public double getAverageCancellationsPerMonth(@RequestBody long clientId, @RequestBody Date dateFrom, @RequestBody Date dateTo) {
        return  clientAnalyzerService.getAverageCancellationsPerMonth(clientId, dateFrom, dateTo);
    }
    @GetMapping("/getAverageShiftsPerMonth")
    public double getAverageShiftsPerMonth(@RequestBody long clientId, @RequestBody Date dateFrom, @RequestBody Date dateTo) {
        return  clientAnalyzerService.getAverageShiftsPerMonth(clientId, dateFrom, dateTo);
    }
    @GetMapping("/getExpectedIncoming")
    public int getExpectedIncoming(@RequestBody long clientId, @RequestBody Date dateFrom, @RequestBody Date dateTo) {
        return clientAnalyzerService.getExpectedIncoming(clientId, dateFrom, dateTo);
    }
    @GetMapping("/getActualIncoming")
    public int getActualIncoming(@RequestBody long clientId, @RequestBody Date dateFrom, @RequestBody Date dateTo) {
        return clientAnalyzerService.getActualIncoming(clientId, dateFrom, dateTo);
    }
    @GetMapping("/getAverageLosses")
    public int getAverageLosses(@RequestBody long clientId, @RequestBody Date dateTo) {
        return clientAnalyzerService.getAverageLosses(clientId, dateTo);
    }
    @GetMapping("/getActualLosses")
    public int getActualLosses(@RequestBody long clientId, @RequestBody Date dateFrom, @RequestBody Date dateTo) {
        return clientAnalyzerService.getActualLosses(clientId, dateFrom, dateTo);
    }

    @GetMapping("/getCancellationsPercentage")
    public double getCancellationsPercentage(@RequestBody long clientId, @RequestBody Date dateFrom, @RequestBody Date dateTo) {
        return clientAnalyzerService.getCancellationsPercentage(clientId, dateFrom, dateTo);
    }
    @GetMapping("/getShiftsPercentage")
    public double getShiftsPercentage(@RequestBody long clientId, @RequestBody Date dateFrom, @RequestBody Date dateTo) {
        return clientAnalyzerService.getShiftsPercentage(clientId, dateFrom, dateTo);
    }




}
