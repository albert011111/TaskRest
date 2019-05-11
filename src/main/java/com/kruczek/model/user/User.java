package com.kruczek.model.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.kruczek.model.role.Role;
import com.kruczek.task.Task;

@Entity
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email")//@UniqueConstrain --> ograniczenie unikatowosci
		// w tabeli o danej nazwie
		//jezeli bede chcial wprowadzic 2x taki sam mail bedzie exception
})

public final class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//IDENTITY oznacza, ze id bedzie przydzielone na podstawie kolumny identity z bazy daych
	private Long id;

	@NotBlank // walidacja nie przepusci pustego znaku -> [" "]
	@Size(min = 3, max = 20)
	private String name;

	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	@NaturalId //domyslnie jest niemutowalne, NIE POWINNO sie tworzyc settera do takiego pola
	//hibernate dostarcza funkcjonalnosc, ze mozna sie odwolac do encji (do DB) przez jego NaturalId
	// --> @see byNaturalId
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(min = 6)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "user")
	private List<Task> tasks = new ArrayList<>();

	public User() {
	}

	public User(String name, String username, String email, String password) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	//TODO zweryfikowac tresc adnotacji @NaturalId
/*    public void setEmail(String email) {
		this.email = email;
    }*/

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}
