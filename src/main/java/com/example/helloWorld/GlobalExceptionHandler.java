package com.example.helloWorld;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	public static final String DEFAULT_ERROE_VIEW="error";
	
	@ExceptionHandler(value=Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request,Exception e) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.addObject("url", request.getRequestURL());
		mav.setViewName(DEFAULT_ERROE_VIEW);
		return mav;
	}
}
