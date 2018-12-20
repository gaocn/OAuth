package govind.validate.code;

import govind.propeties.SecurityCoreProperties;
import govind.validate.ValidateCode;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class SmsValidateCodeGenerator implements ValidateCodeGenerator {
	@Autowired
	private SecurityCoreProperties securityCoreProperties;

	@Override
	public ValidateCode generate() {
		String code = RandomStringUtils.randomNumeric(securityCoreProperties.getSms().getLength());
		return new ValidateCode(code, securityCoreProperties.getSms().getExpireIn());
	}
}
