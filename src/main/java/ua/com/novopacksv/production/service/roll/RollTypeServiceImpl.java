package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.NotUniqueFieldException;
import ua.com.novopacksv.production.exception.RangeException;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.repository.rollRepository.RollTypeRepository;

import java.util.List;
import java.util.Objects;

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
public class RollTypeServiceImpl implements RollTypeService {

    /**
     * Содержит методы для работы с базой данных (DAO уровень) для сущности {@code RollType}
     */
    private final RollTypeRepository rollTypeRepository;

    /**
     * Содержит методы для работы с сущностью {@code RollLeftOver}
     */
    private final RollLeftOverService rollLeftOverService;

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
        return rollTypeRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Roll type whit id = %d is not found!", id);
            return new ResourceNotFoundException(message);
        });
    }

    /**
     * Ищет в базе и возвращает тип произоводимого на предприятии рулона по указанному имени.
     * <p>
     * Не изменяет содержимого базы данных.
     *
     * @param name имя типа рулона в базе данных, должен быть не null
     * @return тип рулона с указанным именем
     * @throws ResourceNotFoundException если тип рулона с указанным именем не найден
     * @throws IllegalArgumentException  если name - null
     */
    @Override
    @Transactional(readOnly = true)
    public RollType findOne(String name) throws ResourceNotFoundException {
        return rollTypeRepository.findByName(name).orElseThrow(() -> {
            String message = String.format("Roll type whit name = %s is not found!", name);
            return new ResourceNotFoundException(message);
        });
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
        return rollTypeRepository.findAll();
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
        checkNameUnique(rollType);
        RollType entityRollType = rollTypeRepository.save(rollType);
        rollLeftOverService.createNewLeftOverAndSave(entityRollType);
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
        checkNameUnique(rollType);
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
        rollTypeRepository.delete(findById(id));
    }

    /**
     * Проверяет существует ли в базе данных тип рулона с таким же именем, как и у передаваемого в качестве параметра
     * типа рулона.
     * <p>
     * Если такое имя есть в базе данных и создается новый рулон, то бросается исключение.
     * Если такое имя есть в базе, но происходит операция update для типа рулона (id равны), то исключение не бросается
     *
     * @param rollType тип рулона с проверяемям на уникальность именем
     * @throws NotUniqueFieldException если имя типа рулона не уникально
     */
    private void checkNameUnique(RollType rollType) {
        RollType entityRollType = rollTypeRepository.findByName(rollType.getName()).orElse(null);
        if (entityRollType != null && !hasSameId(rollType, entityRollType)) {
            throw new NotUniqueFieldException("Roll type name must be unique!");
        }
    }

    /**
     * Проверяет корректность диапазона веса рулона.
     * Максимальный вес должен быть равен или больше минимального
     *
     * @param rollType тип рулона с проверяемым диапазоном веса рулона
     * @throws RangeException если максимальный вес рулона равен или меньше минимального
     */
    private void checkWeightRange(RollType rollType) {
        if (rollType.getMinWeight() >= rollType.getMaxWeight()) {
            throw new RangeException("max weight must be equals or greater than min weight");
        }
    }

    /**
     * Проверяет равны ли между собой id двух типов рулонов и не равны ли они null
     *
     * @param rollType       первый сравниваемый тип рулона
     * @param entityRollType второй сравниваем тип рулона
     * @return {@code true} если оба id не null и равны {@code false} - если не выполняется хоть одно из условий
     */
    private boolean hasSameId(RollType rollType, RollType entityRollType) {
        Long id = rollType.getId();
        Long entityId = entityRollType.getId();
        return id != null && entityId != null && Objects.equals(id, entityId);
    }

}