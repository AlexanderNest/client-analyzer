package ru.nesterov.clientanalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nesterov.clientanalyzer.dao.ClientAnalyzerDao;


import java.util.Date;

@Service
public class ClientAnalyzerService {
    private final ClientAnalyzerDao clientAnalyzerDao;

    @Autowired
    public ClientAnalyzerService (ClientAnalyzerDao clientAnalyzerDao) {
        this.clientAnalyzerDao = clientAnalyzerDao;
    }

    public int getCountOfSuccessfulMeetings() {
        return clientAnalyzerDao.getCountOfSuccessfulMeetings();
    }

    public int getCountOfSuccessfulMeetings(long id) {
        return clientAnalyzerDao.getCountOfSuccessfulMeetings(id);
    }

    public int getCountOfSuccessfulMeetings(long clientId, Date dateFrom, Date dateTo) {
        return clientAnalyzerDao.getCountOfSuccessfulMeetings(clientId, dateFrom, dateTo);
    }

    public int getCountOfUnplannedShifts(long clientId, Date dateFrom, Date dateTo) {
        return  clientAnalyzerDao.getCountOfUnplannedShifts(clientId, dateFrom, dateTo);
    }

    public int getCountOfPlannedShifts(long clientId, Date dateFrom, Date dateTo) {
        return  clientAnalyzerDao.getCountOfPlannedShifts(clientId, dateFrom, dateTo);
    }

    public int getCountOfUnplannedCancels(long clientId, Date dateFrom, Date dateTo) {
        return clientAnalyzerDao.getCountOfUnplannedCancels(clientId, dateFrom, dateTo);
    }

    public int getCountOfPlannedCancels(long clientId, Date dateFrom, Date dateTo) {
        return clientAnalyzerDao.getCountOfPlannedCancels(clientId, dateFrom, dateTo);
    }

    public double getAverageCancellationsPerMonth(long clientId, Date dateFrom, Date dateTo) {
        return  clientAnalyzerDao.getAverageCancellationsPerMonth(clientId, dateFrom, dateTo);
    }

    public double getAverageShiftsPerMonth(long clientId, Date dateFrom, Date dateTo) {
        return  clientAnalyzerDao.getAverageShiftsPerMonth(clientId, dateFrom, dateTo);
    }

    public int getExpectedIncoming(long clientId, Date dateFrom, Date dateTo) {
        return clientAnalyzerDao.getExpectedIncoming(clientId, dateFrom, dateTo);
    }

    public int getActualIncoming(long clientId, Date dateFrom, Date dateTo) {
        return clientAnalyzerDao.getActualIncoming(clientId, dateFrom, dateTo);
    }

    public int getAverageLosses(long clientId, Date dateTo) {
        return clientAnalyzerDao.getAverageLosses(clientId, dateTo);
    }

    public int getActualLosses(long clientId, Date dateFrom, Date dateTo) {
        return clientAnalyzerDao.getActualLosses(clientId, dateFrom, dateTo);
    }

    public double getCancellationsPercentage(long clientId, Date dateFrom, Date dateTo) {
        return clientAnalyzerDao.getCancellationsPercentage(clientId, dateFrom, dateTo);
    }

    public double getShiftsPercentage(long clientId, Date dateFrom, Date dateTo) {
        return clientAnalyzerDao.getShiftsPercentage(clientId, dateFrom, dateTo);
    }

    public String getMostFrequentCancellationDay() {
        return clientAnalyzerDao.getMostFrequentCancellationDay();
    }

    public String getMostFrequentShiftDay() { return clientAnalyzerDao.getMostFrequentShiftDay(); }

    public String getMostFrequentCancellationMonth() { return clientAnalyzerDao.getMostFrequentCancellationMonth(); }

    public String getMostFrequentShiftMonth() { return clientAnalyzerDao.getMostFrequentShiftMonth(); }

    public double getSuccessfulMeetingsPercentage() { return clientAnalyzerDao.getSuccessfulMeetingsPercentage(); }

    public int getAllClientsIncoming(Date dateFrom, Date dateTo) { return clientAnalyzerDao.getAllClientsIncoming(dateFrom, dateTo); }
}
