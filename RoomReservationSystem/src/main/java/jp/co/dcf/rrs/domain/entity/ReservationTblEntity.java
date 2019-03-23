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
	 * 主キー
	 */
	@EmbeddedId
	@Getter
	@Setter
	private ReservationTblEntityId id;

	/**
	 * 利用終了時刻
	 */
	@Getter
	@Setter
	private LocalTime useEndTime;

	/**
	 * 予約ユーザーID
	 */
	@Getter
	@Setter
	private Integer reserverId;

	/**
	 * 利用者名
	 */
	@Getter
	@Setter
	private String roomUserName;

	/**
	 * 備考
	 */
	@Getter
	@Setter
	private String note;
}
