package jp.co.dcf.rrs.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.dcf.rrs.domain.repository.ReservationTblRepository;

@Service
public class ReservationListApplicationService {
	@Autowired
	ReservationTblRepository reservationTblRepository;
}