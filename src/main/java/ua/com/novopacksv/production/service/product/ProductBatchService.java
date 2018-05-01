package ua.com.novopacksv.production.service.product;

import ua.com.novopacksv.production.model.productModel.ProductBatch;

import java.time.LocalDate;

public interface ProductBatchService {

    ProductBatch getOne(Long productTypeId, LocalDate date);

    ProductBatch getOne(Long productTypeId, LocalDate fromDate, LocalDate toDate);
}
