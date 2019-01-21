package com.example;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

public class MailTest {
	
	public static void main(String[] args) {
		sendMail1();
	}
	
	public static void sendMail1() {
		try {
		 Properties prop = new Properties();
		         prop.setProperty("mail.host", "mail.kq300061.com");
		         prop.setProperty("mail.transport.protocol", "smtp");
		          prop.setProperty("mail.smtp.auth", "true");
		          //使用JavaMail发送邮件的5个步骤
		          //1、创建session
		         Session session = Session.getInstance(prop);
		          //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		         session.setDebug(true);
		         //2、通过session得到transport对象
		         Transport ts = session.getTransport();
		         //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
		         ts.connect("mail.kq300061.com", "fengyj@kq300061.com", "fyj321&wwq524!");
		         //4、创建邮件
		          Message message = createAttachMail(session);//createSimpleMail(session);
		        //5、发送邮件
		        ts.sendMessage(message, message.getAllRecipients());
		         ts.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static MimeMessage createSimpleMail(Session session)
	            throws Exception {
	         //创建邮件对象
         MimeMessage message = new MimeMessage(session);
	         //指明邮件的发件人
	         message.setFrom(new InternetAddress("fengyj@kq300061.com"));
	         //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
	         message.setRecipient(Message.RecipientType.TO, new InternetAddress("fengyj@kq300061.com"));
	         //邮件的标题
	        message.setSubject("只包含文本的简单邮件");
	        //邮件的文本内容
	        message.setContent("你好啊！", "text/html;charset=UTF-8");
	        //返回创建好的邮件对象
	         return message;
	    }
	
	public static MimeMessage createAttachMail(Session session) throws Exception{
		         MimeMessage message = new MimeMessage(session);
		         //设置邮件的基本信息
		         //发件人
		         message.setFrom(new InternetAddress("fengyj@kq300061.com"));
		         //收件人
		         String recipient = "fengyj@kq300061.com";
		         InternetAddress[] internetAddressTo= new InternetAddress().parse(recipient);
		         message.setRecipients(Message.RecipientType.TO,internetAddressTo);
		         
		         String ccpeople = "fengyj@kq300061.com";
		         InternetAddress[] internetAddresscc= new InternetAddress().parse(ccpeople);
		         message.setRecipients(Message.RecipientType.CC, internetAddresscc);
		         //邮件标题
		         message.setSubject("JavaMail邮件发送测试");
		         
		         //创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
		        MimeBodyPart text = new MimeBodyPart();
		         text.setContent("使用JavaMail创建的带附件的邮件", "text/html;charset=UTF-8");
	        
		        //创建邮件附件
		         MimeBodyPart attach = new MimeBodyPart();
		         String finalname= "D:\\附件2.xlsx";
		         FileDataSource fileSource = new FileDataSource(finalname);
		         DataHandler dh = new DataHandler(fileSource);
		         attach.setDataHandler(dh);
		         //解决附近乱码
		         attach.setFileName(MimeUtility.encodeText(fileSource.getName()));  //
		        
		         //创建容器描述数据关系
		         MimeMultipart mp = new MimeMultipart();
		         mp.addBodyPart(text);
		         mp.addBodyPart(attach);
		         mp.setSubType("mixed");
		         
		         message.setContent(mp);
		         message.saveChanges();
		         //将创建的Email写入到E盘存储
		         message.writeTo(new FileOutputStream("E:\\attachMail.eml"));
		         //返回生成的邮件
		         return message;
		     }
	
	public static void sendMail() {
		String smtpFromMail = "fengyj@kq300061.com";  //账号    
        String pwd  = "fyj321&wwq524!"; //密码   
        String toMail = "1311594315@qq.com";//收件人
        String ccPepople = "";//抄送人
        
        String subject = "";//主题
        String content = "xxxx";//正文
        int port = 587; //端口    
        String host = "mail.kq300061.com"; //邮件服务器地址
try {
        Properties props = new Properties();    
        props.put("mail.smtp.host", host); 
        props.setProperty("mail.transport.protocol", "POP3");
        //props.put("mail.smtp.auth", "true");    
        Session session = Session.getDefaultInstance(props);    
        session.setDebug(false);    

        MimeMessage mimeMsg = new MimeMessage(session);
        mimeMsg = new MimeMessage(session);
       mimeMsg.setFrom(new InternetAddress(smtpFromMail));    
       
       //设置收件人
       @SuppressWarnings("static-access")
       InternetAddress[] internetAddressTo = new InternetAddress().parse(toMail);  
       mimeMsg.setRecipients(Message.RecipientType.TO, internetAddressTo);
       //设置抄送人
       if(null != ccPepople && !ccPepople.isEmpty()){
           @SuppressWarnings("static-access")
           InternetAddress[] internetAddressCC = new InternetAddress().parse(ccPepople);  
           mimeMsg.setRecipients(Message.RecipientType.CC, internetAddressCC);
       }
       //设置主题
       mimeMsg.setSubject(subject);    
       mimeMsg.addHeader("charset", "UTF-8");    
           
       /*添加正文内容*/    
       Multipart multipart = new MimeMultipart();    
       BodyPart contentPart = new MimeBodyPart();    
       contentPart.setContent(content, "text/html; charset=utf-8");      

       multipart.addBodyPart(contentPart);    
       
       
       /*添加附件    */
       MimeBodyPart fileBody = new MimeBodyPart();  
       /*创建临时文件目录*/
       /*File file = new File(filePath);
       if(!file.exists()){
           file.mkdirs();
       }*/
       
      /* DataSource source = new ByteArrayDataSource(is,"application/msexcel");
       fileBody.setDataHandler(new DataHandler(source));  */
       
      /* if (is != null) {
           BodyPart attachmentBodyPart = new MimeBodyPart();
           DataSource source = new FileDataSource(is);
           attachmentBodyPart.setDataHandler(new DataHandler(source));
           attachmentBodyPart.setFileName(MimeUtility.encodeText(is.getName()));
           multipart.addBodyPart(attachmentBodyPart);
       }*/
       
       
       // 中文乱码问题  
       /*fileBody.setFileName(MimeUtility.encodeText(fileName));  */  
       multipart.addBodyPart(fileBody);    
        
       mimeMsg.setContent(multipart);   
       mimeMsg.setSentDate(new Date());    
       mimeMsg.saveChanges();    
       Transport transport = session.getTransport("smtp");    
       try {
       transport.connect(host, port, smtpFromMail, pwd);
       transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
       }catch(Exception e) {
    	   e.printStackTrace();
       }finally {
    	   transport.close();
       }
       
      
}catch(Exception e) {
	
}finally {
	 
}
      




	}

}
