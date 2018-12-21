package govind.validate;

import govind.validate.code.ValidateCodeType;
import govind.validate.code.processor.ValidateCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component(value = "validateCodeProcessorHolder")
public class ValidateCodeProcessorHolder {
	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

	public ValidateCodeProcessor findValidateCodeProcessorByType(ValidateCodeType type) {
		return findValidateCodeProcessorByType(type.toString().toLowerCase());
	}

	public ValidateCodeProcessor findValidateCodeProcessorByType(String type) {
		String processorName = type + ValidateCodeProcessor.class.getSimpleName();
		ValidateCodeProcessor processor = validateCodeProcessorMap.get(processorName);
		log.info("验证码处理器: {}",  processor);
		if (processor == null)  {
			throw new RuntimeException(processorName + "不存在");
		}
		return processor;
	}
}
