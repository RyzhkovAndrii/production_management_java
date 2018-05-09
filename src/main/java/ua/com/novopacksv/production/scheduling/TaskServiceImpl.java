package ua.com.novopacksv.production.scheduling;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import ua.com.novopacksv.production.scheduling.task.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl {

    private final TaskDetailsRepository taskDetailsRepository;

    private final ApplicationContext context;

    private final ConversionService conversionService;

    private final TaskExecutor taskExecutor;

    @PostConstruct
    public void initTaskService() {
        initAllNewTasks();
        executeAllTasks();
    }

    @Transactional
    public void initAllNewTasks() {
        try {
            taskDetailsRepository.saveAll(taskDetailsForNewTasks());
        } catch (Exception e) {
            // todo logger
        }
    }

    @Scheduled(cron = "0 */30 * * * *")
    public void executeAllTasks() {
        try {
            getAllWhichMustExecute().forEach((task) -> taskExecutor.execute(() -> executeTask(task)));
        } catch (Exception e) {
            // todo logger
        }
    }

    private List<TaskDetails> initialTaskDetails() {
        List<TaskDetails> taskDetailsList = new ArrayList<>();
        taskDetailsList.add(getInitialTaskDetailsForTask(RollReadyToUseTask.class, "0 1 0 * * *"));
        taskDetailsList.add(getInitialTaskDetailsForTask(ResetRollChecksTask.class, "0 1 0 * * *"));
        return taskDetailsList;
    }

    private TaskDetails getInitialTaskDetailsForTask(Class<? extends Task> taskClass, String cronTimer) {
        TaskDetails details = new TaskDetails();
        details.setTaskClassName(taskClass.getName());
        details.setCronTimer(cronTimer);
        details.setNextExecutionTime(LocalDateTime.now());
        return details;
    }

    private List<TaskDetails> taskDetailsForNewTasks() {
        return initialTaskDetails()
                .stream()
                .filter(details -> !isTaskDetailsInDb(details))
                .collect(Collectors.toList());
    }

    private boolean isTaskDetailsInDb(TaskDetails taskDetails) {
        return taskDetailsRepository.findByTaskClassName(taskDetails.getTaskClassName()).isPresent();
    }

    private void executeTask(Task task) {
        task.run();
        changeAndSaveExecutionTime(task);
    }

    private void changeAndSaveExecutionTime(Task task) {
        TaskDetails taskDetails = task.getTaskDetails();
        taskDetails.setLastExecutionTime(LocalDateTime.now());
        taskDetails.setNextExecutionTime(getNextExecutionTime(taskDetails));
        taskDetailsRepository.save(taskDetails);
    }

    private LocalDateTime getNextExecutionTime(TaskDetails taskDetails) {
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(taskDetails.getCronTimer());
        Date next = cronSequenceGenerator.next(new Date());
        return conversionService.convert(next, LocalDateTime.class);
    }

    private Task getTask(TaskDetails taskDetails) {
        Class<?> taskClass = ClassUtils.resolveClassName(taskDetails.getTaskClassName(), null);
        Task task = (Task) context.getBean(taskClass);
        task.setTaskDetails(taskDetails);
        return task;
    }

    private List<Task> getAllWhichMustExecute() {
        return taskDetailsRepository
                .findAllByNextExecutionTimeIsBefore(LocalDateTime.now())
                .stream()
                .map(this::getTask)
                .collect(Collectors.toList());
    }

}