package com.example.helloWorld;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Result;
import com.example.domain.Result.Status;

@ControllerAdvice//通过使用@ControllerAdvice定义统一的异常处理类，而不是在每个Controller中逐个定义
public class GlobalExceptionHandler {

	public static final String DEFAULT_ERROE_VIEW="my_error";
	
	//@ExceptionHandler用来定义函数针对的异常类型，最后将Exception对象和请求URL映射到error.html中
	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public Result defaultErrorHandler(HttpServletRequest request,Exception e) throws Exception {
		e.printStackTrace();
		return new Result(Status.error,"发生错误");
	}
}
