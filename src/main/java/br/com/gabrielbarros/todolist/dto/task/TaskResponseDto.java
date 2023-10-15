package br.com.gabrielbarros.todolist.dto.task;

import br.com.gabrielbarros.todolist.dto.users.UserResponseDto;
import br.com.gabrielbarros.todolist.enums.TasksPriorityEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TaskResponseDto {
	private UUID uuid;
	private String description;
	private String title;
	private TasksPriorityEnum priority;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private LocalDateTime creationDate;
	private UserResponseDto user;
}
