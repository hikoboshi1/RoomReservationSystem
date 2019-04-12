package jp.co.dcf.rrs.domain.entity.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@EqualsAndHashCode
public class UserTblEntityPK implements Serializable {
	/**
	 * 直列化識別子
	 */
	private static final long serialVersionUID = -4658249526082343743L;

	/**
	 * 利用者名
	 */
	@Getter
	@Setter
	Integer id;
}
