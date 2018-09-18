package ua.com.novopacksv.production.service.roll;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.rollModel.RollBatch;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class RollBatchServiceImplTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private RollBatchServiceImpl rollBatchService;
    private RollType rollType;
    private RollManufactured rollManufactured;
    private RollInit rollInit = new RollInit();

    @Before
    public void init() {
        rollType = rollInit.rollTypeInit();
        rollManufactured = rollInit.rollManufacturedInit15Days(rollType);
        entityManager.persist(rollType);
        entityManager.persist(rollManufactured);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @WithMockUser("cmo")
    public void getOne() {
        Assert.assertNotNull(rollBatchService.getOne(rollType.getId(), rollManufactured.getManufacturedDate()));
    }

    @Test
    @WithMockUser("cmo")
    public void getAll_ByManufactureDate() {
        Assert.assertTrue(rollBatchService.getAll(rollManufactured.getManufacturedDate())
                .stream()
                .filter((p) -> p.getRollManufactured().equals(rollManufactured))
                .map(RollBatch::getRollManufactured)
                .collect(Collectors.toList())
                .contains(rollManufactured));
    }

    @Test
    @WithMockUser("cmo")
    public void getAll_ByManufacturePeriod() {
        Assert.assertTrue(rollBatchService.getAll(rollManufactured.getManufacturedDate().minusDays(10),
                rollManufactured.getManufacturedDate().plusDays(10))
                .stream()
                .filter((p) -> p.getRollManufactured().equals(rollManufactured))
                .map(RollBatch::getRollManufactured)
                .collect(Collectors.toList())
                .contains(rollManufactured));
    }

    @Test
    @WithMockUser("cmo")
    public void getAll_ByRollTypeIdAndManufacturePeriod() {
        Assert.assertTrue(rollBatchService.getAll(rollManufactured.getRollType().getId(),
                rollManufactured.getManufacturedDate().minusDays(10),
                rollManufactured.getManufacturedDate().plusDays(10))
                .stream()
                .filter((p) -> p.getRollManufactured().equals(rollManufactured))
                .map(RollBatch::getRollManufactured)
                .collect(Collectors.toList())
                .contains(rollManufactured));
    }

}
