package govind.propeties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImageValidateCodeProperties extends ValidateCodeProperties{
	/**
	 * 验证码图片宽度
	 */
	private int  width = 67;
	/**
	 * 验证码图片高度
	 */
	private int height = 23;

	public ImageValidateCodeProperties() {
		super(4);
	}
}
