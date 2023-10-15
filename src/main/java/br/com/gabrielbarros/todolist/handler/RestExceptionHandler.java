package br.com.gabrielbarros.todolist.handler;

import br.com.gabrielbarros.todolist.exceptions.ValidationExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidException
			(MethodArgumentNotValidException methodArgumentNotValidException) {

		List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
		String messages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

		return new ResponseEntity<>(
				ValidationExceptionDetails.builder()
						.timestamp(LocalDateTime.now())
						.status(HttpStatus.BAD_REQUEST.value())
						.title("Invalid fields")
						.developerMessage(methodArgumentNotValidException.getClass().getName())
						.fields(fields)
						.fieldsMessage(messages)
						.build(), HttpStatus.BAD_REQUEST
		);
	}
}
