package br.com.gabrielbarros.todolist.domain;

import br.com.gabrielbarros.todolist.enums.TasksPriorityEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "tb_tasks")
public class Tasks extends AbstractEntity {
	private String description;
	@Column(length = 40)
	private String title;
	@Enumerated(value = EnumType.STRING)
	private TasksPriorityEnum priority;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_uuid")
	private Users user;
}
