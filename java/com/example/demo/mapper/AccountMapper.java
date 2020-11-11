package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.AccountModel;

@Mapper
public interface AccountMapper {

	/**
	 * アカウント情報の取得
	 * @param accountEmail メール
	 * @param accountPassword パスワード
	 * @return アカウント情報
	 */
	public AccountModel getAccount(String accountEmail);

	/**
	 * アカウント情報の登録
	 * @param accountEmail メール
	 * @param accountPassword パスワード
	 * @param accountName 名前
	 * @param accountRole 権限
	 */
	public void insertAccount(
			String accountEmail, String accountPassword,
			String accountName, String accountRole);
}
