package cn.javabook.chapter15.interceptor;

import cn.javabook.chapter15.annotation.Idempotent;
import cn.javabook.chapter15.service.IdempotentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 接口幂等性拦截器
 * 
 */
public class IdempotentInterceptor implements HandlerInterceptor {
	@Autowired
	private IdempotentService idempotentService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		Idempotent idempotent = method.getAnnotation(Idempotent.class);
		if (idempotent != null) {
			// 幂等性校验，校验通过则放行，校验失败则抛出异常，并通过统一异常处理返回友好提示
			return idempotentService.checkToken(request);
		}
		// 必须返回true，否则会被拦截一切请求
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
	}
}
