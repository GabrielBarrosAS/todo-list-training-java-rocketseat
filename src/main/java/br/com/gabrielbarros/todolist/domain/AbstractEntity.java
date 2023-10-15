package br.com.gabrielbarros.todolist.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity {
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID uuid;
	@CreationTimestamp
	private LocalDateTime creationDate;
}
