package com.example.interceptor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.domain.Result;

/**
 * 在SpringBoot2.0及Spring 5.0 WebMvcConfigurerAdapter已被废弃
 * @author fyj
 *
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer{

	
	//配置跨域
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/cors/**").allowedHeaders("*").allowedMethods("POST","GET").allowedOrigins("*");
	}

	/**
	 * 消息内容转换配置
	 * 配置fastjson返回json转换
	 * FastJson SerializerFeatures
*WriteNullListAsEmpty ：List字段如果为null,输出为[],而非null
*WriteNullStringAsEmpty ： 字符类型字段如果为null,输出为"",而非null
*DisableCircularReferenceDetect ：消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
*WriteNullBooleanAsFalse：Boolean字段如果为null,输出为false,而非null
*WriteMapNullValue：是否输出值为null的字段,默认为false。
*WriteNullNumberAsZero: 数值字段如果为null，则输出为0
*重写writeInternal方法，可以重新构造返回对象，达到统一的返回值
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//创建fastjson消息转换器
		FastJsonHttpMessageConverter fastconver = new FastJsonHttpMessageConverter() {

			@Override
			protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
					throws IOException, HttpMessageNotWritableException {
				if(obj instanceof Result) {
					Map<String,Object> resultMap = new HashMap<String,Object>();
					Result result = (Result) obj;
					resultMap.put("status", result.getStatus());
					resultMap.put("message", result.getMessage());
					resultMap.put("rows", result.getRows());
					super.writeInternal(resultMap, outputMessage);
				}else {
				   super.writeInternal(obj, outputMessage);
				}
			}
		};
		
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastconver.setSupportedMediaTypes(supportedMediaTypes);
		//创建配置类
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setCharset(Charset.forName("UTF-8"));
		fastJsonConfig.setDateFormat("YYYY-MM-dd");
		
		//修改配置返回内容的过滤
		fastJsonConfig.setSerializerFeatures(
				SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.PrettyFormat);
		fastconver.setFastJsonConfig(fastJsonConfig);
		//将fastJson添加到视图转换器消息列表内
		converters.add(fastconver);
	}

	//此方法用来专门注册一个Handler，来处理静态资源的，例如：图片，js，css等
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//可以通过http://127.0.0.1:8080/templates/hello.html访问resources/templates/hello.html页面
		registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
	}

	//此方法可以很方便的实现一个请求到视图的映射，而无需书写controller
	//这时访问http://127.0.0.1:8080/login时，会直接返回login.html页面
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("templates/login.html");
	}

	
	
}
