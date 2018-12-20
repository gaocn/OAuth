package govind.validate.code;

public interface SmsCodeSender {
	void send(String mobile, String code);
}
