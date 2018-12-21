package govind.validate.code.generator;

import govind.propeties.SecurityCoreProperties;
import govind.validate.ValidateCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

@Slf4j
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {
	@Autowired
	private SecurityCoreProperties securityCoreProperties;

	@Override
	public ValidateCode generate(ServletWebRequest request) {
		String code = RandomStringUtils.randomNumeric(securityCoreProperties.getSms().getLength());
		log.info("短信验证码为：{}", code);
		return new ValidateCode(code, securityCoreProperties.getSms().getExpireIn());
	}
}
