package jp.co.dcf.rrs.model.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.dcf.rrs.model.Reservation;
import jp.co.dcf.rrs.model.User;

//LocalDate型を引数にReservationクラスが対応しているテーブルに対し、日付を条件に検索してくれるメソッド(複数ある可能性を考慮してList型で返す)
@Repository
public interface ReservationsRepository extends JpaRepository<Reservation, Integer> {
	/**
	 * 利用日検索
	 * 
	 * @param useDate
	 * @return
	 */
	//Springの持つ仕様で、Repositoryクラスのメソッド名は、findBy<カラム名>で作ることで自動でDBの絞り込みをする
	public List<Reservation> findByUseDate(LocalDate useDate);

	//予約者IDで絞り込む
	public List<Reservation> findByReserverId(Integer reserverId);
	/**
	 * 重複時間検索
	 * 
	 * @param useDate
	 * @param useStartTime
	 * @param useEndTime
	 * @return
	 */
	@Query("select t from Reservation t where t.useDate = :useDate and ("
			+ "(:useStartTime <= t.useStartTime and useEndTime <= :useEndTime) or "
			+ "(:useStartTime <= t.useStartTime and t.useStartTime < :useEndTime) or "
			+ "(:useStartTime < useEndTime and useEndTime <= :useEndTime) or "
			+ "(t.useStartTime <= :useStartTime and :useEndTime <= useEndTime))")
	
	public List<Reservation> findTimeOverlapReservations(@Param("useDate") LocalDate useDate,
			@Param("useStartTime") LocalTime useStartTime, @Param("useEndTime") LocalTime useEndTime);
	
	
	
}
