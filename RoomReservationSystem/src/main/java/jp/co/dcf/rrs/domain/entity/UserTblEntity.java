package jp.co.dcf.rrs.domain.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import jp.co.dcf.rrs.domain.entity.id.UserTblEntityId;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_tbl")
public class UserTblEntity {
	/**
	 * 主キークラス
	 */
	@EmbeddedId
	@Getter
	@Setter
	private UserTblEntityId id;

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
}
