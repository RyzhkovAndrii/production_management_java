package ua.com.novopacksv.production.dto.norm;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;

import java.util.List;

@Getter
@Setter
public class NormResponse {

    private List<Long> rollTypeIds;

    private Long productTypeId;

    private Integer norm;
}
