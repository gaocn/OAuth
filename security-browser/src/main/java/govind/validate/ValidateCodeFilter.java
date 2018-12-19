package govind.validate;

import govind.controller.ValidateCodeController;
import govind.exception.ValidateCodeException;
import govind.handler.AuthenticationFailedHandler;
import govind.propeties.SecurityCoreProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
	@Setter
	private AuthenticationFailedHandler failedHandler;

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@Setter
	private SecurityCoreProperties securityCoreProperties;

	private Set<String> urls = new HashSet<>();

	private AntPathMatcher matcher = new AntPathMatcher();

	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		String[] urlsToFilter = StringUtils.splitByWholeSeparator(securityCoreProperties
				.getBrowser().getImage().getUrl(),",");
		for (String url: urlsToFilter) {
			urls.add(url);
		}
		urls.add("/authentication/form");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		boolean doFilter = false;
		//检查是否是配置的端口
		for (String url : urls) {
			if (matcher.match(url, request.getRequestURI())) {
				doFilter =  true;
			}
		}

		if (doFilter) {
			try {
				validate(new ServletWebRequest(request, response));
			} catch (ValidateCodeException e) {
				failedHandler.onAuthenticationFailure(request, response, e);
				//异常处理后要返回，而不是继续处理
				return;
			}
		}
		filterChain.doFilter(request,response);
	}

	private void validate(ServletWebRequest request) throws ServletRequestBindingException, ValidateCodeException {
		ImageCode imageCode = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);
		String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imagecode");

		if(StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("验证码不能为空！");
		}

		if(imageCode == null) {
			throw new ValidateCodeException("验证码不存在！");
		}

		if(imageCode.isExpired()) {
			throw new ValidateCodeException("验证码过期！");
		}

		if(!StringUtils.equals(codeInRequest, imageCode.getCode()))  {
			throw new ValidateCodeException("验证码不匹配！");
		}
		sessionStrategy.removeAttribute(request,  ValidateCodeController.SESSION_KEY);
	}














}
