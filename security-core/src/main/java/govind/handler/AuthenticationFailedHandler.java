package govind.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import govind.enumeration.LoginType;
import govind.propeties.SecurityCoreProperties;
import govind.response.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author govind
 */
@Component
@Slf4j
public class AuthenticationFailedHandler extends SimpleUrlAuthenticationFailureHandler {
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private SecurityCoreProperties securityCoreProperties;


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		log.info("认证失败");
		switch (securityCoreProperties.getBrowser().getLoginType())  {
			case LoginType.REDIRECT:
				super.onAuthenticationFailure(request, response, exception);
				break;
			case LoginType.JSON:
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().write(objectMapper.writeValueAsString(exception));
				response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
				break;
		}
	}
}
