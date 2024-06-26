package ru.nesterov.clientanalyzer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nesterov.clientanalyzer.controller.request.CreateClientRequest;
import ru.nesterov.clientanalyzer.controller.request.GetClientByIdRequest;
import ru.nesterov.clientanalyzer.controller.response.CreateClientResponse;
import ru.nesterov.clientanalyzer.controller.response.GetClientByIdResponse;
import ru.nesterov.clientanalyzer.service.ClientService;
import ru.nesterov.clientanalyzer.service.mapper.ClientMapper;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @GetMapping("/getClientById")
    public GetClientByIdResponse getClientById(@RequestBody GetClientByIdRequest request) {
        return clientMapper.mapToGetClientByIdResponse(clientService.getClientById(request.getClientId()));
    }

    @PostMapping("/createClient")
    public CreateClientResponse createClient(@RequestBody CreateClientRequest request) {
        return clientMapper.mapToCreateClientResponse(clientService.save(clientMapper.mapToClientDto(request)));
   }
}
