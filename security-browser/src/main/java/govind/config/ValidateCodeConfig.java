package govind.config;

import govind.validate.code.ValidateCodeGenerator;
import govind.validate.code.ValidateCodeGeneratorImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeConfig {
	@Bean
	@ConditionalOnMissingBean(name = "validateCodeGenerator")
	public ValidateCodeGenerator validateCodeGenerator() {
		ValidateCodeGenerator validateCodeGenerator = new ValidateCodeGeneratorImpl();
		return validateCodeGenerator;
	}
}
