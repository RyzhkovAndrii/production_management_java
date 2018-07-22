package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.RangeException;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.model.userModel.TableType;
import ua.com.novopacksv.production.repository.rollRepository.RollTypeRepository;
import ua.com.novopacksv.production.service.norm.NormService;
import ua.com.novopacksv.production.service.user.TableModificationService;

import java.util.List;

/**
 * Класс имплементирует методы интерфейса {@code RollTypeService}.
 * <p>
 * Содержит бизнес логику для работы с типами рулонов производимых на передприятии.
 * <p>
 * На данный момент содержит только CRUD операции.
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RollTypeServiceImpl implements RollTypeService {

    private final static TableType TABLE_TYPE_FOR_UPDATE = TableType.ROLLS;

    /**
     * Содержит методы для работы с базой данных (DAO уровень) для сущности {@code RollType}
     */
    private final RollTypeRepository rollTypeRepository;

    /**
     * Содержит методы для работы с сущностью {@code RollLeftOver}
     */
    private final RollLeftOverService rollLeftOverService;

    /**
     * Содержит методы для работы с сущностью {@code RollCheck}
     */
    private final RollCheckService rollCheckService;

    private final NormService normService;

    private final TableModificationService tableModificationService;

    /**
     * Ищет в базе и возвращает тип произоводимого на предприятии рулона по указанному id.
     * <p>
     * Не изменяет содержимого базы данных.
     *
     * @param id ID типа рулона в базе данных, должен быть не null
     * @return тип рулона с указанным id
     * @throws ResourceNotFoundException если тип рулона с указанным id не найден
     * @throws IllegalArgumentException  если id - null
     */
    @Override
    @Transactional(readOnly = true)
    public RollType findById(Long id) throws ResourceNotFoundException {
        RollType rollType = rollTypeRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Roll type with id = %d is not found!", id);
            log.error("Method findById(Long id): Roll type with id {} is not found", id);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findById(Long id): Roll type {} with id {} is finding", rollType, id);
        return rollType;
    }

    /**
     * Ищет в базе и возвращает все типы производимых на предприятии рулонов.
     * <p>
     * Не изменяет содержимого базы данных.
     *
     * @return {@code List} всех типов рулонов или empty {@code List}, если в базе отсутствует какой-либо тип рулона
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollType> findAll() {
        List<RollType> rollTypes = rollTypeRepository.findAll();
        log.debug("Method findAll(): All roll types was found");
        return rollTypes;
    }

    /**
     * The method finds and return all roll types with pointed thickness
     * @param thickness - double value of thickness
     * @return list of roll types with pointed thickness
     */
    @Override
    public List<RollType> findAll(Double thickness) {
        List<RollType> rollTypes = rollTypeRepository.findAllByThickness(thickness);
        log.debug("Method findAll(Double thickness): All roll types with thickness = {} were found", thickness);
        return rollTypes;
    }

    /**
     * The method finds and return all roll types with pointed color
     * @param colorCode - color's code
     * @return list of roll types with pointed color
     */
    @Override
    public List<RollType> findAll(String colorCode) {
        List<RollType> rollTypes = rollTypeRepository.findAllByColorCode(colorCode);
        log.debug("Method findAll(String colorCode): All roll types with color code = {} were found", colorCode);
        return rollTypes;
    }

    /**
     * Сохраняет в базу новый тип производимого на предприятии рулона.
     * <p>
     * При сохранении также создается и сохраняется в базу остаток для данного типа рулона {@see RollLeftOver}.
     * Количество остатка для данного типа рулона устанавливается равным 0, так как никакие операции с данным
     * типом рулона еще не осуществлялись.
     *
     * @param rollType новый тип рулона, должен быть не null
     * @return новый тип рулона, с указанием присвоенного в базе id
     * @throws IllegalArgumentException если rollType - null
     */
    @Override
    public RollType save(RollType rollType) {
        checkWeightRange(rollType);
        tableModificationService.update(TABLE_TYPE_FOR_UPDATE);
        RollType entityRollType = rollTypeRepository.save(rollType);
        rollLeftOverService.createNewLeftOverAndSave(entityRollType);
        rollCheckService.createNewRollCheckAndSave(entityRollType);
        log.debug("Method save(RollType rollType): Roll type {} was saved", rollType);
        return entityRollType;
    }

    /**
     * Изменяет данные о типе производимого на предприятии рулона.
     * <p>
     * Объект передаваемый в качестве параметра должен содержать id изменяемого типа рулона.
     *
     * @param rollType измененный тип рулона, id должен быть не null
     * @return измененный тип рулона
     * @throws ResourceNotFoundException если изменяемый тип рулона не найден (по id)
     * @throws IllegalArgumentException  если rollType - null
     */
    @Override
    public RollType update(RollType rollType) throws ResourceNotFoundException {
        findById(rollType.getId());
        checkWeightRange(rollType);
        tableModificationService.update(TABLE_TYPE_FOR_UPDATE);
        log.debug("Method update(RollType rollType): Method save(RollType rollType) is calling");
        return rollTypeRepository.save(rollType);
    }

    /**
     * Удаляет из базы тип производимого на предприятии рулона по указанному id.
     * <p>
     * При удалении типа рулона также удаляется информация об остатаке рулоно данного типа.
     *
     * @param id ID типа рулона в базе данных
     * @throws ResourceNotFoundException если удаляемый тип рулона с указанным id не найден
     * @throws IllegalArgumentException  если id - null
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        RollType rollType = findById(id);
        tableModificationService.update(TABLE_TYPE_FOR_UPDATE);
        rollTypeRepository.delete(findById(id));
        normService.deleteNormsWithoutRolls();
        log.debug("Method delete(Long id): Roll type {} with id {} was deleted",rollType, id);
    }

    /**
     * Проверяет корректность диапазона веса рулона.
     * Максимальный вес должен быть равен или больше минимального
     *
     * @param rollType тип рулона с проверяемым диапазоном веса рулона
     * @throws RangeException если максимальный вес рулона равен или меньше минимального
     */
    private void checkWeightRange(RollType rollType) {
        if (rollType.getMinWeight() > rollType.getMaxWeight()) {
            log.error("Method checkWeightRange(RollType rollType): Roll type {} has incorrect range of weight", rollType);
            throw new RangeException("max weight must be equals or greater than min weight");
        }
    }

}