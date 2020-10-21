package java.com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.util.ApiResult;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author fyj
 */
public class LoginControllerTest extends BaseController {

    @Test
    public void ajaxLogin() throws  Exception{
        Map<String, Object> param = Maps.newHashMap();
        /*param.put("storeId", "2711");
        param.put("flag", "1");
        param.put("tab", "2");
        param.put("offset", "1");
        param.put("limit", "10");*/
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/district/getDistrictIntermediaryProductList")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(param))).andReturn();
        Assert.assertEquals("请求错误", 200, mvcResult.getResponse().getStatus());
        String responseString = mvcResult.getResponse().getContentAsString();
        ApiResult result = JSON.parseObject(responseString, ApiResult.class);
        Assert.assertEquals("返回结果不一致", ApiResult.Status.OK, result.getStatus());
        logger.info("返回的结果集为, {}", JSON.toJSONString(result));
    }
}