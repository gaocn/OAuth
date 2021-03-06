package govind.config;

import govind.validate.code.generator.ImageValidateCodeGenerator;
import govind.validate.code.generator.SmsValidateCodeGenerator;
import govind.validate.code.generator.ValidateCodeGenerator;
import govind.validate.code.sender.SmsCodeSender;
import govind.validate.code.sender.SmsCodeSenderImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {
	@Bean
	@ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
	public ValidateCodeGenerator imageValidateCodeGenerator() {
		ValidateCodeGenerator imageValidateCodeGenerator = new ImageValidateCodeGenerator();
		return imageValidateCodeGenerator;
	}

	@Bean
	@ConditionalOnMissingBean(name = "smsValidateCodeGenerator")
	public ValidateCodeGenerator smsValidateCodeGenerator() {
		SmsValidateCodeGenerator smsValidateCodeGenerator = new SmsValidateCodeGenerator();
		return smsValidateCodeGenerator;
	}

	@Bean
	@ConditionalOnMissingBean(SmsCodeSender.class)
	public SmsCodeSender smsCodeSender() {
		SmsCodeSender smsCodeSender =  new SmsCodeSenderImpl();
		return smsCodeSender;
	}
}
