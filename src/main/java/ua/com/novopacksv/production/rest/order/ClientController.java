package ua.com.novopacksv.production.rest.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.order.ClientRequest;
import ua.com.novopacksv.production.dto.order.ClientResponse;
import ua.com.novopacksv.production.model.orderModel.Client;
import ua.com.novopacksv.production.service.order.ClientService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "${spring.rest.api-url-prefix}/clients", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_CMO', 'ROLE_ECONOMIST', 'ROLE_FULL_ACCESS')")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    private final ModelConversionService conversionService;

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAll(@RequestParam(name="sort", defaultValue = "id") String sort) {
        List<Client> clients = clientService.findAll(sort);
        List<ClientResponse> response = conversionService.convert(clients, ClientResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getOne(@PathVariable Long id) {
        Client client = clientService.findById(id);
        ClientResponse response = conversionService.convert(client, ClientResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_FULL_ACCESS')")
    public ResponseEntity<ClientResponse> save(@Valid @RequestBody ClientRequest request) {
        Client client = conversionService.convert(request, Client.class);
        client = clientService.save(client);
        ClientResponse response = conversionService.convert(client, ClientResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_FULL_ACCESS')")
    public ResponseEntity<ClientResponse> update(@PathVariable Long id, @Valid @RequestBody ClientRequest request) {
        Client client = conversionService.convert(request, Client.class);
        client.setId(id);
        client = clientService.update(client);
        ClientResponse response = conversionService.convert(client, ClientResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_FULL_ACCESS')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        clientService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}