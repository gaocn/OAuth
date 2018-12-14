package govind.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
@Slf4j
public class MockQueue {
	private ArrayBlockingQueue<String> toBeConsumedQueue;
	private ThreadPoolExecutor executor;
	public MockQueue() {
		toBeConsumedQueue = new ArrayBlockingQueue<>(10);
		executor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));
	}

	public void produceInQueue1(String orderid) {
		executor.submit(()->{
				log.info("Queue-Thread[produceInQueue1] enqueue order[{}]",orderid);
				try {
					toBeConsumedQueue.put(orderid);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		});
	}

	public String consumeInQueue1() throws InterruptedException, ExecutionException, TimeoutException {
		Future<String> result = executor.submit(()->{
				String orderid = toBeConsumedQueue.take();
				log.info("Queue-Thread[consumeInQueue1] dequeue order[{}]", orderid);
				return orderid;
		});
		return result.get(60, TimeUnit.SECONDS);
	}
}
