package govind.validate.code.generator;

import govind.validate.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {
	ValidateCode generate(ServletWebRequest request);
}
