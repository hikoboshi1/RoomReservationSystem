package jp.co.dcf.rrs.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.dcf.rrs.application.service.ReservationListApplicationService;
import jp.co.dcf.rrs.domain.entity.ReservationTblEntity;

@Controller
public class ReservationListController {
	@Autowired
	ReservationListApplicationService reservationListApplicationService;

	@RequestMapping({ "/reservation_list" })
	public String index(Model model) {
		List<ReservationTblEntity> reservationList = reservationListApplicationService.getReservationList();
		model.addAttribute("reservationList", reservationList);
		return "/reservation_list";
	}
}