
/**
 * 当类没有声明 package 时，它被认为处于 default package 下。通常不推荐使
*用 default package ，因为对于使
*用 @ComponentScan ， @EntityScan 或 @SpringBootApplication 注解的
*Spring Boot应用来说，它会扫描每个jar中的类，这会造成一定的问题。
*注 我们建议你遵循Java推荐的包命名规范，使用一个反转的域名（例
*如 com.example.project ） 。
 * @author
 *
 */
public class DefaultPackageClass {

}
