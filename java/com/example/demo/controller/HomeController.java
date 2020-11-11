package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.AccountModel;

@Controller
public class HomeController {

	@Autowired
	AccountMapper accountMapper;

	@GetMapping("/index")
	public String getIndex() {
		return "index";
	}

	@GetMapping("/adminpage")
	public String getAdminpage(Model model) {

		// ログイン情報の取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String accountEmail = auth.getName();
		AccountModel am = accountMapper.getAccount(accountEmail);

		//ログインアカウントの情報をモデルに追加
		model.addAttribute("accountModel", am);
		System.out.println(am);

		return "adminpage";
	}

	@GetMapping("/userpage")
	public String getUserpage(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String accountEmail = auth.getName();
		AccountModel am = accountMapper.getAccount(accountEmail);

		model.addAttribute("accountModel", am);
		System.out.println(am);

		return "userpage";
	}
}
