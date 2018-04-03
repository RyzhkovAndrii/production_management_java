package ua.com.novopacksv.production.dto.user;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;

@Getter
@Setter
public class ModificationResponse extends BaseEntityResponse {

    private Long userId;

    private String modificationDate;

    private String tableTypeName;

}
