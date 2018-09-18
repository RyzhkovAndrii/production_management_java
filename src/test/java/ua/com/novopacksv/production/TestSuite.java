package ua.com.novopacksv.production;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.com.novopacksv.production.service.roll.RollLeftOverServiceImplTest;
import ua.com.novopacksv.production.service.roll.RollManufacturedServiceImplTest;
import ua.com.novopacksv.production.service.roll.RollOperationServiceImplTest;
import ua.com.novopacksv.production.service.roll.RollTypeServiceImplTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({RollTypeServiceImplTest.class, RollOperationServiceImplTest.class,
        RollManufacturedServiceImplTest.class, RollLeftOverServiceImplTest.class})
public class TestSuite {
}
