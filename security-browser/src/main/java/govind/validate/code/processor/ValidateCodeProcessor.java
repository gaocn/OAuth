package govind.validate.code.processor;

import govind.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 封装发送图形、短信验证码逻辑
 * 1、generate
 * 2、save to session
 * 3、send to client
 */
public interface ValidateCodeProcessor {

	String SESSION_KEY_PREFIX = "SESSION_KEY_CODE_";

	/**
	 *  create -> save -> send
	 */
	void process(ServletWebRequest request);

	void validate(ServletWebRequest request, ValidateCodeType type) throws ServletRequestBindingException;
}
