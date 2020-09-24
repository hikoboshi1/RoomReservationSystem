package jp.co.dcf.rrs.model;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import jp.co.dcf.rrs.model.validator.DateTimeIsFuture;
import jp.co.dcf.rrs.model.validator.EndIsAfterStart;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "reservation_tbl")
@EndIsAfterStart(element1="useStartTime", element2="useEndTime")
@DateTimeIsFuture(element1="useDate", element2="useStartTime")
public class Reservation {
	/**
	 * 主キー
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Integer id;
	/**
	 * 利用日
	 */
	@Getter
	@Setter
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotNull(message="{required}")
	LocalDate useDate = LocalDate.of(1, 1, 1);
	/**
	 * 利用開始時刻
	 */
	@Getter
	@Setter
	@DateTimeFormat(pattern="HH:mm")
	@NotNull(message="{required}")
	LocalTime useStartTime;
	/**
	 * 利用終了時刻
	 */
	@Getter
	@Setter
	@DateTimeFormat(pattern="HH:mm")
	@NotNull(message="{required}")
	LocalTime useEndTime;
	/**
	 * 予約者
	 */
	@Getter
	@Setter
	@ManyToOne
	@NotNull(message="{required}")
	private User reserver;

	/**
	 * 会議室利用者
	 */
	@Getter
	@Setter
	@NotEmpty(message="{required}")
	private String roomUsername;
	/**
	 * 備考
	 */
	@Getter
	@Setter
	private String note;
}