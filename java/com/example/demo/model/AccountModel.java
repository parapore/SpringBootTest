package com.example.demo.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

//@Dataは自動でセッターゲッター作る
// springじゃなくてlombokの機能
@Data
public class AccountModel {

	private Integer accountId;

	@NotBlank
	@Size(min = 3, max = 25)
	@Email
	private String accountEmail;

	@NotBlank
	//@Size(min = 3, max = 25)
	//@Pattern(regexp = "[a-zA-Z0-9]*")
	private String accountPassword;

	@NotBlank
	@Size(min = 3, max = 15)
	private String accountName;

	@NotBlank
	@Size(min = 10, max = 12)
	private String accountRole;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date accountCreateTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date accountUpdateTime;
}
