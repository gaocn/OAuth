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
	 * 验证码图片宽度
	 */
	private int  width = 67;
	/**
	 * 验证码图片高度
	 */
	private int height = 23;
	/**
	 * 验证码长度
	 */
	private int length = 4;
	/**
	 * 验证码过期时间
	 */
	private int expireIn = 60;
}
