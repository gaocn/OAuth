package govind.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import govind.enumeration.LoginType;
import govind.propeties.SecurityCoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @aurhor: govind
 * 根据不同登录类型，处理登录成功后的返回结果
 */
@Component
@Slf4j
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private SecurityCoreProperties securityCoreProperties;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		log.info("认证成功");
		if (LoginType.JSON.equals(securityCoreProperties.getBrowser().getLoginType())) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(authentication));
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}
}
