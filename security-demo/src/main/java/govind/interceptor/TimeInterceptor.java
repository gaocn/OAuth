package govind.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Slf4j
@Component
public class TimeInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		HandlerMethod handlerMethod =  (HandlerMethod)o;
		String controllerName = handlerMethod.getBean().getClass().getSimpleName();
		String methodName =  handlerMethod.getMethod().getName();

		log.info("[拦截器]preHandle controller["+controllerName+"] method["+methodName+"]");
		httpServletRequest.setAttribute("startTime", System.currentTimeMillis());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
		HandlerMethod handlerMethod =  (HandlerMethod)o;
		String controllerName = handlerMethod.getBean().getClass().getSimpleName();
		String methodName =  handlerMethod.getMethod().getName();
		Long interval = System.currentTimeMillis() - (Long) httpServletRequest.getAttribute("startTime");
		log.info("[拦截器]postHandle controller["+controllerName+"] method["+methodName+"] 执行时间：" + interval + "ms");
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
		HandlerMethod handlerMethod =  (HandlerMethod)o;
		String methodName =  handlerMethod.getMethod().getName();
		if (e == null)  {
			log.info("[拦截器]afterCompletion method["+ methodName +"]");
		} else {
			log.info("[拦截器]afterCompletion method["+ methodName +"],有异常！");
			e.printStackTrace();
		}
	}
}
