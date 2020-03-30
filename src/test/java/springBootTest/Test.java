package springBootTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class Test {

	@Value("${spring.redis.host}")
	private static  String host;
	
	public static void main(String[] args) {


		//转化成
		List list = new ArrayList();
		list.add("xxx");
		list.add("vvv");

		String str ="{\"sign\": \"99EC5020DCFF7A68540FD3452B216AC5\", \"orderId\": 2074, \"userCode\": \"SXTUVFUJJDZRQ\", \"refundReason\": \"这猪太瘦\", \"imgUrl\": [\"http://zhkjmalltest.oss-cn-shanghai.aliyuncs.com/mall-20191211-6dd22cfe7a5d43778ad3fb8b88446c82.jpg\"]}";

		JSONObject obj = JSON.parseObject(str);
		//System.out.println(JSON.toJSONString(list));

		 System.out.println(obj.get("imgUrl").toString());
		 String sttt = "[\"http://zhkjmalltest.oss-cn-shanghai.aliyuncs.com/mall-20191211-6dd22cfe7a5d43778ad3fb8b88446c82.jpg\"]";
		JSONArray jsonArr = JSONArray.parseArray(sttt);
		//JSONArray jsonArr1 = JSONArray.parseArray((String) obj.get("imgUrl"));
		System.out.println(jsonArr);
	}

}
