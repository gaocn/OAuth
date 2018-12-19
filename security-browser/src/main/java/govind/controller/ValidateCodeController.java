package govind.controller;

import govind.validate.ImageCode;
import govind.propeties.SecurityCoreProperties;
import govind.validate.code.ValidateCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author govind
 */
@RestController
@Slf4j
public class ValidateCodeController {
	public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@Autowired
	private ValidateCodeGenerator validateCodeGenerator;

	@GetMapping("/code/image")
	public void createImageCode(HttpServletRequest request, HttpServletResponse response) {
		ImageCode imageCode = validateCodeGenerator.generate();
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
		try {
			ImageIO.write(imageCode.getImage(),"JPEG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
