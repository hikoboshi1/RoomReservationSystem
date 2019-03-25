package jp.co.dcf.rrs.domain.entity.id;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@EqualsAndHashCode
public class ReservationTblEntityId implements Serializable {
	/**
	 * 直列化識別子
	 */
	private static final long serialVersionUID = -7067344067219037058L;

	/**
	 * 利用日
	 */
	@Getter
	@Setter
	LocalDate useDate;

	/**
	 * 利用開始時刻
	 */
	@Getter
	@Setter
	LocalTime useStartTime;
}
