package jp.co.dcf.rrs.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.dcf.rrs.application.service.ReservationListApplicationService;

@Controller
public class ReservationEditController {
	@Autowired
	ReservationListApplicationService reservationListApplicationService;

	@RequestMapping({ "/reservation_edit" })
	public String index(Model model, @RequestParam("use_date") String useDate,
			@RequestParam("use_start_time") String useStartTime) {
		model.addAttribute("useDate", useDate);
		model.addAttribute("useStartTime", useStartTime);
		return "/reservation_edit";
	}
}