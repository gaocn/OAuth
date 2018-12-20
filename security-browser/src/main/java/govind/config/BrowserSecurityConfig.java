package govind.config;

import govind.handler.AuthenticationFailedHandler;
import govind.handler.AuthenticationSuccessHandler;
import govind.propeties.SecurityCoreProperties;
import govind.validate.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

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

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		//只能执行一次，用于创建数据库表保存remember-me的用户
//		tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//添加验证码过滤器
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		validateCodeFilter.setFailedHandler(authenticationFailedHandler);
		validateCodeFilter.setSecurityCoreProperties(securityCoreProperties);
		validateCodeFilter.afterPropertiesSet();

		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
				.formLogin()
				//自定义登录界面
				.loginPage("/authentication/require")
				//自定义登录路径
				.loginProcessingUrl("/authentication/form")
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailedHandler)
				.and()
				.rememberMe()
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(securityCoreProperties.getBrowser().getRememberMeSeconds())
				.userDetailsService(userDetailsService)
				.and()
				.authorizeRequests()
				.antMatchers("/authentication/require",
						securityCoreProperties.getBrowser().getLoginPage(),"/code/image").permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.csrf().disable();
	}






















}
