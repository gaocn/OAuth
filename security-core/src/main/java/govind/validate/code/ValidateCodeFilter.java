package govind.validate.code;

import govind.controller.ValidateCodeController;
import govind.exception.ValidateCodeException;
import govind.handler.AuthenticationFailedHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter {
	@Setter
	private AuthenticationFailedHandler failedHandler;

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		//是登录请求
		if (StringUtils.equals("/authentication/form", request.getRequestURI()) && StringUtils.equals("POST", request.getMethod())) {
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
