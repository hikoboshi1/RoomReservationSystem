package jp.co.dcf.rrs.domain.entity;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import jp.co.dcf.rrs.domain.entity.pk.ReservationTblEntityPK;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "reservation_tbl")
public class ReservationTblEntity {
	/**
	 * 主キークラス
	 */
	@EmbeddedId
	@Getter
	@Setter
	private ReservationTblEntityPK id;
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
	/**
	 * 利用終了時刻
	 */
	@Getter
	@Setter
	private LocalTime useEndTime;
	/**
	 * 予約者名
	 */
	@Getter
	@Setter
	private String reserverId;
	/**
	 * 会議室利用者
	 */
	@Getter
	@Setter
	private String roomUsername;
	/**
	 * 備考
	 */
	@Getter
	@Setter
	private String note;
}