package ru.nesterov.clientanalyzer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nesterov.clientanalyzer.dao.ClientAnalyzerDAO;
import ru.nesterov.clientanalyzer.models.Client;

@RestController
@RequiredArgsConstructor
public class ClientController {
    private final ClientAnalyzerDAO clientAnalyzerDAO;

    @GetMapping("/getClientById")
    public Client getClientById (int id) {
        return clientAnalyzerDAO.getClientById(id);
    }

}
