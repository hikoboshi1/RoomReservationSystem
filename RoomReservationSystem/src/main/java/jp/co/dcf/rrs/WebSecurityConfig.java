package jp.co.dcf.rrs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jp.co.dcf.rrs.application.service.LoginApplicationService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginApplicationService loginApplicationService;

	/**
	 * フォームの値と比較するDBから取得したパスワードは暗号化されているのでフォームの値も暗号化するために利用
	 * 
	 * @return bCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	/**
	 * 静的ファイルの設定
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/image/**", "/css/**", "/javascript/**");
	}

	/**
	 * HTTPリクエスト関連の設定
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login") // ログインページはコントローラを経由しないのでViewNameとの紐付けが必要
				.loginProcessingUrl("/sign_in") // フォームのSubmitURL、このURLへリクエストが送られると認証処理が実行される
				.usernameParameter("username") // リクエストパラメータのname属性を明示
				.passwordParameter("password").successForwardUrl("/reservation_list").failureUrl("/login?error")
				.permitAll().and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll();
	}

	/**
	 * ログイン認証関連の設定
	 */
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.inMemoryAuthentication().withUser("dummyuser").password("dummypassword").roles("DUMMY");
		auth.userDetailsService(loginApplicationService).passwordEncoder(passwordEncoder());
	}
}