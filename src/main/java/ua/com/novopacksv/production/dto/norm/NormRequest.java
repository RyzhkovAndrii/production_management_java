package ua.com.novopacksv.production.dto.norm;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NormRequest {

    private List<Long> rollTypeIds;

    private Long productTypeId;

    private Double norm;
}
