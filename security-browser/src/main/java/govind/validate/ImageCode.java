package govind.validate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

@Setter
@Getter
@AllArgsConstructor
public class ImageCode extends ValidateCode {
	transient private BufferedImage image;

	public ImageCode(BufferedImage image, String code, long expireIn) {
		super(code, expireIn);
		this.image = image;
	}
}
