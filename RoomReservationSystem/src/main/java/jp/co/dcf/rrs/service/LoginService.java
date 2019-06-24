package jp.co.dcf.rrs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.dcf.rrs.model.User;
import jp.co.dcf.rrs.model.repository.UsersRepository;

@Service
public class LoginService implements UserDetailsService {
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private HttpSession session;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<User> userList = userRepository.findByUsername(username);
		if (userList.isEmpty()) {
			throw new UsernameNotFoundException("User " + username + " was not found in the database.");
		}

		User user = userList.get(0);
		session.setAttribute("loginUser", user);
		return user;
	}
}
