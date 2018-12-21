package govind.validate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.image.BufferedImage;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class ImageCode extends ValidateCode {
	transient private BufferedImage image;

	public ImageCode(BufferedImage image, String code, long expireIn) {
		super(code, expireIn);
		this.image = image;
	}
}
