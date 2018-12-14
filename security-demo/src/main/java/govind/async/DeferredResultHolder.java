package govind.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class DeferredResultHolder {
	private Map<String, DeferredResult<Map<String, String>>> map = new HashMap<>();

	public Map<String, DeferredResult<Map<String, String>>> getMap() {
		return map;
	}
}
