package br.com.gabrielbarros.todolist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "tb_users")
public class Users extends AbstractEntity {
	@Column(unique = true)
	private String username;
	private String name;
	private String password;
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Tasks> tasks;
}
