package jp.co.dcf.rrs.domain.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.dcf.rrs.domain.entity.ReservationTblEntity;
import jp.co.dcf.rrs.domain.entity.pk.ReservationTblEntityPK;

@Repository
public interface ReservationTblRepository extends JpaRepository<ReservationTblEntity, ReservationTblEntityPK> {
	/**
	 * 利用日検索
	 * 
	 * @param useDate
	 * @return
	 */
	public List<ReservationTblEntity> findByUseDate(LocalDate useDate);

	/**
	 * 重複時間検索
	 * 
	 * @param useDate
	 * @param useStartTime
	 * @param useEndTime
	 * @return
	 */
	@Query("select t from ReservationTblEntity t where t.useDate = :useDate and ("
			+ "(:useStartTime <= t.useStartTime and useEndTime <= :useEndTime) or "
			+ "(:useStartTime <= t.useStartTime and t.useStartTime < :useEndTime) or "
			+ "(:useStartTime < useEndTime and useEndTime <= :useEndTime) or "
			+ "(t.useStartTime <= :useStartTime and :useEndTime <= useEndTime))")
	public List<ReservationTblEntity> findTimeOverlapReservation(@Param("useDate") LocalDate useDate,
			@Param("useStartTime") LocalTime useStartTime, @Param("useEndTime") LocalTime useEndTime);
}
