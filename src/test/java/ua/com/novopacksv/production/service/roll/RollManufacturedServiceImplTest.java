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
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class RollManufacturedServiceImplTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private RollManufacturedService rollManufacturedService;
    private RollInit rollInit = new RollInit();
    private RollType rollType;
    private RollType rollType2;
    private RollManufactured rollManufactured;
    private RollManufactured rollManufactured1;

    @Before
    public void init() {
        rollType = rollInit.rollTypeInit();
        rollType2 = rollInit.rollTypeInit2();
        rollManufactured = rollInit.rollManufacturedInit15Days(rollType);
        rollManufactured1 = rollInit.rollManufacturedInit14Days(rollType);
        entityManager.persist(rollType);
        entityManager.persist(rollType2);
        entityManager.persist(rollManufactured);
        entityManager.persist(rollManufactured1);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @WithMockUser("cmo")
    public void findOne() {
        Assert.assertEquals(rollManufactured, rollManufacturedService
                .findOne(rollManufactured.getManufacturedDate(), rollType.getId()));
    }

    @Test
    @WithMockUser("cmo")
    public void findOneOrCreate() {
        Assert.assertEquals(rollManufactured, rollManufacturedService
                .findOneOrCreate(rollManufactured.getManufacturedDate(), rollType));
    }

    @Test
    @WithMockUser("cmo")
    public void findOneOrCreate_ForCreate() {
        Assert.assertEquals(rollType2, rollManufacturedService
                .findOneOrCreate(rollManufactured.getManufacturedDate(), rollType2).getRollType());
    }

    @Test
    @WithMockUser("cmo")
    public void findAll_ByRollTypeId() {
        Assert.assertTrue(rollManufacturedService.findAll(rollType.getId()).contains(rollManufactured));
    }

    @Test
    @WithMockUser("cmo")
    public void findAll_ByManufacturedDate(){
        Assert.assertTrue(rollManufacturedService.findAll(rollManufactured.getManufacturedDate()).contains(rollManufactured));
    }

    @Test
    @WithMockUser("cmo")
    public void findAll_ByPeriodManufacturedDates() {
        Assert.assertTrue(rollManufacturedService.findAll(rollManufactured.getManufacturedDate().minusDays(2),
                rollManufactured.getManufacturedDate().plusDays(2)).contains(rollManufactured));
    }

    @Test
    @WithMockUser("cmo")
    public void findAll_ByPeriodManufacturedDatesAndRollTypeId() {
        Assert.assertTrue(rollManufacturedService.findAll(rollManufactured.getManufacturedDate().minusDays(2),
                rollManufactured.getManufacturedDate().plusDays(2), rollType.getId()).contains(rollManufactured));
    }

    @Test
    @WithMockUser("cmo")
    public void getManufacturedRollAmount(){
        Assert.assertTrue(0 == rollManufacturedService.getManufacturedRollAmount(rollManufactured));
    }

    @Test
    @WithMockUser("cmo")
    public void getUsedRollAmount(){
        Assert.assertTrue(0 == rollManufacturedService.getUsedRollAmount(rollManufactured));
    }

    @Test
    @WithMockUser("cmo")
    public void getLeftOverAmount(){
        Assert.assertTrue(0 == rollManufacturedService.getLeftOverAmount(rollManufactured));
    }

    @Test
    @WithMockUser("cmo")
    public void setReadyToUseTrue(){
        rollManufacturedService.setReadyToUseTrue(LocalDate.now().minusDays(40), LocalDate.now().minusDays(13));
        Assert.assertTrue(rollManufacturedService
                .findOne(rollManufactured1.getManufacturedDate(), rollManufactured1.getRollType().getId()).getReadyToUse());
    }
}
