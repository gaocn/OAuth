package govind.config;

import govind.filter.TimeFilter;
import govind.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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


}
