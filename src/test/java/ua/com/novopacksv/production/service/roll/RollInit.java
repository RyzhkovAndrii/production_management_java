package ua.com.novopacksv.production.service.roll;

import ua.com.novopacksv.production.model.rollModel.OperationType;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollOperation;
import ua.com.novopacksv.production.model.rollModel.RollType;

import java.time.LocalDate;

public class RollInit {

    public RollType rollTypeInit(){
        return new RollType("", 2.7, 50.0, 52.0, 360.0, "#ffffff");
    }

    public RollType rollTypeInit2(){
        return new RollType("", 2.8, 50.0, 51.5, 380.0, "#ffffff");
    }

    public RollOperation rollOperationInit(RollType rollType){
        return new RollOperation(LocalDate.now(),rollManufacturedInit20Days(rollType), OperationType.MANUFACTURE, 123, null);
    }

    public RollOperation rollOperationInit2(RollType rollType){
        return new RollOperation(LocalDate.now(),rollManufacturedInit14Days(rollType), OperationType.MANUFACTURE, 321, null);
    }

    public RollOperation rollOperationUsedInit(RollType rollType){
        return new RollOperation(LocalDate.now(),rollManufacturedInit15Days(rollType), OperationType.USE, 100, null);
    }

    public RollManufactured rollManufacturedInit20Days(RollType rollType){
        return new RollManufactured(LocalDate.now().minusDays(20), rollType, true);
    }

    public RollManufactured rollManufacturedInit14Days(RollType rollType){
        return new RollManufactured(LocalDate.now().minusDays(14), rollType, false);
    }

    public RollManufactured rollManufacturedInit15Days(RollType rollType){
        return new RollManufactured(LocalDate.now().minusDays(15), rollType, true);
    }

}
