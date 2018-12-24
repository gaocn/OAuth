package govind.config;

import govind.handler.AuthenticationFailedHandler;
import govind.handler.AuthenticationSuccessHandler;
import govind.sms.SmsAuthenticationFilter;
import govind.sms.SmsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author govind
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private AuthenticationSuccessHandler successHandler;
	@Autowired
	private AuthenticationFailedHandler failedHandler;

	@Autowired
	UserDetailsService  userDetailsService;

	@Override
	public void configure(HttpSecurity http) {
		SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
		smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		smsAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
		smsAuthenticationFilter.setAuthenticationFailureHandler(failedHandler);

		SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
		smsAuthenticationProvider.setUserDetailsService(userDetailsService);

		http.authenticationProvider(smsAuthenticationProvider)
				.addFilterBefore(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
