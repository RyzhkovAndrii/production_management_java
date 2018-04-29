package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollOperation;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.repository.rollRepository.RollManufacturedRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Класс имплементирует методы интерфейса {@code RollManufacturedService}.
 * Содержит бизнес-логику для работы с объектами класса {@see RollManufactured}, представляющие собой условный рулон
 * определенного типа и даты производства
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RollManufacturedServiceImpl implements RollManufacturedService {

    /**
     * Дополнительный период времени, за который необходмо проверять, установлено ли поле готовности в зачение
     * {@code true} для готовых к использованию рулонов.
     * <p>
     * Необходим для учета случаев, когда метод {@link #rollsBecomeReadyToUseForNow()} не выполнялся из-за не зависящих
     * от программы причин (например, выход из строя аппаратного обеспечения т.п.)
     * <p>
     * Период указан в днях.
     */
    private final static Integer PERIOD_FOR_CHECK_READY_TO_USE = 7;

    /**
     * Содержит методы для работы с базой данных (DAO уровень) для сущности {@code RollManufactured}
     */
    private final RollManufacturedRepository rollManufacturedRepository;

    /**
     * Содержит методы для работы с операциями, производимыми с рулонами - сущностью {@code RollOperation}
     */
    private final RollOperationService rollOperationService;

    /**
     * Содержит методы для конвертации (преобразования) объектов одного класса в другой
     */
    private final ConversionService conversionService;

    /**
     * Ищет в базе и возвращает рулон, по указанному типу и дате производства.
     *
     * @param rollTypeId       ID типа рулона
     * @param manufacturedDate дата производства рулона
     * @return рулон {@code RollManufactured} для указнного id типа рулона и даты производства
     * @throws ResourceNotFoundException если рулон с указанными id типа рулона и датой производства не найдены
     */
    @Override
    @Transactional(readOnly = true)
    public RollManufactured findOne(LocalDate manufacturedDate, Long rollTypeId) throws ResourceNotFoundException {
        return rollManufacturedRepository.findByManufacturedDateAndRollType_Id(manufacturedDate, rollTypeId)
                .orElseThrow(() -> {
                    String formatDate = conversionService.convert(manufacturedDate, String.class);
                    String message = String.format("Roll manufactured whit roll type id = %d" +
                            " and manufactured date = %s is not found!", rollTypeId, formatDate);
                    return new ResourceNotFoundException(message);
                });
    }

    /**
     * Ищет в базе и возвращает рулон, по указанному типу и дате производства. Елси такой остутсвует в базе,
     * то создает и возвращает новый рулон с указанным типом, датой производства. При этом для нового
     * рулона проверяется готов ли он к использовнию и выставляется соответсвтующее значение {@code true}
     * или {@code false} для поля готовности
     *
     * @param manufacturedDate дата производства рулона
     * @param rollType         тип рулона
     * @return рулон {@code RollManufactured} для указнного типа рулона и даты производства
     * @throws IllegalArgumentException если rollType - null или manufacturedDate - null
     */
    @Override
    public RollManufactured findOneOrCreate(LocalDate manufacturedDate, RollType rollType) {
        return rollManufacturedRepository.findByManufacturedDateAndRollType(manufacturedDate, rollType)
                .orElseGet(() -> {
                    RollManufactured rollManufactured = new RollManufactured();
                    rollManufactured.setManufacturedDate(manufacturedDate);
                    rollManufactured.setRollType(rollType);
                    rollManufactured.setReadyToUse(isReadyToUse(manufacturedDate));
                    return rollManufactured;
                });
    }

    /**
     * Ищет в базе и возвращает все произведнные рулоны с указанным id типа рулона.
     * <p>
     * Не изменяет содержимого базы данных
     *
     * @param rollTypeId ID типа рулона
     * @return {@code List} всех произведенных рулонов с указаным id типа рулона
     * или empty {@code List}, если в базе отсутствуют рулоны с указаным id типа рулона
     * @throws IllegalArgumentException если rollTypeId - null
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollManufactured> findAll(Long rollTypeId) {
        return rollManufacturedRepository.findAllByRollType_Id(rollTypeId);
    }

    /**
     * Ищет в базе и возвращает все произведнные рулоны с указнной датой производства.
     * <p>
     * Не изменяет содержимого базы данных
     *
     * @param manufacturedDate дата производства рулона
     * @return {@code List} всех произведенных рулонов за указанную дату производства
     * или empty {@code List}, если в базе отсутствуют рулоны с указанной датой производства
     * @throws IllegalArgumentException если manufacturedDate - null
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollManufactured> findAll(LocalDate manufacturedDate) {
        return rollManufacturedRepository.findAllByManufacturedDate(manufacturedDate);
    }

    /**
     * Ищет в базе и возвращает все рулоны произведенные в указанный период времени.
     * <p>
     * Не изменяет содержимого базы данных
     *
     * @param fromManufacturedDate начало периода даты производства рулонов
     * @param toManufacturedDate   окончание периода даты производства рулонов
     * @return {@code List} всех рулонов, произведенных в указанный период времени,
     * или empty {@code List} если в базе отсутствуют рулоны, произведенные в указанный период
     * @throws IllegalArgumentException если fromManufacturedDate - null и/или toManufacturedDate - null
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollManufactured> findAll(LocalDate fromManufacturedDate, LocalDate toManufacturedDate) {
        return rollManufacturedRepository.findAllByManufacturedDateBetween(fromManufacturedDate, toManufacturedDate);
    }


    /**
     * Ищет в базе и возвращает все рулоны указанного типа, произведенные в указанный период времени.
     * <p>
     * Не изменяет содержимого базы данных
     *
     * @param rollTypeId           ID типа рулона
     * @param fromManufacturedDate начало периода даты производства рулонов
     * @param toManufacturedDate   окончание периода даты производства рулонов
     * @return {@code List} всех рулонов указанного типа, произведенных в указанный период времени,
     * или empty {@code List}, если в базе отсутствуют рулоны с указанным типом, произведенные в указанный период
     * @throws IllegalArgumentException если rollTypeId - null, и/или fromManufacturedDate - null,
     *                                  и/или toManufacturedDate - null
     */
    @Override
    public List<RollManufactured> findAll(LocalDate fromManufacturedDate, LocalDate toManufacturedDate, Long rollTypeId) {
        return rollManufacturedRepository
                .findAllByManufacturedDateBetweenAndRollType_Id(fromManufacturedDate, toManufacturedDate, rollTypeId);
    }


    /**
     * Вычисляет и возвращает общее количество указанных рулонов {@code RollManufactured},
     * произведенных на предприятии.
     * <p>
     * Не изменяет содержимого базы данных
     *
     * @param rollManufactured рулон, содержащий информацию о типе и дате производства
     * @return количество указанных рулонов, произведенных на предприятии или 0 если такие рулоны не произодились
     * @throws IllegalArgumentException если rollManufactured - null
     */
    @Override
    @Transactional(readOnly = true)
    public Integer getManufacturedRollAmount(RollManufactured rollManufactured) {
        return rollOperationService.getAllManufacturedOperationsByRollManufactured(rollManufactured)
                .stream()
                .mapToInt(RollOperation::getRollAmount)
                .sum();
    }

    /**
     * Вычисляет и возвращает общее количество указанных рулонов {@code RollManufactured},
     * отправленных в переработку.
     * <p>
     * Не изменяет содержимого базы данных
     *
     * @param rollManufactured рулон, содержащий информацию о типе и дате производства
     * @return количество указанных рулонов, отправленных в переработку или 0 если рулоны не перерабатывались
     * @throws IllegalArgumentException если rollManufactured - null
     */
    @Override
    @Transactional(readOnly = true)
    public Integer getUsedRollAmount(RollManufactured rollManufactured) {
        return rollOperationService.getAllUsedOperationsByRollManufactured(rollManufactured)
                .stream()
                .mapToInt(RollOperation::getRollAmount)
                .sum();
    }

    /**
     * Ищет в базе готовые к использованию рулоны, но у которых не установлено соответствующее поле
     * в значение {@code true}, и устанавливает данное поле в значение {@code true}
     * <p>
     * Выполняется каждый день в 00.00
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void rollsBecomeReadyToUseForNow() {
        findAllShouldBeReadyToUseNow().forEach(rollManufactured -> {
            rollManufactured.setReadyToUse(true);
            rollManufacturedRepository.save(rollManufactured);
        });
    }

    /**
     * Ищет в базе и возвращет все рулоны, которые уже готовы к использованию, но у которых не установлено
     * соответствующее поле в значение {@code true}.
     * <p>
     * Вычисляет дату производства рулона, который становится готовым сегодня. Затем ищет в базе все рулоны,
     * значение готовности которых - {@code false} и которые произведенны в вычисленную дату и в период
     * за {@value #PERIOD_FOR_CHECK_READY_TO_USE} дней ранее.
     * <p>
     * Рулон становится готовым после прошествия {@value RollType#READY_TO_USE_PERIOD} дней от даты его производства.
     *
     * @return {@code List} всех рулонов, которые уже готовы к использованию, но у которых не установлено
     * соответствующее поле в значение {@code true} или empty {@code List}, если рулоны, готовые к использованию,
     * отсутствуют
     */
    private List<RollManufactured> findAllShouldBeReadyToUseNow() {
        LocalDate manufacturedDate = LocalDate.now().minusDays(RollType.READY_TO_USE_PERIOD);
        return rollManufacturedRepository.findAllByManufacturedDateBetweenAndReadyToUseIsFalse(
                manufacturedDate.minusDays(PERIOD_FOR_CHECK_READY_TO_USE),
                manufacturedDate);
    }

    /**
     * Проверяет готов ли рулон с указнной датой производства.
     * <p>
     * Рулон становтся готовым после прошествия {@value RollType#READY_TO_USE_PERIOD} дней после даты его производства
     *
     * @param manufacturedDate дата производства рулона
     * @return {@code true} если дата готовности рулона насупила и {@code false} - если нет
     * @throws NullPointerException если manufacturedDate - null
     */
    private boolean isReadyToUse(LocalDate manufacturedDate) {
        return manufacturedDate.until(LocalDate.now(), ChronoUnit.DAYS) > RollType.READY_TO_USE_PERIOD;
    }

}