package govind.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Slf4j
public class TimeFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("[过滤器]"+getClass().getSimpleName() + " initializing ......");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		long startTime = System.currentTimeMillis();
		filterChain.doFilter(servletRequest, servletResponse);
		log.info("[过滤器]"+"交易执行时间："+ (System.currentTimeMillis() - startTime) + "ms");
	}

	@Override
	public void destroy() {
		log.info("[过滤器]"+ getClass().getSimpleName() + " is destroying .....");
	}
}
