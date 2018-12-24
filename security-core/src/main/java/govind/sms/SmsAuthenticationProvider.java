package govind.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @authr govind
 */
@Slf4j
public class SmsAuthenticationProvider implements AuthenticationProvider {

	@Getter
	@Setter
	private UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;
		String mobile = (String) smsAuthenticationToken.getPrincipal();
		log.info("根据用户手机号{}验证用户是否存在，若存在则返回认证后的TOKEN", mobile);
		UserDetails user = userDetailsService.loadUserByUsername(mobile);
		if (user == null) {
			throw new IllegalArgumentException("无法根据手机号获取用户信息");
		}
		SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(user, user.getAuthorities());
		authenticationResult.setDetails(smsAuthenticationToken.getDetails());
		log.info("完成用户认证");
		return authenticationResult;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SmsAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
