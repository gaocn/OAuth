package govind.validate.code.processor;

import govind.exception.ValidateCodeException;
import govind.propeties.SecurityCoreProperties;
import govind.validate.ImageCode;
import govind.validate.ValidateCode;
import govind.validate.code.ValidateCodeType;
import govind.validate.code.generator.ValidateCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

@Slf4j
public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor {

	@Autowired
	private SecurityCoreProperties securityCoreProperties;

	/**
	 * 保存验证码到会话中
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	/**
	 * 收集系统内部所有ValidateCodeGenerator实例
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> generatorMap;

	/**
	 * 1. create validate code
	 * 2. save validate code to session
	 * 3. send validate code to client
	 */
	@Override
	public void process(ServletWebRequest request) {
		T validateCode = generate(request);
		saveToSession(request, validateCode);
		send(request, validateCode);
	}

	private T generate(ServletWebRequest request){
		ValidateCodeType type = getProcessorType(request);
		String generatorName = type.name().toLowerCase() + "ValidateCodeGenerator";
		return (T) generatorMap.get(generatorName).generate(request);
	}

	private void saveToSession(ServletWebRequest request, T validateCode) {
		sessionStrategy.setAttribute(request, getSessionKey(getProcessorType(request)), validateCode);
	}

	protected abstract void send(ServletWebRequest request, T validateCode);

	/**
	 *  获取验证码类型：image、sms
	 */
	 private ValidateCodeType getProcessorType(ServletWebRequest request) {
	 	ValidateCodeType type = null;
	 	switch (StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/")) {
			case "sms":
				type = ValidateCodeType.SMS;
				break;
			case "image":
				type = ValidateCodeType.IMAGE;
				break;
			default:
				throw new  RuntimeException("不支持的ValidateCodeType类型");
		}
		return type;
	}

	private String getSessionKey(ValidateCodeType type) {
		return SESSION_KEY_PREFIX + type.name().toUpperCase();
	}

	@Override
	public void validate(ServletWebRequest request, ValidateCodeType type) throws ServletRequestBindingException {
	 	String sessionKey =  getSessionKey(type);
		T validateCode = (T) sessionStrategy.getAttribute(request, sessionKey);
		String codeInRequest = null;
			String codeNameInRequest = type.getParamNameOnValidate();
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), codeNameInRequest);
			log.info("请求表单中验证码参数名：{}，请求表单中的验证为：{}, 会话中的验证码：{}", codeNameInRequest, codeInRequest, validateCode);

		if(StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("验证码不能为空！");
		}

		if(validateCode == null) {
			throw new ValidateCodeException("验证码不存在！");
		}

		if(validateCode.isExpired()) {
			throw new ValidateCodeException("验证码过期！");
		}

		if(!StringUtils.equals(codeInRequest, validateCode.getCode()))  {
			throw new ValidateCodeException("验证码不匹配！");
		}
		sessionStrategy.removeAttribute(request,  sessionKey);
	}
}
