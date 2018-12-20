package govind.propeties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCodeProperties {
	/**
	 * 验证码长度
	 */
	private int length = 4;
	/**
	 * 验证码过期时间
	 */
	private int expireIn = 60;

	/**
	 * 默认是来自"/authentication/form"的请求进行验证码验证
	 * 可以通过配置url自定义哪些接口需要校验验证码
	 */
	private String url;

	public ValidateCodeProperties(int length) {
		this.length = length;
	}
}
