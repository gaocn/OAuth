package govind.validate.code.processor;

import govind.validate.ValidateCode;
import govind.validate.code.sender.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

@Component(value = "smsValidateCodeProcessor")
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode>{

	@Autowired
	SmsCodeSender smsCodeSender;

	@Override
	public void send(ServletWebRequest request, ValidateCode validateCode) {
		String mobile = null;
		try {
			mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
			smsCodeSender.send(mobile, validateCode.getCode());
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
	}
}
