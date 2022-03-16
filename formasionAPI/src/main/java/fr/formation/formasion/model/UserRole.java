package fr.formation.formasion.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@SequenceGenerator(name = "user_role_gen", sequenceName = "user_role_seq", initialValue = 100, allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
	@Id
	@GeneratedValue(generator = "user_role_gen")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_username")
	@JsonIgnoreProperties("roles")
	private User user;

	@Enumerated(EnumType.STRING)
	private Role role;
	
	public UserRole(User u, Role r) {
		this.user=u;
		this.role=r;
	}

	public String toString() {
		return this.role.name();
	}
}
