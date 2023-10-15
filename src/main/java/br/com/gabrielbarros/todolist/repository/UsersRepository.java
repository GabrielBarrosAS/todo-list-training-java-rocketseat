package br.com.gabrielbarros.todolist.repository;

import br.com.gabrielbarros.todolist.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID> {
	Users findByUsername(String username);
}
