package jp.co.dcf.rrs.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.dcf.rrs.application.service.LoginApplicationService;

@Controller
public class LoginController {
	@Autowired
	LoginApplicationService loginApplicationService;

	@RequestMapping({ "/", "/login" })
	public String index() {
		return "/login";
	}
}