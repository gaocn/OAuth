package govind.validate.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ImageCode implements Serializable {
	transient private BufferedImage image;
	private String code;
	private LocalDateTime expireTime;

	public ImageCode(BufferedImage image, String code, long expireIn) {
		this.image = image;
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(this.expireTime);
	}
}
