package ua.com.novopacksv.production.converter.report;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.report.ProductReportResponse;
import ua.com.novopacksv.production.model.reportModel.ProductReport;

@Component
public class ProductReportToProductReportResponseConverter implements Converter<ProductReport, ProductReportResponse> {

    @Override
    public ProductReportResponse convert(ProductReport source) {
        ProductReportResponse result = new ProductReportResponse();
        result.setProductTypeId(source.getProductType().getId());
        result.setPlanAmount(source.getPlanAmount());
        result.setActualAmount(source.getActualAmount());
        return result;
    }

}
