package jp.co.dcf.rrs.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.dcf.rrs.domain.entity.UserTblEntity;
import jp.co.dcf.rrs.domain.entity.id.UserTblEntityId;
import jp.co.dcf.rrs.domain.repository.UserTblRepository;

@Service
public class LoginApplicationService implements UserDetailsService {
	@Autowired
	private UserTblRepository userTblRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// ユーザー情報を取得する。
		UserTblEntityId id = new UserTblEntityId();
		id.setUsername(username);
		Optional<UserTblEntity> userTblEntityOpt = userTblRepository.findById(id);
		if (!userTblEntityOpt.isPresent()) {
			throw new UsernameNotFoundException("User " + username + " was not found in the database.");
		}

		// ユーザー権限を仮実装する。
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		GrantedAuthority authority = new SimpleGrantedAuthority("DUMMY");
		grantList.add(authority);

		// パスワードを暗号化する。
		UserTblEntity userTblEntity = userTblEntityOpt.get();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return (UserDetails) new User(userTblEntity.getId().getUsername(), encoder.encode(userTblEntity.getPassword()),
				grantList);
	}
}
