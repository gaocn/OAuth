package govind.config;

import govind.handler.AuthenticationFailedHandler;
import govind.handler.AuthenticationSuccessHandler;
import govind.propeties.SecurityCoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private SecurityCoreProperties securityCoreProperties;

	@Autowired
	private AuthenticationFailedHandler authenticationFailedHandler;
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				//自定义登录界面
				.loginPage("/authentication/require")
				//自定义登录路径
				.loginProcessingUrl("/authentication/form")
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailedHandler)
				.and()
				.authorizeRequests()
				.antMatchers("/authentication/require",
						securityCoreProperties.getBrowser().getLoginPage()).permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.csrf().disable();
	}






















}
