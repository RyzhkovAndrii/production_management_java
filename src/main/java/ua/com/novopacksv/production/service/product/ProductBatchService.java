package ua.com.novopacksv.production.service.product;

import ua.com.novopacksv.production.model.productModel.ProductBatch;

import java.time.LocalDate;
import java.util.List;

public interface ProductBatchService {

    ProductBatch getOne(Long productTypeId, LocalDate date);

    List<ProductBatch> getAll(LocalDate date);

    List<ProductBatch> getAll(LocalDate fromDate, LocalDate toDate);

    ProductBatch getOne(Long productTypeId, LocalDate fromDate, LocalDate toDate);
}
