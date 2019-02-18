package com.example.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *HandlerInterceptor的功能和Filter类似，但是提供更精细的控制能力:在request响应之前，在request响应之后，
 *在request全部结束之后。我们不能通过拦截器修改request的内容，但是我们可以抛出异常来暂停request的执行
 *强调一点:只有经过DispatcherServlet的请求，才会经过拦截器链,自定义的servlet不会被拦截
 *
 */
public class MyInterceptor1 implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("在整个请求结束之后调用，也就是在dispatchServle渲染了对应的视图之后，主要用于资源清理工作");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println(">>>>>在请求处理之后但在试图渲染之前调用(controller方法调用之后)");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println(">>>再请求处理之前调用(controller方法调用之前)>>>>");
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}
