package govind.controller;

import govind.validate.ValidateCodeProcessorHolder;
import govind.validate.code.processor.ValidateCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author govind
 */
@RestController
@Slf4j
public class ValidateCodeController {
	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;

	@GetMapping("/code/{type}")
	public void createImageCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) {
		validateCodeProcessorHolder.findValidateCodeProcessorByType(type).process(new ServletWebRequest(request, response));
	}
}
