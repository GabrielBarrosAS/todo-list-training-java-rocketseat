package br.com.gabrielbarros.todolist.repository;

import br.com.gabrielbarros.todolist.domain.Tasks;
import br.com.gabrielbarros.todolist.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TasksRepository extends JpaRepository<Tasks, UUID> {
	List<Tasks> findByUser(Users user);
}
