package ua.com.novopacksv.production.rest.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.order.ClientRequest;
import ua.com.novopacksv.production.dto.order.ClientResponse;
import ua.com.novopacksv.production.model.orderModel.Client;
import ua.com.novopacksv.production.service.order.ClientService;

import java.util.List;

@RestController
@RequestMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    private final ModelConversionService conversionService;

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getList() {
        List<Client> clients = clientService.findAll();
        List<ClientResponse> response = conversionService.convert(clients, ClientResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getById(@PathVariable Long id) {
        Client client = clientService.findById(id);
        ClientResponse response = conversionService.convert(client, ClientResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClientResponse> save(@RequestBody ClientRequest request) {
        Client client = conversionService.convert(request, Client.class);
        client = clientService.save(client);
        ClientResponse response = conversionService.convert(client, ClientResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable Long id, @RequestBody ClientRequest request) {
        Client client = conversionService.convert(request, Client.class);
        client.setId(id);
        client = clientService.update(client);
        ClientResponse response = conversionService.convert(client, ClientResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        clientService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}