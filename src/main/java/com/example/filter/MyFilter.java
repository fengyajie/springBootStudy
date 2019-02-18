package com.example.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * 使用注解标注过滤器
 * @WebFilter将一个实现了javax.servlet.Filter的类定义为过滤器
 * 属性filterName声明过滤器的名称，可选
 * 属性urlPatterns制定要过滤的url模式，也可使用value来声明,必选属性
 */
@WebFilter(filterName="myFilter",urlPatterns="/*")
public class MyFilter implements Filter {

	@Override
	public void destroy() {

		System.out.println("销毁过滤器");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		System.out.println("过滤器执行操作");
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
           System.out.println("过滤器初始化");
	}

}
