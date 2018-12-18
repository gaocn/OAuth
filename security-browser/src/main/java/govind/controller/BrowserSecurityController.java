package govind.controller;

import govind.propeties.SecurityCoreProperties;
import govind.response.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: govind
 */
@RestController
@Slf4j
public class BrowserSecurityController {

	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	@Autowired
	private SecurityCoreProperties securityCoreProperties;

	@RequestMapping("/authentication/require")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) {
		SavedRequest request1 = requestCache.getRequest(request, response);
		if (requestCache != null)  {
			String targetUrl = request1.getRedirectUrl();
			log.info("引发跳转的请求为：{}", targetUrl);
			if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")){
				try {
					log.info("跳转登录页：{}",securityCoreProperties.getBrowser().getLoginPage());
					redirectStrategy.sendRedirect(request, response, securityCoreProperties.getBrowser().getLoginPage());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new SimpleResponse("访问的用户需要身份验证，请引导用户到登录页");
	}
}
