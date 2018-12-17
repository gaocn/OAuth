package govind.config;

import govind.filter.TimeFilter;
import govind.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private TimeInterceptor timeInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(timeInterceptor);
	}

	@Bean
	public FilterRegistrationBean timeFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

		filterRegistrationBean.setFilter(new TimeFilter());
		List<String> urlPatterns = new ArrayList<>();
		urlPatterns.add("/user/*");
		filterRegistrationBean.setUrlPatterns(urlPatterns);

		return filterRegistrationBean;
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setQueueCapacity(10);
		executor.setKeepAliveSeconds(20);
		configurer.setDefaultTimeout(5000);
		executor.initialize();
		configurer.setTaskExecutor(executor);
	}
}
