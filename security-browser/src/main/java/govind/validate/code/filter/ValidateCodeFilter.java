package govind.validate.code.filter;

import govind.handler.AuthenticationFailedHandler;
import govind.propeties.SecurityCoreProperties;
import govind.validate.ValidateCodeProcessorHolder;
import govind.validate.code.ValidateCodeType;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
	/**
	 * 验证码校验失败处理器
	 */
	@Setter
	private AuthenticationFailedHandler failedHandler;

	@Setter
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;

	@Setter
	private SecurityCoreProperties securityCoreProperties;

	private Map<String, ValidateCodeType>  urlMap = new HashMap<>();

	private AntPathMatcher matcher = new AntPathMatcher();

	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		addToUrlMap(securityCoreProperties.getBrowser().getImage().getUrl(), ValidateCodeType.IMAGE);
		addToUrlMap("/authentication/form_image",ValidateCodeType.IMAGE);
		addToUrlMap("/authentication/form_sms",ValidateCodeType.SMS);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		ValidateCodeType type = getValidateCodeType(request);

		if (type != null) {
			try {
				log.info("请求[{}]的ValidateCodeType类型: {}", request.getRequestURI(), type);
				validateCodeProcessorHolder.findValidateCodeProcessorByType(type).validate(new ServletWebRequest(request, response), type);
			}catch (Exception e) {
				failedHandler.onAuthenticationFailure(request, response, (AuthenticationException) e);
			}

		}
		filterChain.doFilter(request,response);
	}

	private void addToUrlMap(String urls, ValidateCodeType  type) {
		String[] urlsToFilter = StringUtils.splitByWholeSeparator(urls,",");
		for (String url: urlsToFilter) {
			urlMap.put(url, type);
		}
	}

	private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
		ValidateCodeType type  = null;

		if (request.getMethod().equals(HttpMethod.POST.name())) {
			for (String url : urlMap.keySet()) {
				if (matcher.match(url, request.getRequestURI())) {
					type = urlMap.get(url);
					break;
				}
			}
		}
		return type;
	}















}
