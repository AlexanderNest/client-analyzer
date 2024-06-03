package ru.nesterov.clientanalyzer.dao;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.models.TypeOfChange;

import java.sql.ResultSet;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ClientAnalyzerDaoTest extends BaseClientTest {
    @Autowired
    private ScheduleChangeDao scheduleChangeDao;
    @Autowired
    private ClientAnalyzerDao clientAnalyzerDao;

    @Test
    void getCountOfSuccessfulMeetings() {
        Client client = createClient();
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 14), null, false, TypeOfChange.CANCELLED);

        int actualCountOfSuccessfulMeetings = clientAnalyzerDao.getCountOfSuccessfulMeetings(client.getId(), new Date(2024, 05, 03), new Date(2024, 05, 17));
        assertEquals(3, actualCountOfSuccessfulMeetings);
    }

    @Test
    void getCountOfUnplannedShifts() {
        Client client = createClient();
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 14), new Date(2024, 05, 16), false, TypeOfChange.SHIFTED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 10), new Date(2024, 05, 11), true, TypeOfChange.SHIFTED);

        int actualCountOfUnplannedShifts = clientAnalyzerDao.getCountOfUnplannedShifts(client.getId(), new Date(2024, 05, 05), new Date(2024, 05, 15));
        Assertions.assertEquals(1, actualCountOfUnplannedShifts);
    }

    @Test
    void getCountOfPlannedShifts() {
        Client client = createClient();
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 14), new Date(2024, 05, 15), false, TypeOfChange.SHIFTED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 10), new Date(2024, 05, 11), true, TypeOfChange.SHIFTED);

        int actualCountOfPlannedShifts = clientAnalyzerDao.getCountOfPlannedShifts(client.getId(), new Date(2024, 05, 05), new Date(2024, 05, 20));
        Assertions.assertEquals(1, actualCountOfPlannedShifts);
    }

    @Test
    void getCountOfUnplannedCancellations() {
        Client client = createClient();
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 14), null, false, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 10), null, true, TypeOfChange.CANCELLED);

        int actualCountOfUnplannedCancellations = clientAnalyzerDao.getCountOfUnplannedCancels(client.getId(), new Date(2024, 05, 05), new Date(2024, 05, 20));
        Assertions.assertEquals(1, actualCountOfUnplannedCancellations);
    }

    @Test
    void getCountOfPlannedCancellations() {
        Client client = createClient();
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 14), null, false, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 10), null, true, TypeOfChange.CANCELLED);

        int actualCountOfPlannedCancellations = clientAnalyzerDao.getCountOfPlannedCancels(client.getId(), new Date(2024, 05, 05), new Date(2024, 05, 20));
        assertEquals(1, actualCountOfPlannedCancellations);
    }

    @Test
    void getAverageCancellationsPerMonth() {
        long date = 1709424000000L; //2024 03 03

        Client client = createClient(new Date(date));
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(1)), null, false, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(7)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(7)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(9)), null, true, TypeOfChange.CANCELLED);

        double actualAverageCancellationsPerMonth = clientAnalyzerDao.getAverageCancellationsPerMonth(client.getId(), new Date(date), new Date(date + getDaysInMillis(62)));
        assertEquals(2.5, actualAverageCancellationsPerMonth);
    }

    private long getDaysInMillis(int countOfDays) {
        long day = 86400000; // one day
        return day * countOfDays;
    }

    @Test
    void getExpectedIncoming() {
        Client client = buildDefaultClient();
        System.out.println(client.getId());

        int expectedIncoming = clientAnalyzerDao.getExpectedIncoming(client.getId(), new Date(2024, 04, 01), new Date(2024, 05, 01));
        assertEquals(8000, expectedIncoming);
    }

    @Test
    void getActualIncoming() {
        Client client = buildDefaultClient();
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 04, 10), null, false, TypeOfChange.CANCELLED);
        int actualIncoming = clientAnalyzerDao.getActualIncoming(client.getId(), new Date(2024, 04, 01), new Date(2024, 05, 01));

        assertEquals(7000, actualIncoming);
    }

    void getAverageLosses(long clientId) {

    }

    void getActualLosses() {

    }

    void getCancellationsPercentage() {

    }

    void getShiftsPercentage() {

    }

    void getChangesPercentage() {

    }
}

   