package govind.code;

import govind.validate.ImageCode;
import govind.validate.code.generator.ValidateCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.ServletWebRequest;

//@Component("validateCodeGenerator")
@Slf4j
public class DemoValidateCodeGenerator implements ValidateCodeGenerator {
	@Override
	public ImageCode generate(ServletWebRequest request) {
		log.info("更高级别的验证码生成器！");
		return null;
	}
}
