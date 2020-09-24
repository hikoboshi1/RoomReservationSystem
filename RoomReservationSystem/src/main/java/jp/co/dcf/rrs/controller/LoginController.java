package jp.co.dcf.rrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.dcf.rrs.model.Reservation;
import jp.co.dcf.rrs.service.LoginService;

@Controller
public class LoginController {
	@Autowired
	LoginService loginApplicationService;

	@RequestMapping({ "/login" })
	public String index() {
		return "/login";
	}

	@RequestMapping({ "/" })
	public String loginSuccess() {
		return "redirect:/reservations/list";
	}
}