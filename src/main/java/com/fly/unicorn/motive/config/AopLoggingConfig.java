package com.fly.unicorn.motive.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AopLoggingConfig {
	@Around("execution(* com.fly.unicorn.motive.controller.*.*(..)) " +
		"&& !@annotation(com.fly.unicorn.motive.config.NoLogging)")
	public Object dataHandlingLog(ProceedingJoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();

		StringBuilder sb = new StringBuilder();
		for (Object object : joinPoint.getArgs()) {
			sb.append(object);
		}
		log.info("[{} :: request:{}]", methodName, sb);

		ResponseEntity response = (ResponseEntity) joinPoint.proceed();
		if (response != null) {
			log.info("[{} Response :: Code:{} Data:{}]",
				methodName, response.getStatusCode(), response.getBody());
		}

		return response;
	}
}
