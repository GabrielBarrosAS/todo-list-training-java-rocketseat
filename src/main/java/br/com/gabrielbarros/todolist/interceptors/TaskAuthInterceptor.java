package br.com.gabrielbarros.todolist.interceptors;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.gabrielbarros.todolist.domain.Users;
import br.com.gabrielbarros.todolist.exceptions.messages.AuthenticationMessages;
import br.com.gabrielbarros.todolist.exceptions.messages.MessageHandler;
import br.com.gabrielbarros.todolist.service.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class TaskAuthInterceptor extends OncePerRequestFilter {

	private final UsersService usersService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String servletPath = request.getServletPath();

		if (servletPath.startsWith("/tasks")) {

			String authorization = request.getHeader("Authorization");

			String basicAuthEncoded = authorization.substring("Basic".length()).trim();

			String basicAuthDecoded = new String(Base64.getDecoder().decode(basicAuthEncoded));

			String[] credentials = basicAuthDecoded.split(":");

			String username = credentials[0];
			String password = credentials[1];

			Users userByUsername = this.usersService.findUserByUsername(username);

			if (Objects.isNull(userByUsername)) {
				response.sendError(HttpStatus.UNAUTHORIZED.value(), MessageHandler.settingParamsToMessage(AuthenticationMessages.USERNAME_NOT_FOUND, username));
			} else {

				BCrypt.Result passwordIsValid = BCrypt.verifyer().verify(password.toCharArray(), userByUsername.getPassword());

				if (passwordIsValid.verified) {
					request.setAttribute("idUser",userByUsername.getUuid());
					filterChain.doFilter(request, response);
				} else {
					response.sendError(HttpStatus.UNAUTHORIZED.value(), MessageHandler.settingParamsToMessage(AuthenticationMessages.INCORRECT_PASSWORD));
				}

			}
		} else {
			filterChain.doFilter(request,response);
		}
	}
}
