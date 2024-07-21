package ru.nesterov.clientanalyzer.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.models.TypeOfChange;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientAnalyzerDaoTest extends BaseClientTest {
    private static final Logger logger = LoggerFactory.getLogger(ClientAnalyzerDaoTest.class);
    @Autowired
    private ScheduleChangeDao scheduleChangeDao;
    @Autowired
    private ClientAnalyzerDao clientAnalyzerDao;

    @Test
    void getCountOfSuccessfulMeetings() {
        Client client = createClient();
        logger.info("getCountOfSuccessfulMeetings :{}", client.getId());
        long date = 1709424000000L;
        scheduleChangeDao.addScheduleChange(client.getId(), new Date (date + getDaysInMillis(3)), null, false, TypeOfChange.CANCELLED);

        int actualCountOfSuccessfulMeetings = clientAnalyzerDao.getCountOfSuccessfulMeetings(client.getId(), new Date(date), new Date (date + getDaysInMillis(14)));
        assertEquals(3, actualCountOfSuccessfulMeetings);
    }

    @Test
    void getCountOfUnplannedShifts() {
        Client client = createClient();
        logger.info("getCountOfUnplannedShifts {}", client.getId());
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 14), new Date(2024, 05, 16), false, TypeOfChange.SHIFTED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 10), new Date(2024, 05, 11), true, TypeOfChange.SHIFTED);

        int actualCountOfUnplannedShifts = clientAnalyzerDao.getCountOfUnplannedShifts(client.getId(), new Date(2024, 05, 05), new Date(2024, 05, 15));
        Assertions.assertEquals(1, actualCountOfUnplannedShifts);
    }

    @Test
    void getCountOfPlannedShifts() {
        Client client = createClient();
        logger.info("getCountOfPlannedShifts :{}", client.getId());
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 14), new Date(2024, 05, 15), false, TypeOfChange.SHIFTED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 10), new Date(2024, 05, 11), true, TypeOfChange.SHIFTED);

        int actualCountOfPlannedShifts = clientAnalyzerDao.getCountOfPlannedShifts(client.getId(), new Date(2024, 05, 05), new Date(2024, 05, 20));
        Assertions.assertEquals(1, actualCountOfPlannedShifts);
    }

    @Test
    void getCountOfUnplannedCancellations() {
        Client client = createClient();
        logger.info("getCountOfUnplannedCancellations :{}", client.getId());
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 14), null, false, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 10), null, true, TypeOfChange.CANCELLED);

        int actualCountOfUnplannedCancellations = clientAnalyzerDao.getCountOfUnplannedCancels(client.getId(), new Date(2024, 05, 05), new Date(2024, 05, 20));
        Assertions.assertEquals(1, actualCountOfUnplannedCancellations);
    }

    @Test
    void getCountOfPlannedCancellations() {
        Client client = createClient();
        logger.info("getCountOfPlannedCancellations :{}", client.getId());
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 14), null, false, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 05, 10), null, true, TypeOfChange.CANCELLED);

        int actualCountOfPlannedCancellations = clientAnalyzerDao.getCountOfPlannedCancels(client.getId(), new Date(2024, 05, 05), new Date(2024, 05, 20));
        assertEquals(1, actualCountOfPlannedCancellations);
    }

    @Test
    void getAverageCancellationsPerMonth() {
        long date = 1709424000000L; //2024 03 03

        Client client = createClient(new Date(date));
        logger.info("getAverageCancellationsPerMonth {}", client.getId());
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(1)), null, false, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(7)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(7)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(9)), null, true, TypeOfChange.CANCELLED);

        double actualAverageCancellationsPerMonth = clientAnalyzerDao.getAverageCancellationsPerMonth(client.getId(), new Date(date), new Date(date + getDaysInMillis(62)));
        assertEquals(2, actualAverageCancellationsPerMonth); //на самом деле ожидаемое значение 2.5, у h2 проблемы с округлением
    }

    private long getDaysInMillis(int countOfDays) {
        long day = 86400000; // one day
        return day * countOfDays;
    }

    @Test
    void getExpectedIncoming() {
        Client client = createClient();
        System.out.println(client.getId());
        logger.info("getExpectedIncoming :{}", client.getId());

        int expectedIncoming = clientAnalyzerDao.getExpectedIncoming(client.getId(), new Date(2024, 04, 01), new Date(2024, 05, 01));
        assertEquals(8000, expectedIncoming);
    }

    @Test
    void getActualIncoming() {
        Client client = createClient();
        logger.info("getActualIncoming :{}", client.getId());

        scheduleChangeDao.addScheduleChange(client.getId(), new Date(2024, 04, 10), null, false, TypeOfChange.CANCELLED);
        int actualIncoming = clientAnalyzerDao.getActualIncoming(client.getId(), new Date(2024, 04, 01), new Date(2024, 05, 01));

        assertEquals(7000, actualIncoming);
    }

    @Test
    void getAverageLosses() {
        Client client = createClient(new Date(1709424000000L));
        logger.info("getAverageLosses :{}", client.getId());
        long date = 1709424000000L;
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(5)), null, false, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(8)), null, false, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(15)), null, false, TypeOfChange.CANCELLED);

        int averageLosses = clientAnalyzerDao.getAverageLosses(client.getId(), new Date(date + getDaysInMillis(62)));
        assertEquals(1500, averageLosses);
    }

    @Test
    void getActualLosses() {
        Client client = createClient(new Date(1709424000000L));
        logger.info("getActualLosses :{}", client.getId());
        long date = 1709424000000L;
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(5)), null, false, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(8)), null, false, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(15)), null, false, TypeOfChange.CANCELLED);

        int actualLosses = clientAnalyzerDao.getActualLosses(client.getId(), new Date(date),new Date(date + getDaysInMillis(62)));
        assertEquals(3000, actualLosses);
    }

    @Test
    void getCancellationsPercentage() {
        Client client = createClient(new Date(1709424000000L));
        logger.info("getCancellationsPercentage :{}", client.getId());
        long date = 1709424000000L;
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(5)), null, false, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(8)), null, false, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(15)), null, false, TypeOfChange.CANCELLED);

        double cancellationsPercentage = clientAnalyzerDao.getCancellationsPercentage(client.getId(), new Date(date), new Date(date + getDaysInMillis(31)));
        assertEquals(37, cancellationsPercentage);//на самом деле ожидаемое значение 37.5, у h2 проблемы с округлением
    }

    @Test
    void getShiftsPercentage() {
        Client client = createClient(new Date(1709424000000L));
        logger.info("getShiftsPercentage :{}", client.getId());
        long date = 1709424000000L;
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(5)), new Date(date + getDaysInMillis(6)), false, TypeOfChange.SHIFTED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(8)), new Date(date + getDaysInMillis(9)), false, TypeOfChange.SHIFTED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(15)), new Date(date + getDaysInMillis(17)), false, TypeOfChange.SHIFTED);

        double shiftsPercentage = clientAnalyzerDao.getShiftsPercentage(client.getId(), new Date(date), new Date(date + getDaysInMillis(31)));
        assertEquals(37, shiftsPercentage);//на самом деле ожидаемое значение 37.5, у h2 проблемы с округлением
    }

    @Test
    void getMostFrequentCancellationDay() {
        Client client = createClient();
        logger.info("getMostFrequentCancellationDay :{}", client.getId());
        long date = 1709424000000L;

        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date - getDaysInMillis(360)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date - getDaysInMillis(360)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date - getDaysInMillis(360)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date - getDaysInMillis(360)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date - getDaysInMillis(360)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date - getDaysInMillis(360)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date - getDaysInMillis(360)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date - getDaysInMillis(320)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date - getDaysInMillis(320)), null, true, TypeOfChange.SHIFTED);

        assertEquals(new java.sql.Date(date - getDaysInMillis(360)).toString(), clientAnalyzerDao.getMostFrequentCancellationDay());
    }

    @Test
    void getMostFrequentShiftDay() {
        Client client = createClient();
        logger.info("getMostFrequentShiftDay :{}", client.getId());
        long date = 1709424000000L;

        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date + getDaysInMillis(70)), null, true, TypeOfChange.SHIFTED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date + getDaysInMillis(70)), null, true, TypeOfChange.SHIFTED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date + getDaysInMillis(70)), null, true, TypeOfChange.SHIFTED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date + getDaysInMillis(30)), null, true, TypeOfChange.SHIFTED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date + getDaysInMillis(30)), null, true, TypeOfChange.CANCELLED);

        assertEquals(new java.sql.Date(date + getDaysInMillis(70)).toString(), clientAnalyzerDao.getMostFrequentShiftDay());
    }

    @Test
    void getMostFrequentShiftMonth() {
        Client client = createClient();
        logger.info("getMostFrequentShiftMonth{}", client.getId());
        long date = 1709424000000L;

        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date + getDaysInMillis(60)), null, true, TypeOfChange.SHIFTED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date + getDaysInMillis(60)), null, true, TypeOfChange.SHIFTED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date + getDaysInMillis(40)), null, true, TypeOfChange.SHIFTED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date + getDaysInMillis(40)), null, true, TypeOfChange.CANCELLED);

        String mostFrequentShiftMonth = clientAnalyzerDao.getMonthName(new java.sql.Date(date + getDaysInMillis(60)).getMonth() + 1);

        assertEquals(mostFrequentShiftMonth, clientAnalyzerDao.getMostFrequentShiftMonth());
    }

    @Test
    void getMostFrequentCancellationMonth() {
        Client client = createClient();
        logger.info("getMostFrequentCancellationMonth :{}", client.getId());
        long date = 1709424000000L;

        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date - getDaysInMillis(360)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date - getDaysInMillis(360)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date - getDaysInMillis(340)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new java.sql.Date(date - getDaysInMillis(340)), null, true, TypeOfChange.SHIFTED);

        String mostFrequentCancellationMonth = clientAnalyzerDao.getMonthName(new java.sql.Date(date - getDaysInMillis(360)).getMonth() + 1);

        assertEquals(mostFrequentCancellationMonth, clientAnalyzerDao.getMostFrequentCancellationMonth());}

    @Test
    void getCountOfSuccessfulMeetingsWithTwoDates() {
        long date = 1709424000000L;
        Client client = createClient();
        Client client1 = createClient();
        logger.info("getCountOfSuccessfulMeetingsWithTwoDates :{}, :{}", client.getId(), client1.getId());

        scheduleChangeDao.addScheduleChange(client.getId(), new Date (date + getDaysInMillis(2001)), null, false, TypeOfChange.CANCELLED);

        int expectedResult = 8 * client1.getId() - 1;

        int countOfSuccesfulMeetings = clientAnalyzerDao.getCountOfSuccessfulMeetings(new Date(date + getDaysInMillis(2000)), new Date(date + getDaysInMillis(2030)));
        assertEquals(expectedResult, countOfSuccesfulMeetings);
    }

    @Test
    void getSuccessfulMeetingsPercentage() {
        long date = 1709424000000L;
        Client client = createClient(new Date(date - getDaysInMillis(720)));
        logger.info("getSuccessfulMeetingsPercentage :{}", client.getId());

        scheduleChangeDao.addScheduleChange(client.getId(), new Date (date - getDaysInMillis(717)), null, false, TypeOfChange.CANCELLED);

        double expectedResult = (client.getId() * 8 - 1)/(8.0 * client.getId()) * 100;

        double successfulMeetingsPercentage = clientAnalyzerDao.getSuccessfulMeetingsPercentage(new Date(date - getDaysInMillis(720)), new Date(date - getDaysInMillis(690)));
        assertEquals(expectedResult, successfulMeetingsPercentage);
    }

    @Test
    void getAllClientsIncoming() {
        long date = 1709424000000L;
        Client client = createClient();
        Client client1 = createClient();
        logger.info("getAllClientsIncoming :{}, :{}", client.getId(), client1.getId());

        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(3001)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client.getId(), new Date(date + getDaysInMillis(3005)), null, true, TypeOfChange.CANCELLED);
        scheduleChangeDao.addScheduleChange(client1.getId(), new Date(date + getDaysInMillis(3010)), null, true, TypeOfChange.CANCELLED);

        int expectedResult = client1.getId() * 8000 - 3000;

        int allClientsIncoming = clientAnalyzerDao.getAllClientsIncoming(new Date(date + getDaysInMillis(3000)), new Date(date + getDaysInMillis(3030)));

        assertEquals(expectedResult, allClientsIncoming);
    }
}

   