package springBootTest;

import org.springframework.beans.factory.annotation.Value;

public class Test {

	@Value("${spring.redis.host}")
	private static  String host;
	
	public static void main(String[] args) {
		

		System.out.println("host:::"+host);
	}

}
