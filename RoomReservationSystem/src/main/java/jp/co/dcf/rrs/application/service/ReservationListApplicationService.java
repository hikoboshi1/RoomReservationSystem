package jp.co.dcf.rrs.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.dcf.rrs.domain.entity.ReservationTblEntity;
import jp.co.dcf.rrs.domain.repository.ReservationTblRepository;

@Service
public class ReservationListApplicationService {
	@Autowired
	ReservationTblRepository reservationTblRepository;

	public List<ReservationTblEntity> getReservationList() {
		return reservationTblRepository.findAll();
	}
}