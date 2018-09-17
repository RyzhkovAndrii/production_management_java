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
import ua.com.novopacksv.production.model.rollModel.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class RollOperationServiceImplTest {

    @Autowired
    private RollOperationServiceImpl rollOperationService;
    private RollInit rollInit = new RollInit();
    private RollOperation rollOperation = new RollOperation();
    private RollOperation rollOperation1 = new RollOperation();
    private RollOperation rollOperationUsed = new RollOperation();
    private RollType rollType;
    private List<RollOperation> operationsExpected = new ArrayList<>();
    private RollLeftOver rollLeftOver;
    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void before() {
        rollType = rollInit.rollTypeInit();
        rollLeftOver = new RollLeftOver(LocalDate.now(), rollType, 0);
        rollOperation = rollInit.rollOperationInit(rollType);
        rollOperation1 = rollInit.rollOperationInit2(rollType);
        rollOperationUsed = rollInit.rollOperationUsedInit(rollType);
        entityManager.persist(rollType);
        entityManager.persist(rollLeftOver);
        entityManager.persist(rollOperation);
        entityManager.persist(rollOperation1);
        entityManager.persist(rollOperationUsed);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @WithMockUser("cmo")
    public void findAllByRollTypeAndManufacturedDateBetween() {
        operationsExpected.add(rollOperation1);
        Assert.assertTrue(rollOperationService.findAllByRollTypeAndManufacturedDateBetween(rollType,
                LocalDate.now().minusDays(18), LocalDate.now()).containsAll(operationsExpected));
    }

    @Test
    @WithMockUser("cmo")
    public void findAllByRollTypeIdAndManufacturedPeriod() {
        operationsExpected.add(rollOperation1);
        Assert.assertTrue(rollOperationService.findAllByRollTypeIdAndManufacturedPeriod(rollType.getId(),
                LocalDate.now().minusDays(18), LocalDate.now()).containsAll(operationsExpected));
    }

    @Test
    @WithMockUser("cmo")
    public void findById() {
        Assert.assertEquals(rollOperation, rollOperationService.findById(rollOperation.getId()));
    }

    @Test
    @WithMockUser("cmo")
    public void findAll() {
        operationsExpected.add(rollOperation);
        operationsExpected.add(rollOperation1);
        Assert.assertTrue(rollOperationService.findAll().containsAll(operationsExpected));
    }

    @Test
    @WithMockUser("cmo")
    public void save() {
        RollManufactured rollManufactured = new RollManufactured(LocalDate.now().minusDays(16), rollType, true);
        RollOperation rollOperation2 = new RollOperation(LocalDate.now(), rollManufactured,
                OperationType.MANUFACTURE,254, null);
        Assert.assertEquals(rollOperation2, rollOperationService.save(rollOperation2));
    }

    @Test
    @WithMockUser("cmo")
    public void update() {
        rollOperation.setRollAmount(147);
        Assert.assertEquals(rollOperation, rollOperationService.update(rollOperation));
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    @WithMockUser("cmo")
    public void delete() throws Exception {
        expectedEx.expect(ResourceNotFoundException.class);
        rollOperationService.delete(rollOperation.getId());
        rollOperationService.findById(rollOperation.getId());
    }

    @Test
    @WithMockUser("cmo")
    public void findAllManufacturedOperationsByRollManufactured() {
        operationsExpected.add(rollOperation);
        Assert.assertTrue(rollOperationService.findAllManufacturedOperationsByRollManufactured(
                rollOperation.getRollManufactured()).containsAll(operationsExpected));
    }

    @Test
    @WithMockUser("cmo")
    public void findAllUsedOperationsByRollManufactured() {
        operationsExpected.add(rollOperationUsed);
        Assert.assertTrue(rollOperationService.findAllUsedOperationsByRollManufactured(
                rollOperationUsed.getRollManufactured()).containsAll(operationsExpected));
    }

    @Test
    @WithMockUser("cmo")
    public void isItManufactureOperation() {
        Assert.assertTrue(rollOperationService.isItManufactureOperation(rollOperation));
    }
}
