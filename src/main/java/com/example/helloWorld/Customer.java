package com.example.helloWorld;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 *Spring Boot将 Environment 属性绑定到 @ConfigurationProperties beans时
*会使用一些宽松的规则，所以 Environment 属性名和bean属性名不需要精确匹
*配。常见的示例中有用的包括虚线分割（比如， context-path 绑定
*到 contextPath ） ，将environment属性转为大写字母（比如， PORT 绑
*定 port ） 
 */
@Component
//@ConfigurationProperties(prefix="person") 可以匹配:person.firstName,person.first-name,person.first_name,PERSON_FIRST_NAME
public class Customer {
	
	//通过application.properties配置注入
	@Value("${customerName}")
	private String customerName;
	
	@Value("${usePhone}")
	private String usePhone;
	
	@Value("${desc}")
	private String desc;
	
	@Value("${value}")
	private String value;
	
	@Value("${intValue}")
	private int intValue;
	
	
	//Spring Boot将尝试校验外部配置，默认使用JSR-303（如果在classpath路径中） ，
	//你只需要将JSR-303 javax.validation 约束注解添加
	//到 @ConfigurationProperties 类上
	//@NotNull//用于对象
	@NotEmpty
	@Valid
	private String firstName;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getUsePhone() {
		return usePhone;
	}

	public void setUsePhone(String usePhone) {
		this.usePhone = usePhone;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
}
