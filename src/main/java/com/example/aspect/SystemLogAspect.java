package com.example.aspect;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.configuration.annotation.SystemControllerLog;
import com.example.configuration.annotation.SystemLogModel;
import com.example.dao.customerShiro.SystemLogDao;

/**
 * 切面类
 * @author Administrator
 *
 */
@Aspect
@Component
public class SystemLogAspect {
	// 本地异常日志记录对象
	   private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

	   @Autowired
	   private SystemLogDao systemLogDao;
	   
	// Controller层切点,针对在业务模块标注SystemControllerLog注解记录日志
	   //@annotation 表示标注了指定注解的目标类方法
	   @Pointcut("@annotation(com.example.configuration.annotation.SystemControllerLog)")
	   public void controllerAspect(){
		   
	   }
	   
	   /**
	    * 前置通知，拦截用户操作
	    * @param joinPoint
	    */
	   @Before("controllerAspect()")
	   public void doBefore(JoinPoint joinPoint){
		   HttpServletRequest  request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		   try{
		   //请求ip
		   String logIP = request.getRemoteAddr();
		   
		   String userID = request.getParameter("UserId");
		   String userName = request.getParameter("UserName");
		   SystemLogModel slm = getControllerMethodDescription(joinPoint); 
		   slm.setLogIP(logIP); 
		   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss"); 
		   String date = dateFormat.format(new Date()); 
		   slm.setTimeFlag(date); 
		   slm.setFlagID(userID); 
		   slm.setFlagName(userName); 
		   // *========控制台输出=========*//
	         logger.debug("=====注解参数获取开始====="); 
	         logger.debug("请求方法:"
	               + (joinPoint.getTarget().getClass().getName() + "."
	               + joinPoint.getSignature().getName() + "()")); 
	         logger.debug("操作模块:" + slm.getModuleID()); 
	         logger.debug("操作方法:" + slm.getLogAction()); 
	         logger.debug("操作内容:" + slm.getLogContent()); 
	         logger.debug("请求IP:" + slm.getLogIP()); 
	         logger.debug("FlagID:" + slm.getFlagID()); 
	         logger.debug("FlagName:" + slm.getFlagName()); 
	         // *========数据库日志=========*//
	         int res = systemLogDao.saveOrUpdate(slm); 
	         if (res > 0) { logger.info(">>>>>>>>保存日志成功"); }

		   }catch(Exception e){
			   logger.error("前置通知异常,保存日志异常信息:{}",e.getMessage());
		   }
		   
	   }
	   
	   /**
	    * 获取注解中对方法的描述信息 用于Controller层注解
	    * 
	    * JoinPoint 官方注释百度翻译
	    * 提供对连接点处可用状态和关于它的静态信息的反射访问。
	    * 此信息可从使用thisJoinPoint的特殊表单的建议主体获得。这个反射信息的主要用途是跟踪和记录应用程序。
	    * @return
	    */
	   public static SystemLogModel  getControllerMethodDescription(JoinPoint joinPoint) throws Exception{
		   //获取被代理的对象
		   String targetName = joinPoint.getTarget().getClass().getName();
		   
		   //获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息
		   String methodName = joinPoint.getSignature().getName();
		   //获取传入目标方法的参数对象
		   Object[] arguments = joinPoint.getArgs();
		   Class targetClass = Class.forName(targetName);
		   Method[] methods = targetClass.getMethods();
		   String description="";
		   SystemControllerLog log;
		   SystemLogModel logM = new SystemLogModel();
		   
		   for(Method method:methods){
			   if(method.getName().equals(methodName)){
				   Class[] clazzs = method.getParameterTypes();
				   if(clazzs.length == arguments.length){
					   log = method.getAnnotation(SystemControllerLog.class);
					   logM.setModuleID(log.ModuleID());
					   logM.setLogAction(log.LogAction());
					   logM.setLogContent(log.LogContent());
					   break;
				   }
			   }
		   }
		   return logM;
	   }

}
