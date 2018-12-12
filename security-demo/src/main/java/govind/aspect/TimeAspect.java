package govind.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class TimeAspect {

	@Around("execution(* govind.controller.UserController.*(..))")
	public Object handleUserCtrollerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		log.info("[切片] time aspect start");
		log.info("[切片] {},{},参数：{}",
				proceedingJoinPoint.getTarget().getClass().getSimpleName(),
				proceedingJoinPoint.getSignature(),
				Arrays.toString(proceedingJoinPoint.getArgs()));
		long startTime =  System.currentTimeMillis();
		Object result = proceedingJoinPoint.proceed();
		log.info("[切片] 交易执行时间：{}, 执行结果：{}", (System.currentTimeMillis()-startTime), result);
		log.info("[切片] time aspect end");
		return result;
	}
}
