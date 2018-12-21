package govind.validate.code.sender;

public interface SmsCodeSender {
	void send(String mobile, String code);
}
