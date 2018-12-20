package govind.controller;

import govind.validate.ImageCode;
import govind.propeties.SecurityCoreProperties;
import govind.validate.ValidateCode;
import govind.validate.code.SmsCodeSender;
import govind.validate.code.ValidateCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
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
	public static final String SESSION_KEY_IMAGE = "SESSION_KEY_CODE_IMAGE";
	public static final String SESSION_KEY_SMS = "SESSION_KEY_CODE_SMS";

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@Autowired
	private ValidateCodeGenerator imageValidateCodeGenerator;

	@Autowired
	private ValidateCodeGenerator smsValidateCodeGenerator;

	@Autowired
	private SmsCodeSender smsCodeSender;

	@GetMapping("/code/image")
	public void createImageCode(HttpServletRequest request, HttpServletResponse response) {
		ImageCode imageCode = (ImageCode) imageValidateCodeGenerator.generate();
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_IMAGE, imageCode);
		try {
			ImageIO.write(imageCode.getImage(),"JPEG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/code/sms")
	public void createSmsCode(HttpServletRequest request, HttpServletResponse response) {
		ValidateCode smsCode = smsValidateCodeGenerator.generate();
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_SMS, smsCode);
		try {
			String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
			smsCodeSender.send(mobile, smsCode.getCode());
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
	}






















}
