package jp.co.dcf.rrs.domain.entity;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "reservation_tbl")
public class ReservationTblEntity {
	/**
	 * 主キー
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	@Setter
	private Integer id;
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
	 * 予約者
	 */
	@Getter
	@Setter
	@ManyToOne
	private UserTblEntity reserver;

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