package ua.com.novopacksv.production.converter.report;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.report.NormReportResponse;
import ua.com.novopacksv.production.model.reportModel.NormReport;

@Component
public class NormReportToNormReportResponseConverter implements Converter<NormReport, NormReportResponse> {

    @Override
    public NormReportResponse convert(NormReport source) {
        NormReportResponse result = new NormReportResponse();
        result.setProductTypeId(source.getProductType().getId());
        result.setRollTypeId(source.getRollType().getId());
        result.setRollAmount(source.getRollAmount());
        result.setProductPlanAmount(source.getProductPlanAmount());
        result.setProductActualAmount(source.getProductActualAmount());
        return result;
    }

}
