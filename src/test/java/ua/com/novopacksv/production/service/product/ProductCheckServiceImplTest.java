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
import ua.com.novopacksv.production.model.productModel.ProductCheck;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.model.rollModel.CheckStatus;

import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductCheckServiceImplTest {

    @Autowired
    private ProductCheckServiceImpl productCheckService;
    @Autowired
    private ProductTypeService productTypeService;
    private ProductInit productInit = new ProductInit();
    private ProductType productType;

    @Before
    public void init() {
        productType = productInit.productTypeInit();
        productTypeService.save(productType); // I need this action for create new check for this productType
    }

    @Test
    @WithMockUser("accounter")
    public void findOneByProductTypeId() {
        Assert.assertNotNull(productCheckService.findOneByProductTypeId(productType.getId()));
    }

    @Test
    @WithMockUser("accounter")
    public void findAll() {
        Assert.assertTrue(productCheckService.findAll()
                .stream()
                .filter((p) -> p.getProductType().equals(productType))
                .map(ProductCheck::getProductType)
                .collect(Collectors.toList())
                .contains(productType));
    }

    @Test
    @WithMockUser("accounter")
    public void update() {
        ProductCheck productCheck = productCheckService.findOneByProductTypeId(productType.getId());
        productCheck.setProductLeftOverCheckStatus(CheckStatus.CONFIRMED);
        Assert.assertEquals(productCheckService.update(productCheck).getProductLeftOverCheckStatus(),
                CheckStatus.CONFIRMED);
    }

    @Test
    @WithMockUser("accounter")
    public void createNewProductCheckAndSave() {
        ProductType productTypeNew = productInit.productTypeInit2();
        productCheckService.createNewProductCheckAndSave(productTypeNew);
        Assert.assertTrue(productCheckService.findAll()
                .stream()
                .filter((p) -> p.getProductType().equals(productTypeNew))
                .map(ProductCheck::getProductType)
                .collect(Collectors.toList())
                .contains(productTypeNew));
    }

    @Test
    @WithMockUser("accounter")
    public void setNotCheckedStatusForAll(){
        productCheckService.findOneByProductTypeId(
                productType.getId()).setProductLeftOverCheckStatus(CheckStatus.CONFIRMED);
        productCheckService.setNotCheckedStatusForAll();
        Assert.assertTrue(productCheckService.findAll()
                .stream()
                .filter(p -> !p.getProductLeftOverCheckStatus().equals(CheckStatus.NOT_CHECKED))
        .collect(Collectors.toList())
        .isEmpty());
    }
}
