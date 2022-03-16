package fr.formation.formasion.service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.formation.formasion.model.Personne;
import fr.formation.formasion.model.Role;
import fr.formation.formasion.model.User;
import fr.formation.formasion.model.UserRole;
import fr.formation.formasion.model.dto.ChangePasswordDTO;
import fr.formation.formasion.model.dto.UserDTO;
import fr.formation.formasion.repository.UserRepository;
import fr.formation.formasion.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	UserRepository ur;

	@Autowired
	UserRoleRepository urr;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Value("${security.encodepwdindb}")
	Boolean encodePwdInDB;

	public Optional<User> getByUsername(String name) {
		return ur.findByUsername(name);
	}

	// Meme méthode pour update et create pour l'instant
	public User create(User u) {
		if (encodePwdInDB)
			u.setPassword(passwordEncoder.encode(u.getPassword()));

		u = ur.save(u);

		// Roles
		if (Objects.isNull(u.getRoles()))
			u.setRoles(new ArrayList<>());
		for (UserRole userRole : u.getRoles()) {
			userRole.setUser(u);

			create(userRole);
		}
		return u;
	}

	public UserRole create(UserRole userRole) {
		return urr.save(userRole);
	}

	@Transactional
	public UserDTO inscription(Personne personne) {
		String pwd = generatePassword(7);
		User u = new User(personne.getIdentifiant(), pwd);
		u.setPersonne(personne);
		u = create(u);
		addRoleToUser(u, Role.ROLE_BASE);
		return new UserDTO(personne.getIdentifiant(), pwd);// pour renvoyer le mot de passe en clair
	}

	public void addRoleToUser(User u, Role r) {
		create(new UserRole(u, r));
	}

	private static String generatePassword(int length) {
		String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		String specialCharacters = "!@#$";
		String numbers = "1234567890";
		String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
		Random random = new Random();
		char[] password = new char[length];

		password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
		password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
		password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
		password[3] = numbers.charAt(random.nextInt(numbers.length()));

		for (int i = 4; i < length; i++) {
			password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
		}
		return password.toString();
	}

	public Boolean changePassword(ChangePasswordDTO dto) {
		Optional<User> optU = ur.findByUsername(dto.getUsername());
		if (optU.isEmpty())
			return false;
		User u = optU.get();
		Boolean passwordsMatch = encodePwdInDB 
				? u.getPassword().equals(passwordEncoder.encode(dto.getOldPassword()))
				: u.getPassword().equals(dto.getOldPassword());
		if (passwordsMatch) {
			if(encodePwdInDB) u.setPassword(passwordEncoder.encode(dto.getNewPassword()));
			else u.setPassword(dto.getNewPassword());
			ur.save(u);
			return true;
		}
		return false;
	}
}
