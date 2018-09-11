package ua.com.novopacksv.production.service.roll;

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
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Profile("test")
@Transactional
public class RollTypeServiceImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RollTypeServiceImpl rollTypeService;

    private RollType rollType = new RollType();
    private RollType rollType1 = new RollType();
    private List<RollType> rollTypes = new ArrayList<>();

    @Before
    public void initializationModels() {
        //init for 1-st model
        rollType.setColorCode("#ffffff");
        rollType.setLength(340.0);
        rollType.setMinWeight(50.0);
        rollType.setMaxWeight(51.0);
        rollType.setThickness(2.8);
        rollType.setNote("");
        entityManager.persist(rollType);
        entityManager.flush();
        entityManager.clear();
        rollTypes.add(rollType);
    }

    @Test
    public void findById() {
        RollType rollTypeExpected = rollTypeService.findById(rollType.getId());
        Assert.assertEquals(rollType, rollTypeExpected);

    }

    @Test
    public void findByIdNegative() {
        RollType rollTypeNotExpected = rollTypeService.findById(rollType.getId());
        Assert.assertNotEquals(rollTypeNotExpected, rollType1);
    }

    @Test
    public void findAll() {
        List<RollType> rollTypesExpected = rollTypeService.findAll();
        Assert.assertTrue(rollTypesExpected.containsAll(rollTypes));
    }

    @Test
    public void findAllByThickness() {
        List<RollType> rollTypesExpected = rollTypeService.findAll(rollType.getThickness());
        Assert.assertTrue(rollTypesExpected.contains(rollType));
    }

    @Test
    public void findAllByColorCode() {
        List<RollType> rollTypesExpected = rollTypeService.findAll(rollType.getThickness());
        Assert.assertTrue(rollTypesExpected.contains(rollType));
    }

    @Test
    @WithMockUser("cmo")
    public void save() {
        //init for 2-nd model
        rollType1.setColorCode("#ffffff");
        rollType1.setLength(380.0);
        rollType1.setMinWeight(50.0);
        rollType1.setMaxWeight(51.5);
        rollType1.setThickness(2.7);
        rollType1.setNote("");
        RollType rollTypeExpected = rollTypeService.save(rollType1);
        Assert.assertEquals(rollTypeExpected, rollType1);

    }

    @Test
    @WithMockUser("cmo")
    public void update() {
        rollType.setThickness(3.0);
        RollType rollTypeExpected = rollTypeService.update(rollType);
        Assert.assertEquals(rollTypeExpected, rollType);
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    @WithMockUser("cmo")
    public void delete() throws Exception {
        expectedEx.expect(ResourceNotFoundException.class);
        rollTypeService.delete(rollType.getId());
        rollTypeService.findById(rollType.getId());
    }

}
