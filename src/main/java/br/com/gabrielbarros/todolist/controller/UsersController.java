package br.com.gabrielbarros.todolist.controller;

import br.com.gabrielbarros.todolist.dto.users.CreateUserDto;
import br.com.gabrielbarros.todolist.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

	private final UsersService usersService;

	@PostMapping
	public ResponseEntity create(@RequestBody CreateUserDto createUserDto) {
		return this.usersService.save(createUserDto);
	}

}
