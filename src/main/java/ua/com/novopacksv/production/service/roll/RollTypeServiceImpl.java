package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.repository.rollRepository.RollTypeRepository;

import java.util.List;

/**
 * Класс имплементирует методы интерфейса {@code RollTypeService}.
 * Содержит бизнес логику для работы с типами рулонов производимых на передприятии.
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
     * Ищет в базе и возвращает тип произоводимого на предприятии рулона по указанному id.
     * Не изменяет содержимого базы данных.
     *
     * @param id ID типа рулона в базе данных, должен быть не null
     * @return тип рулона с указанным id
     * @throws ResourceNotFoundException если тип рулона с указанным id не найден
     * @throws IllegalArgumentException если id - null
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
     * Ищет в базе и возвращает все типы производимых на предприятии рулонов.
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
     *
     * @param rollType новый тип рулона, должен быть не null
     * @return новый тип рулона, с указанием присвоенного в базе id
     * @throws IllegalArgumentException если rollType - null
     */
    @Override
    public RollType save(RollType rollType) {
        return rollTypeRepository.save(rollType);
    }

    /**
     * Изменяет данные о типе производимого на предприятии рулона.
     * Объект передаваемый в качестве параметра должен содержать id изменяемого типа рулона.
     *
     * @param rollType изменный тип рулона, должен быть не null
     * @return измененный тип рулона
     * @throws ResourceNotFoundException если изменяемый тип рулона не найден (по id)
     * @throws IllegalArgumentException если rollType - null
     */
    @Override
    public RollType update(RollType rollType) {
        return this.save(rollType);
    }

    /**
     * Удаляет из базы тип производимого на предприятии рулона по указанному id
     *
     * @param id ID типа рулона в базе данных
     * @throws ResourceNotFoundException если удаляемый тип рулона с указанным id не найден
     * @throws IllegalArgumentException если id - null
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        rollTypeRepository.delete(findById(id));
    }

}