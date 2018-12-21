package govind.validate.code.processor;

import govind.validate.ImageCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

@Component(value = "imageValidateCodeProcessor")
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {
	@Override
	public void send(ServletWebRequest request, ImageCode validateCode) {
		try {
			ImageIO.write(validateCode.getImage(), "JPEG", request.getResponse().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
