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
	 * �t�H�[���̒l�Ɣ�r����DB����擾�����p�X���[�h�͈Í�������Ă���̂Ńt�H�[���̒l���Í������邽�߂ɗ��p
	 * 
	 * @return bCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	/**
	 * �ÓI�t�@�C���̐ݒ�
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/image/**", "/css/**", "/javascript/**");
	}

	/**
	 * HTTP���N�G�X�g�֘A�̐ݒ�
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login") // ���O�C���y�[�W�̓R���g���[�����o�R���Ȃ��̂�ViewName�Ƃ̕R�t�����K�v
				.loginProcessingUrl("/sign_in") // �t�H�[����SubmitURL�A����URL�փ��N�G�X�g��������ƔF�؏��������s�����
				.usernameParameter("username") // ���N�G�X�g�p�����[�^��name�����𖾎�
				.passwordParameter("password").successForwardUrl("/reservation_list").failureUrl("/login?error")
				.permitAll().and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll();
	}

	/**
	 * ���O�C���F�؊֘A�̐ݒ�
	 */
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.inMemoryAuthentication().withUser("dummyuser").password("dummypassword").roles("DUMMY");
		auth.userDetailsService(loginApplicationService).passwordEncoder(passwordEncoder());
	}
}