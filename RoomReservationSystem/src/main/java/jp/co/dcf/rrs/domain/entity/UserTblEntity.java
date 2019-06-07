package jp.co.dcf.rrs.domain.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "user_tbl")
@EqualsAndHashCode(callSuper=false)
public class UserTblEntity extends User{
	
	static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
	
    public UserTblEntity() {
    	super("user","pass",AUTHORITIES);
    }

	public UserTblEntity(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	

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
	private List<ReservationTblEntity> reservation;
}
