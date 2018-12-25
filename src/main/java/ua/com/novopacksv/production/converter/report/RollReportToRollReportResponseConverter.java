package ua.com.novopacksv.production.converter.report;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.report.RollReportResponse;
import ua.com.novopacksv.production.model.reportModel.RollReport;

@Component
public class RollReportToRollReportResponseConverter implements Converter<RollReport, RollReportResponse> {

    @Override
    public RollReportResponse convert(RollReport source) {
        RollReportResponse result = new RollReportResponse();
        result.setRollTypeId(source.getRollType().getId());
        result.setPlanAmount(source.getPlanAmount());
        result.setActualAmount(source.getActualAmount());
        return result;
    }

}
