package jp.co.dcf.rrs.domain.entity;

import java.time.LocalTime;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import jp.co.dcf.rrs.domain.entity.id.ReservationTblEntityId;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reservation_tbl")
public class ReservationTblEntity {
	/**
	 * ��L�[
	 */
	@EmbeddedId
	@Getter
	@Setter
	private ReservationTblEntityId id;

	/**
	 * ���p�I������
	 */
	@Getter
	@Setter
	private LocalTime useEndTime;

	/**
	 * �\�񃆁[�U�[ID
	 */
	@Getter
	@Setter
	private Integer reserverId;

	/**
	 * ���p�Җ�
	 */
	@Getter
	@Setter
	private String roomUserName;

	/**
	 * ���l
	 */
	@Getter
	@Setter
	private String note;
}
