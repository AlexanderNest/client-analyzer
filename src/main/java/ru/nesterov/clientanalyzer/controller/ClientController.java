package ru.nesterov.clientanalyzer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.nesterov.clientanalyzer.dao.AnalyzerDAO;
import ru.nesterov.clientanalyzer.dao.ClientAnalyzerDao;
import ru.nesterov.clientanalyzer.dao.ClientDao;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.models.ClientScheduleChanges;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientController {
    private final AnalyzerDAO analyzerDAO;
    private final ClientAnalyzerDao clientAnalyzerDao;
    private final ClientDao clientDao;

    @GetMapping("/getClientById/{id}")
    public Client getClientById (@PathVariable int id) {
        return clientDao.getClientById(id);
    }

    @GetMapping("/getCountOfMeetingsHeld")
    public int getCountOfMeetingsHeld () {
        return clientAnalyzerDao.getCountOfSuccessfulMeetings();
    }

    @GetMapping("/getCountOfUnplannedShifts")
    public List<ClientScheduleChanges> getCountOfUnplannedShifts () {
        return analyzerDAO.getClientsOfUnplannedShifts();
    }

    @GetMapping("/getCountOfPlannedShifts")
    public List<ClientScheduleChanges> getCountOfPlannedShifts () {
        return analyzerDAO.getClientsOfPlannedShifts();
    }

    @GetMapping("/getCountOfPlannedCancellations")
    public List<ClientScheduleChanges> getCountOfPlannedCancellations () {
        return analyzerDAO.getClientsWithPlannedCancellations();
    }

    @GetMapping("/getCountOfUnplannedCancellations")
    public List<ClientScheduleChanges> getCountOfUnplannedCancellations () {
        return analyzerDAO.getClientsWithPlannedCancellations();
    }
}
