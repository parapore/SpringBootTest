package com.example.demo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.AccountModel;

@Controller
public class SignupController {

	@Autowired
	AccountMapper accountMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	//ラジオボタンの実装
	private Map<String, String> radioAccountRole;

	//ラジオボタンの初期化メソッド
	private Map<String, String> initRadioAccountRole() {

		Map<String, String> radio = new LinkedHashMap<>();

		//一般ユーザー、管理者をMapに格納
		radio.put("一般", "ROLE_GENERAL");
		radio.put("管理者", "ROLE_ADMIN");

		return radio;
	}

	@GetMapping("/signup")
	public String getSignupPage(@ModelAttribute AccountModel acModel, Model model) {

		//ラジオボタンの初期化メソッド呼び出し
		radioAccountRole = initRadioAccountRole();

		//ラジオボタン用のMapをModelに登録
		model.addAttribute("radioAccountRoleModel", radioAccountRole);
		return "signup";
	}

	@PostMapping("/signup")
	public String executeSignUp(
			@ModelAttribute @Validated AccountModel acModel,
			BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			System.out.println(result);
			return getSignupPage(acModel, model);
		}

		// アカウント新規登録処理
		accountMapper.insertAccount(
				acModel.getAccountEmail(),
				passwordEncoder.encode(acModel.getAccountPassword()), // パスワードを暗号化
				acModel.getAccountName(),
				acModel.getAccountRole());

		//登録したアカウント情報を取得
		AccountModel am = accountMapper.getAccount(
				acModel.getAccountEmail());

		//アカウント情報をModelクラスに格納
		model.addAttribute("accountModel", am);

		return "index";
	}
}
