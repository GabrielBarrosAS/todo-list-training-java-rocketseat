package br.com.gabrielbarros.todolist.dto.users;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
	private String username;
	private String name;
	private LocalDateTime creationDate;
}
