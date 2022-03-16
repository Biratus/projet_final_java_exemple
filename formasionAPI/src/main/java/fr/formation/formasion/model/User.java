package fr.formation.formasion.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	private String username;

	private String password;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnoreProperties("user")
	private List<UserRole> roles;

	@OneToOne
	@JoinColumn(name = "personne_id")
	@JsonIgnoreProperties({"user","filiere","modules"})
	private Personne personne;

	public User(String username) {
		this.username = username;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UserDetails toUserDetails(Boolean encodePwd) {
		List<GrantedAuthority> authorities = new ArrayList<>();

		for (UserRole role : roles) {
			GrantedAuthority ga = new SimpleGrantedAuthority(role.getRole().name());
			authorities.add(ga);
		}

		return new org.springframework.security.core.userdetails.User(this.username, encodePwd?new BCryptPasswordEncoder().encode(this.password):this.password, authorities);
	}

}
