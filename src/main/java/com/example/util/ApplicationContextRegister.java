package com.example.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 在我们的web程序中，用spring来管理各个实例(bean), 有时在程序中为了使用已被实例化的bean, 通常会用到这样的代码：
 * ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext-common.xml");  
 *         AbcService abcService = (AbcService)appContext.getBean("abcService"); 
 *          但是这样就会存在一个问题：因为它会重新装载applicationContext-common.xml并实例化上下文bean，如果有些线程配置类也是在这个配置文件中，
 *          那么会造成做相同工作的的线程会被启两次。一次是web容器初始化时启动，另一次是上述代码显示的实例化了一次。当于重新初始化一遍！！！！这样就产生了冗余。
 *不用类似new ClassPathXmlApplicationContext()的方式，从已有的spring上下文取得已实例化的bean。通过ApplicationContextAware接口进行实现。
 *当一个类实现了这个接口（ApplicationContextAware）之后，这个类就可以方便获得ApplicationContext中的所有bean。换句话说，就是这个类可以直接获取spring配置文件中，所有有引用到的bean对象。
 *
 * @author fyj
 *
 */
@Component
public class ApplicationContextRegister implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;
	/**
	 * 加载Spring配置文件时，如果Spring配置文件中所定义的Bean类实现了ApplicationContextAware 接口，那么在加载Spring配置文件时，
	 * 会自动调用ApplicationContextAware 接口中的此方法
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		this.applicationContext=applicationContext;
	}
	
	//获取applicationContext
    public static ApplicationContext getApplicationContext() {
       return applicationContext;
    }
    //通过name获取 Bean.
    public static Object getBean(String name){
       return getApplicationContext().getBean(name);
    }
    
  //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
       return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
       return getApplicationContext().getBean(name, clazz);
    }

}
