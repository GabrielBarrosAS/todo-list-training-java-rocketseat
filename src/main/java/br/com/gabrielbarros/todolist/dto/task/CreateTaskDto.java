package br.com.gabrielbarros.todolist.dto.task;

import br.com.gabrielbarros.todolist.enums.TasksPriorityEnum;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateTaskDto {
	@Size(max = 4)
	private String description;
	@Size(max = 40)
	private String title;
	private TasksPriorityEnum priority;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
}
