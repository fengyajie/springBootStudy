package com.example.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.example.domain.Result;
import com.example.domain.Result.Status;

/**
 *HandlerInterceptor的功能和Filter类似，但是提供更精细的控制能力:在request响应之前，在request响应之后，
 *在request全部结束之后。我们不能通过拦截器修改request的内容，但是我们可以抛出异常来暂停request的执行
 *强调一点:只有经过DispatcherServlet的请求，才会经过拦截器链,自定义的servlet不会被拦截
 *
 */
public class RedisSessionInterceptor implements HandlerInterceptor {
 
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	
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
		HttpSession session = request.getSession();
		Object userId = session.getAttribute("userId");
		if(null != userId) {
			String loginUserId = redisTemplate.opsForValue().get("userId:"+userId);
			if(null != loginUserId && loginUserId.equals(session.getId())) {
				return true;
			}
		}
		response401(response);
		return false;
	}

	
	private void response401(HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
		try {
			response.getWriter().print(JSON.toJSONString(new Result(Status.error,"用户未登录！")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
