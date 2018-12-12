package govind.config;

import govind.filter.TimeFilter;
import org.apache.commons.collections.ListUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig {

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
