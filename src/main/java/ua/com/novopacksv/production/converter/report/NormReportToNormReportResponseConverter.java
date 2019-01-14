package ua.com.novopacksv.production.converter.report;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.report.NormReportResponse;
import ua.com.novopacksv.production.dto.report.RollOperationResponse;
import ua.com.novopacksv.production.model.reportModel.NormReport;
import ua.com.novopacksv.production.model.rollModel.RollOperation;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

@Component
public class NormReportToNormReportResponseConverter implements Converter<NormReport, NormReportResponse> {

    @Override
    public NormReportResponse convert(NormReport source) {
        List<RollOperationResponse> rolls = getSumRollOperationResp(source.getRollOperations());
        NormReportResponse result = new NormReportResponse();
        result.setProductTypeId(source.getProductType().getId());
        result.setRolls(rolls);
        result.setProductPlanAmount(source.getProductPlanAmount());
        result.setProductActualAmount(source.getProductActualAmount());
        return result;
    }

    private RollOperationResponse convertToRollOperationResponse(RollOperation operation) {
        RollOperationResponse operationResponse = new RollOperationResponse();
        operationResponse.setRollTypeId(operation.getRollManufactured().getRollType().getId());
        operationResponse.setAmount(operation.getRollAmount());
        return operationResponse;
    }

    private List<RollOperationResponse> getSumRollOperationResp(List<RollOperation> source) {
        return source.stream()
                .map(this::convertToRollOperationResponse)
                .collect(groupingBy(RollOperationResponse::getRollTypeId,
                        summingInt(RollOperationResponse::getAmount)))
                .entrySet()
                .stream()
                .map(entity -> {
                    RollOperationResponse resp = new RollOperationResponse();
                    resp.setRollTypeId(entity.getKey());
                    resp.setAmount(entity.getValue());
                    return resp;
                })
                .collect(Collectors.toList());
    }

}
