package jp.co.dcf.rrs.domain.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_tbl")
public class UserTblEntity {
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
