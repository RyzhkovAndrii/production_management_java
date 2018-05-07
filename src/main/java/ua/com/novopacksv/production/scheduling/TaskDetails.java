package ua.com.novopacksv.production.scheduling;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "task_details")
@Data
public class TaskDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_class_name", nullable = false, unique = true)
    private String taskClassName;

    @Column(name = "last_execution_time")
    private LocalDateTime lastExecutionTime;

    @Column(name = "next_execution_time")
    private LocalDateTime nextExecutionTime;

    @Column(name = "cron_timer", nullable = false)
    private String cronTimer;

}