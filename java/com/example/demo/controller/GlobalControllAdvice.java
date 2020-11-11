package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class GlobalControllAdvice {

	@ExceptionHandler(Exception.class)
	public String ExceptionHandler(Exception e, Model model) {

		model.addAttribute("error", "内部サーバーエラー");

		model.addAttribute("message", "サーバーエラーが発生しました。");

		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";
	}

	@ExceptionHandler(NullPointerException.class)
	public String NullPointerExceptionHandler(Exception e, Model model) {

		model.addAttribute("error", "ぬるぽ");

		model.addAttribute("message", "■━⊂( ･∀･) 彡 ｶﾞｯ");

		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";
	}

}
