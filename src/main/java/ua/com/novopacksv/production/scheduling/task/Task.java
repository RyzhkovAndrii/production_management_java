package ua.com.novopacksv.production.scheduling.task;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.scheduling.TaskDetails;

@Getter
@Setter
@Component
public abstract class Task implements Runnable {

    private TaskDetails taskDetails;

}