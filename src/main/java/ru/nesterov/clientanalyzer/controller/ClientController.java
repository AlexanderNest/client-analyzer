package ru.nesterov.clientanalyzer.controller;

import org.springframework.web.bind.annotation.*;
import ru.nesterov.clientanalyzer.controller.request.GetClientByIdRequest;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {
    //private final ClientAnalyzerService clientAnalyzerService;
    private final ClientService clientService;
// все передавать в теле
    public ClientController (ClientService clientService) {
        this.clientService = clientService;
    }
    @GetMapping("/getClientById")
    public Client getClientById (@RequestBody GetClientByIdRequest request) {
        return clientService.getClientById((int)request.getId());
    }
}
