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
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
				log.info("进入应用2线程，开始处理队列中的任务,获取订单号{}", orderid);
				if(StringUtils.isEmpty(orderid) || StringUtils.isBlank(orderid)){
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}
				Map<String, String> map = new HashMap<>();
				map.put("orderid", orderid);
				map.put("status","0");
				deferredResultHolder.getMap().get(orderid).setResult(map);
				log.info("进入应用2线程，处理完成");
			}
		});
	}
}
