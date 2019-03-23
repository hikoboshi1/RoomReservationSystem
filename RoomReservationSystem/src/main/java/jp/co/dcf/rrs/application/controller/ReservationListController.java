package jp.co.dcf.rrs.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.dcf.rrs.application.service.ReservationListApplicationService;

@Controller
public class ReservationListController {
	@Autowired
	ReservationListApplicationService reservationListApplicationService;

	@RequestMapping({ "/reservation_list" })
	public String index() {
		return "/reservation_list";
	}
}