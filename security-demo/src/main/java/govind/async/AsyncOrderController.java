package govind.async;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @Author: govind
 */
@ApiOperation("异步处理RESTFUl API")
@Controller
@Slf4j
public class AsyncOrderController {

	@ApiOperation("创建订单")
	@RequestMapping("/order")
	public @ResponseBody Callable<Map<String,String>> create() {
		log.info("主线程开始");
		Callable<Map<String,String>> callable  = () -> {
			log.info("副线程开始");
			String orderId = RandomStringUtils.randomAlphanumeric(16);
			log.info("订单生成成功，订单号：{}", orderId);
			Thread.sleep(1000);
			log.info("副线程返回");
			Map<String,String> orderInfo = new HashMap<>();
			orderInfo.put("orderId", orderId);
			return orderInfo;
		};
		log.info("主线程返回");

		return callable;
	}












}
