package com.example.helloWorld;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Customer;
import com.example.service.CustomerService;

import io.swagger.annotations.ApiOperation;

@RestController//这是一个支持REST的控制器,组合注解@Controller@ResponseBody,@RestController
                 //告诉srping以字符串的形式渲染结果，并直接返回给调用者
                 //@RestController和@RequestMapping是springMvc的注解，不是springBoot的特定部分
//@EnableAutoConfiguration//该注解告诉spring Boot 根据添加的jar猜测你想如何配置spring。由于spring-boot-starter
                        //-web添加了tomcat和springmvc，所以auto-configuration将假定你正在开发一个web应用，
                        //并对spring进行相应的配置;starters和Auto-configuration:Auto-configuration设计成
                        //可以跟starters一起很好的使用，但这两个概念没有直接的联系；
//@ComponentScan 
public class HelloWorld {
    
	
	@Autowired
	private Customer customer;
	
	@Autowired
	private CustomerService  customerService;
	
	@ApiOperation(value="helloworld")//给API增加说明
	//@ApiImplicitParams、@ApiImplicitParam注解来给参数增加说明
	@RequestMapping("/helloWorld")
	public String helloWorld() {
	/*	System.out.println("customer>>>"+customer.getCustomerName());
		System.out.println("desc>>>>"+customer.getDesc());
		System.out.println("value>>>"+customer.getValue()+";;;int>>>"+customer.getIntValue());*/
		//throw new RuntimeException("发生错误");
		String customerName="";
		List<Customer> customerList = customerService.selectList();
	    if(customerList !=null && customerList.size() >0){
	    	Customer customer = customerList.get(0);
	    	customerName = customer.getCustomerName();
	    }
		return customerName;
	}
	
	@RequestMapping("/querySpringboot")
	public String querySpringboot(){
		String customerName="";
		List<Customer> customerList = customerService.selectSpringBoot();
	    if(customerList !=null && customerList.size() >0){
	    	Customer customer = customerList.get(0);
	    	customerName = customer.getCustomerName();
	    }
		return customerName;
	}
	
	@RequestMapping("/deleteById")
	public void deleteById(){
		Long id=2L;
		try{
		customerService.deleteById(id);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/index")
	public String index(ModelMap map) {
		//加一个属性，用来在模板中读取
		map.addAttribute("name", "fengyj");
		//return 模板文件的名称,对应/springBootTest/src/main/resources/templates/index.html
		return "index";
	}
	
}
