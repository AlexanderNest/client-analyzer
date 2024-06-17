package ru.nesterov.clientanalyzer.service;

import org.springframework.stereotype.Service;
import ru.nesterov.clientanalyzer.dao.AnalyzerDao;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.models.ClientScheduleChanges;

import java.util.List;

@Service
public class AnalyzerService {
    private final AnalyzerDao analyzerDao;

    public AnalyzerService (AnalyzerDao analyzerDao) {
        this.analyzerDao = analyzerDao;
    }

    public List<ClientScheduleChanges> getClientsOfUnplannedShifts() {
        return analyzerDao.getClientsOfUnplannedShifts();
    }

    public List<ClientScheduleChanges> getClientsOfPlannedShifts() {
        return analyzerDao.getClientsOfPlannedShifts();
    }

    public List<ClientScheduleChanges> getClientsWithUnplannedCancellations() {
        return analyzerDao.getClientsWithUnplannedCancellations();
    }

    public List<ClientScheduleChanges> getClientsWithPlannedCancellations() {
        return analyzerDao.getClientsWithPlannedCancellations();
    }

}
