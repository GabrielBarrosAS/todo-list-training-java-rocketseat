package br.com.gabrielbarros.todolist.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
	private String username;
	private String name;
	private String password;
}
