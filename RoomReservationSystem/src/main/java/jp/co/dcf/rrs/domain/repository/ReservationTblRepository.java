package jp.co.dcf.rrs.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.dcf.rrs.domain.entity.ReservationTblEntity;
import jp.co.dcf.rrs.domain.entity.id.ReservationTblEntityId;

@Repository
public interface ReservationTblRepository extends JpaRepository<ReservationTblEntity, ReservationTblEntityId> {

}
