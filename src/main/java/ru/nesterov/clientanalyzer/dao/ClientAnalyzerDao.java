package ru.nesterov.clientanalyzer.dao;


import org.springframework.stereotype.Repository;

import java.util.Date;
@Repository
public interface ClientAnalyzerDao {
    int getCountOfSuccessfulMeetings(Date dateFrom, Date dateTo);

    int getCountOfSuccessfulMeetings(long clientId);

    int getCountOfSuccessfulMeetings(long clientId, Date dateFrom, Date dateTo);

    int getCountOfUnplannedShifts(long clientId, Date dateFrom, Date dateTo);

    int getCountOfPlannedShifts(long clientId, Date dateFrom, Date dateTo);

//    int getCountOfChanges(TypeOfChange typeOfChange, boolean isPlanned, long clientId, Date dateFrom, Date dateTo);

    int getCountOfUnplannedCancels(long clientId, Date dateFrom, Date dateTo);

    int getCountOfPlannedCancels(long clientId, Date dateFrom, Date dateTo);

    double getAverageCancellationsPerMonth(long clientId, Date dateFrom, Date dateTo);

    double getAverageShiftsPerMonth(long clientId, Date dateFrom, Date dateTo);

    int getExpectedIncoming(long clientId, Date dateFrom, Date dateTo);

    int getActualIncoming(long clientId, Date dateFrom, Date dateTo);

    int getAverageLosses(long clientId, Date dateTo);

    int getActualLosses(long clientId, Date dateFrom, Date dateTo);

    double getCancellationsPercentage(long clientId, Date dateFrom, Date dateTo);

    double getShiftsPercentage(long clientId, Date dateFrom, Date dateTo);

    String getMostFrequentCancellationDay();

    String getMostFrequentShiftDay();

    String getMostFrequentCancellationMonth();

    String getMonthName(int month);

    String getMostFrequentShiftMonth();

    double getSuccessfulMeetingsPercentage(Date dateFrom, Date dateTo);

    int getAllClientsIncoming(Date dateFrom, Date dateTo);
}
