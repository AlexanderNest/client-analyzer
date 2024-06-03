package ru.nesterov.clientanalyzer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nesterov.clientanalyzer.dao.ClientAnalyzerDao;

@SpringBootTest
class ClientAnalyzerApplicationTests {
    @Autowired
    private ClientAnalyzerDao clientAnalyzerDao;

    @Test
    void contextLoads() {
       clientAnalyzerDao.getCountOfSuccessfulMeetings(3 );

    }
}
