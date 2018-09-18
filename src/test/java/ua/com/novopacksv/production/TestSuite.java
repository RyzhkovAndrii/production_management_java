package ua.com.novopacksv.production;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.com.novopacksv.production.service.roll.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({RollTypeServiceImplTest.class, RollOperationServiceImplTest.class,
        RollManufacturedServiceImplTest.class, RollLeftOverServiceImplTest.class, RollCheckServiceImplTest.class})
public class TestSuite {
}
