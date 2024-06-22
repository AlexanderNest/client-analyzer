package ru.nesterov.clientanalyzer.controller;

import org.springframework.web.bind.annotation.*;
import ru.nesterov.clientanalyzer.controller.request.CreateClientRequest;
import ru.nesterov.clientanalyzer.controller.request.GetClientByIdRequest;
import ru.nesterov.clientanalyzer.models.Client;
import ru.nesterov.clientanalyzer.service.ClientDto;
import ru.nesterov.clientanalyzer.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/getClientById")
    public ClientDto getClientById(@RequestBody GetClientByIdRequest request) {
        return clientService.getClientById((int)request.getId());
    }

    @PostMapping("/createClient")
    public ClientDto createClient(@RequestBody CreateClientRequest request) {
        return clientService.save(request.getClientDto());
   }
}
