package govind.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Component
public class QueueListenerAndProcessor implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private MockQueue consume;
	@Autowired
	private DeferredResultHolder deferredResultHolder;

	private ThreadPoolExecutor executor = new ThreadPoolExecutor(2,  2, 2, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		executor.submit(() ->{
			while (true) {
				String orderid = null;
				try {
					orderid = consume.consumeInQueue1();
				} catch (Exception e) {
				}
				if(StringUtils.isEmpty(orderid) || StringUtils.isBlank(orderid)){
					log.info("获取订单号超时，进行重试....");
					continue;
				}
				log.info("进入应用2线程，开始处理队列中的任务,获取订单号{}", orderid);
				Map<String, String> map = new HashMap<>();
				map.put("orderid", orderid);
				map.put("status","0");
				deferredResultHolder.getMap().get(orderid).setResult(map);
				log.info("进入应用2线程，处理完成");
			}
		});
	}
}
