package ua.com.novopacksv.production.service.product;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.productModel.ProductBatch;
import ua.com.novopacksv.production.model.productModel.ProductOperation;
import ua.com.novopacksv.production.model.productModel.ProductType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductBatchServiceImplTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ProductBatchServiceImpl productBatchService;
    private ProductType productType;
    private ProductOperation productOperation;
    private ProductInit productInit = new ProductInit();

    @Before
    public void init() {
        productType = productInit.productTypeInit();
        productOperation = productInit.productOperationInit(productType);
        entityManager.persist(productType);
        entityManager.persist(productOperation);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @WithMockUser("cmo")
    public void getOne_byProductTypeIdAndDate() {
        Assert.assertEquals(productBatchService.getOne(productType.getId(), productOperation.getDate())
                .getManufacturedAmount(), productOperation.getAmount());
    }

    @Test
    @WithMockUser("cmo")
    public void getOne_byProductTypeIdAndPeriod() {
        Assert.assertEquals(productBatchService.getOne(productType.getId(),
                productOperation.getDate().minusDays(2), productOperation.getDate().plusDays(2))
                .getManufacturedAmount(), productOperation.getAmount());
    }

    @Test
    @WithMockUser("cmo")
    public void getAll_onDate() {
        Assert.assertTrue(productBatchService.getAll(productOperation.getDate())
                .stream()
                .filter(p -> p.getProductType().equals(productType))
                .mapToInt(ProductBatch::getManufacturedAmount)
                .sum() == productOperation.getAmount());
    }

    @Test
    @WithMockUser("cmo")
    public void getAll_forPeriod() {
        Assert.assertTrue(productBatchService.getAll(productOperation.getDate().minusDays(2),
                productOperation.getDate().plusDays(2))
                .stream()
                .filter(p -> p.getProductType().equals(productType))
                .mapToInt(ProductBatch::getManufacturedAmount)
                .sum() == productOperation.getAmount());
    }
}
