package ua.com.novopacksv.production.service.product;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.NotAvailableColorException;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.productModel.ProductType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductTypeServiceImplTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ProductTypeServiceImpl productTypeService;
    private ProductType productType;
    private ProductType productType1;
    private ProductInit productInit = new ProductInit();
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void init(){
        productType = productInit.productTypeInit();
        entityManager.persist(productType);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @WithMockUser("cmo")
    public void findById(){
        Assert.assertEquals(productType, productTypeService.findById(productType.getId()));
    }

    @Test
    @WithMockUser("cmo")
    public void findAll(){
        Assert.assertTrue(productTypeService.findAll().contains(productType));
    }

    @Test
    @WithMockUser("cmo")
    public void findAll_ByName(){
        Assert.assertTrue(productTypeService.findAll(productType.getName()).contains(productType));
    }

    @Test
    @WithMockUser("cmo")
    public void save(){
        productType1 = productInit.productTypeInit2();
        Assert.assertNotNull(productTypeService.save(productType1));
    }

    @Test
    @WithMockUser("cmo")
    public void update(){
        productType.setWeight(6.5);
        Assert.assertEquals(productType, productTypeService.update(productType));
    }

    @Test
    @WithMockUser("cmo")
    public void update_Negative() throws Exception{
        expectedEx.expect(NotAvailableColorException.class);
        productType.setColorCode("#0000ff");
        productTypeService.update(productType);
    }

    @Test
    @WithMockUser("cmo")
    public void update_NegativeId() throws Exception{
        expectedEx.expect(ResourceNotFoundException.class);
        productType1 = productInit.productTypeInit2();
        productType1.setId(1000000L);
        productType1.setWeight(5.0);
        productTypeService.update(productType1);
    }

    @Test
    @WithMockUser("cmo")
    public void delete(){
        expectedEx.expect(ResourceNotFoundException.class);
        productTypeService.delete(productType.getId());
        productTypeService.findById(productType.getId());
    }

}
