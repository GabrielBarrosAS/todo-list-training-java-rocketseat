package br.com.gabrielbarros.todolist.service;

import br.com.gabrielbarros.todolist.domain.Tasks;
import br.com.gabrielbarros.todolist.domain.Users;
import br.com.gabrielbarros.todolist.dto.task.CreateTaskDto;
import br.com.gabrielbarros.todolist.dto.task.TaskResponseDto;
import br.com.gabrielbarros.todolist.dto.task.UpdateTaskDto;
import br.com.gabrielbarros.todolist.exceptions.messages.MessageHandler;
import br.com.gabrielbarros.todolist.exceptions.messages.TasksMessages;
import br.com.gabrielbarros.todolist.repository.TasksRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TasksService {

	private final TasksRepository tasksRepository;
	private final UsersService usersService;
	private final ModelMapper modelMapper;

	public ResponseEntity save(CreateTaskDto createTaskDto, HttpServletRequest request) {
		Object idUser = request.getAttribute("idUser");

		Tasks taskToBeSaved = this.modelMapper.map(createTaskDto, Tasks.class);

		LocalDateTime currentDate = LocalDateTime.now();
		LocalDateTime startAt = taskToBeSaved.getStartAt();
		LocalDateTime endAt = taskToBeSaved.getEndAt();

		if (currentDate.isAfter(startAt)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageHandler.settingParamsToMessage(TasksMessages.INVALID_START_DATE, String.valueOf(startAt)));
		}

		if (currentDate.isAfter(endAt)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageHandler.settingParamsToMessage(TasksMessages.INVALID_END_DATE, String.valueOf(endAt)));
		}

		if (startAt.isAfter(endAt)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageHandler.settingParamsToMessage(TasksMessages.START_DATE_AFTER_END_DATE, String.valueOf(startAt), String.valueOf(endAt)));
		}

		Users taskOwner = this.usersService.findById((UUID) idUser);

		taskToBeSaved.setUser(taskOwner);

		Tasks saved = this.tasksRepository.save(taskToBeSaved);

		TaskResponseDto taskResponseDto = this.modelMapper.map(saved, TaskResponseDto.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(taskResponseDto);
	}

	public ResponseEntity findAllByUser(HttpServletRequest request) {
		Object idUser = request.getAttribute("idUser");
		Users taskOwner = this.usersService.findById((UUID) idUser);
		List<Tasks> tasksByUserUUID = this.tasksRepository.findByUser(taskOwner);
		List<TaskResponseDto> taskResponseDtoList = tasksByUserUUID.stream().map(taskByUser -> this.modelMapper.map(taskByUser, TaskResponseDto.class)).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(taskResponseDtoList);
	}

	public ResponseEntity updateTask(UpdateTaskDto updateTaskDto, UUID uuid, HttpServletRequest request) {

		Tasks taskSaved = this.findById(uuid);

		if (Objects.isNull(taskSaved)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageHandler.settingParamsToMessage(TasksMessages.TASK_NOT_FOUND, "UUID", uuid.toString()));
		}

		Object idUser = request.getAttribute("idUser");

		Users userAuthenticated = this.usersService.findById((UUID) idUser);

		if (!taskSaved.getUser().equals(userAuthenticated)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageHandler.settingParamsToMessage(TasksMessages.ERROR_UPDATE_TASK_FROM_OTHER_USER));
		}

		this.modelMapper.map(updateTaskDto, taskSaved);
		Tasks updatedTask = this.tasksRepository.save(taskSaved);
		TaskResponseDto taskResponseDto = this.modelMapper.map(updatedTask, TaskResponseDto.class);

		return ResponseEntity.status(HttpStatus.OK).body(taskResponseDto);
	}

	public Tasks findById(UUID id) {
		return this.tasksRepository.findById(id)
				       .orElse(null);
	}
}
