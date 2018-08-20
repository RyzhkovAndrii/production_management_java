package ua.com.novopacksv.production.service.plan;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.novopacksv.production.model.planModel.ProductPlanBatch;
import ua.com.novopacksv.production.model.productModel.ProductType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductPlanBatchServiceImplTest {

    private ProductType productType = new ProductType();
    LocalDate fromDate = LocalDate.now();
    LocalDate toDate = LocalDate.now().plusDays(1);

    @Before
    public void initializationModels() {
        productType.setName("");
        productType.setColorCode("");
        productType.setWeight(4.6);

    }

    @Test
    public void getFromRangeTest() {
        List<ProductPlanBatch> value = Stream
                .iterate(fromDate, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(fromDate, toDate))
                .map(date -> getOne(productType, date))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<ProductPlanBatch> expectedValue = new ArrayList<>();
        expectedValue.add(getOne(productType, toDate));
        Assert.assertEquals(value, expectedValue);
    }

    private ProductPlanBatch getOne(ProductType productType, LocalDate date) {
        date = toDate;
        ProductPlanBatch productPlanBatch = new ProductPlanBatch();
        productPlanBatch.setDate(date);
        productPlanBatch.setProductType(productType);
        productPlanBatch.setManufacturedAmount(1200);
        productPlanBatch.setUsedAmount(600);
        return productPlanBatch;
    }

}
