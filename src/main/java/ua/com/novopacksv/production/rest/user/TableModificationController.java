package ua.com.novopacksv.production.rest.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.user.TableModificationResponse;
import ua.com.novopacksv.production.model.userModel.TableModification;
import ua.com.novopacksv.production.model.userModel.TableType;
import ua.com.novopacksv.production.service.user.TableModificationService;

@RestController
@RequestMapping(value = "/modifications", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class TableModificationController {

    private final TableModificationService tableModificationService;

    private final ModelConversionService conversionService;

    @GetMapping(params = "table_type")
    public ResponseEntity<TableModificationResponse> getOne(@RequestParam(name = "table_type") TableType tableType) {
        TableModification tableModification = tableModificationService.findOne(tableType);
        TableModificationResponse response =
                conversionService.convert(tableModification, TableModificationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}