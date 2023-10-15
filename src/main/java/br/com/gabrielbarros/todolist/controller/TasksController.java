package br.com.gabrielbarros.todolist.controller;

import br.com.gabrielbarros.todolist.dto.task.CreateTaskDto;
import br.com.gabrielbarros.todolist.dto.task.UpdateTaskDto;
import br.com.gabrielbarros.todolist.service.TasksService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TasksController {

	private final TasksService tasksService;

	@PostMapping
	public ResponseEntity create(@RequestBody @Valid CreateTaskDto createTaskDto, HttpServletRequest request) {
		return this.tasksService.save(createTaskDto, request);
	}

	@GetMapping
	public ResponseEntity findAllByUser(HttpServletRequest request) {
		return this.tasksService.findAllByUser(request);
	}

	@PutMapping("/{uuid}")
	public ResponseEntity updateTask(@RequestBody @Valid UpdateTaskDto updateTaskDto, @PathVariable UUID uuid, HttpServletRequest request) {
		return this.tasksService.updateTask(updateTaskDto,uuid,request);
	}

}
