package br.com.gabrielbarros.todolist.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.gabrielbarros.todolist.domain.Users;
import br.com.gabrielbarros.todolist.dto.users.CreateUserDto;
import br.com.gabrielbarros.todolist.dto.users.UserResponseDto;
import br.com.gabrielbarros.todolist.exceptions.messages.MessageHandler;
import br.com.gabrielbarros.todolist.exceptions.messages.UsersMessages;
import br.com.gabrielbarros.todolist.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService {

	private final UsersRepository usersRepository;
	private final ModelMapper modelMapper;

	public ResponseEntity save(CreateUserDto createUserDto) {

		String usernameToBeSave = createUserDto.getUsername();

		Users userWithDuplicatedUsername = this.usersRepository.findByUsername(usernameToBeSave);

		if (!Objects.isNull(userWithDuplicatedUsername)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageHandler.settingParamsToMessage(UsersMessages.USERNAME_EXISTING, usernameToBeSave));
		}

		Users creteUserDtoToUser = modelMapper.map(createUserDto, Users.class);

		Users userToBeSaved = this.handleUserToBeSaved(creteUserDtoToUser);

		Users saved = this.usersRepository.save(userToBeSaved);

		return ResponseEntity.status(HttpStatus.CREATED).body(this.modelMapper.map(saved, UserResponseDto.class));
	}

	private Users handleUserToBeSaved(Users userToBeSaved) {

		String password = userToBeSaved.getPassword();

		String passwordEncrypted = BCrypt.withDefaults().hashToString(12, password.toCharArray());

		userToBeSaved.setPassword(passwordEncrypted);

		return userToBeSaved;
	}

	public Users findUserByUsername(String username) {
		return this.usersRepository.findByUsername(username);
	}

	public Users findById(UUID idUser) {
		return this.usersRepository.findById(idUser)
				       .orElseThrow(() -> new RuntimeException(
						       MessageHandler.settingParamsToMessage(UsersMessages.USER_NOT_FOUND, "UUID", idUser.toString())));
	}
}
