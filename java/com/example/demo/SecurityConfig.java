package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// データソース
	@Autowired
	private DataSource dataSource;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		//静的リソースを除外。wejars配下とcss配下を除外。
		web.ignoring().antMatchers("/webjars/**", "/css/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/* 注意０
		 * ログイン不要ページの設定
		 * 順番が大事。
		 * メソッドチェーンは上から順に設定される。
		 * anyRequest().authenticated()の後に.antMatchers("xxx").permitAll()
		 * しても全リクエストに認証が必要になってしまう。
		 */
		http
				.authorizeRequests()
				.antMatchers("/webjars/**").permitAll() //webjarsへアクセス許可
				.antMatchers("/css/**").permitAll() //cssへアクセス許可
				.antMatchers("/login").permitAll() //ログインページは直リンクOK
				.antMatchers("/signup").permitAll() //ユーザー登録画面は直リンクOK
				.antMatchers("/index").permitAll() //ホーム画面は直リンクOK
				//.antMatchers("/rest/**").permitAll() //RESTは直リンクOK
				.antMatchers("/adminpage").hasAuthority("ROLE_ADMIN") //アドミンページをアドミンユーザーに許可
				.anyRequest().authenticated(); //それ以外は直リンク禁止

		//ログイン処理
		http
				.formLogin()
				.loginProcessingUrl("/login") //ログイン処理のパス
				.loginPage("/login") //ログインページの指定
				.failureUrl("/login") //ログイン失敗時の遷移先

				/*※注意１
				 * usernameParameterとpasswordParameterの引数は
				 * htmlのname属性と同じにする。
				 * ここではlogin.htmlのinputタグのname
				 */
				.usernameParameter("accountEmail") //ログインページのユーザーID
				.passwordParameter("accountPassword") //ログインページのパスワード
				.defaultSuccessUrl("/index", true); //ログイン成功後の遷移先

		//ログアウト処理
		http
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //GETでログアウトする用。普通はPOSTなので不要。
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login");

		//CSRF対策を無効にする。
		//http.csrf().disable();
	}

	// ログイン時のアカウント情報を、DBから取得する
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication()
				.dataSource(dataSource)

				/* 注意2
				 * メールアドレスとパスワードを取得するSQL文を引数に入れる
				 * where句の後にand句を付け足すとエラー出る。これでハマった・・・。
				 * ユーザ情報を取得するクエリを設定する。取得するデータは、「ユーザID」、「パスワード」、「有効フラグ」の順とする。
				 * 「有効フラグ」による認証判定を行わない場合には、「有効フラグ」のSELECT結果を「true」固定とする。
				 * なお、ユーザを一意に取得できるクエリを記述すること。複数件数取得された場合には、１件目のレコードがユーザとして使われる。
				 */
				.usersByUsernameQuery("SELECT accountEmail, accountPassword, true FROM account WHERE accountEmail = ?")

				/* 注意３
				 *  ユーザーのロールを取得するSQL文を引数に入れる
				 *  ユーザの権限を取得するクエリを設定する。取得するデータは、「ユーザID」、「権限ID」の順とする。
				 *  認可の機能を使用しない場合は、「権限ID」は任意の固定値でよい。
				 */
				.authoritiesByUsernameQuery("SELECT accountEmail, accountRole FROM account WHERE accountEmail = ?")
				.passwordEncoder(passwordEncoder());
	}
}
