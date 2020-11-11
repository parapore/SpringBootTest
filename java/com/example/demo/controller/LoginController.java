package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.AccountModel;

//DIコンテナーに登録
@Controller
public class LoginController {
	@Autowired
	AccountMapper accountMapper;

	@GetMapping("/login")
	public String getResponse(@ModelAttribute AccountModel acModel) {

		//一般ユーザー画面へ遷移
		return "/login";
	}

	/**
	 * @ModelAttribute AccountModel acModelの中身説明。
	 * @param acModelにはメールとパスワードだけ入ってる。他はnull
	 * こんな感じで↓
	 * AccountModel(accountId=null, accountEmail=eee, accountPassword=aaa, accountName=null, accountRole=null, accountCreateTime=null, accountUpdateTime=null)
	 */

	@PostMapping("/login")
	public String postLogin(
			@ModelAttribute @Validated AccountModel acModel,
			BindingResult result,
			Model model) {

		/*
		 * かつてのログイン処理。
		 * 現在はSpringSecurity(SecurityConfig.java)がログイン処理を担う。
		 */
		//		System.out.println("logincon" + acModel);
		//		//入力エラーチェック
		//		if (result.hasErrors()) {
		//			System.out.println(result + "入力エラー");
		//			return getResponse(acModel);
		//		}
		//
		//		//アカウント情報を取得
		//		AccountModel am = accountMapper.getAccount(
		//				acModel.getAccountEmail());
		//
		//		//アカウント有無を判定。
		//		if (am == null) {
		//			System.out.println("なる！！！");
		//
		//			//todo 画面遷移せずにログインエラーメッセージを赤字表示させたい。
		//			//さらに入力内容も残っているとなお可。
		//			return "login";
		//		}
		//
		//		//アカウント情報をModelクラスに格納
		//		model.addAttribute("accountModel", am);

		return "redirect:/index";
	}
}
