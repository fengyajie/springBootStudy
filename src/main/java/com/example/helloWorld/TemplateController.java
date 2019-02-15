package com.example.helloWorld;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TemplateController {

	@RequestMapping("/helloHtmlTem")
	public String template(Map<String,Object> map) {
		System.out.println("........");
		return "hello";
	}
}
