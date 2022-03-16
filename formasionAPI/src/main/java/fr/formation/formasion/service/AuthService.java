package fr.formation.formasion.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.formation.formasion.model.User;
import fr.formation.formasion.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService {
	
	@Autowired
	private UserRepository ur;
	
	@Value("${security.encodepwdindb}")
	Boolean encodePwdInDB;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optU = ur.findByUsername(username);

		if(optU.isPresent()) {			
			return optU.get().toUserDetails(!encodePwdInDB);
		} else {
			throw new UsernameNotFoundException("Le nom d'utilisateur "+username+" n'existe pas");
		}
	}
	
}
