package govind.sms;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";
	private String mobileParameter = "mobile";
	private boolean postOnly = true;

	public SmsAuthenticationFilter() {
		super(new AntPathRequestMatcher("/authentication/mobile", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (this.postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		} else {
			String mobile = this.obtainMobile(request);
			if (mobile == null) {
				mobile = "";
			}
			mobile = mobile.trim();

			SmsAuthenticationToken authRequest = new SmsAuthenticationToken(mobile);

			this.setDetails(request, authRequest);
			return this.getAuthenticationManager().authenticate(authRequest);
		}
	}


	protected String obtainMobile(HttpServletRequest request) {
		return request.getParameter(this.mobileParameter);
	}

	protected void setDetails(HttpServletRequest request, SmsAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}

	public void setUsernameParameter(String mobileParameter) {
		Assert.hasText(mobileParameter, "Mobile parameter must not be empty or null");
		this.mobileParameter = mobileParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getSmscodeParameter() {
		return this.mobileParameter;
	}

}
