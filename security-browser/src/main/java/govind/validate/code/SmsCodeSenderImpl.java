package govind.validate.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsCodeSenderImpl implements SmsCodeSender {

	@Override
	public void send(String mobile, String code) {
		log.info("调用发送短信服务发送短信验证码{}给{}", code, mobile);
	}
}
