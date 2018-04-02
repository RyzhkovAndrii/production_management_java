package ua.com.novopacksv.production.rest.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.novopacksv.production.service.order.ClientService;
import ua.com.novopacksv.production.converter.ModelConversionService;

@RestController
@RequestMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    private final ModelConversionService conversionService;

}
