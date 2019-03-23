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
	 * ���񉻗p���ʎq
	 */
	private static final long serialVersionUID = -7067344067219037058L;

	/**
	 * ���p��
	 */
	@Getter
	@Setter
	LocalDate useDate;

	/**
	 * ���p�J�n����
	 */
	@Getter
	@Setter
	LocalTime useStartTime;
}
