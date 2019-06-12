package jp.co.dcf.rrs.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "user_tbl")
public class User implements UserDetails{
	static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
	
	/**
	 * 主キー
	 */
	@Id
	@Getter
	@Setter
	private Integer id;

	/**
	 * 名前
	 */
	@Getter
	@Setter
	private String username;
	
	/**
	 * パスワード
	 */
	@Getter
	@Setter
	private String password;
	
	/**
	 * 予約
	 */
	@Getter
	@Setter
	@OneToMany(mappedBy = "reserver")
	private List<Reservation> reservations = new ArrayList<Reservation>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
