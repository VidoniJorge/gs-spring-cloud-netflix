package com.curso.spring.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;

@Controller
@RibbonClient(name = "ribbon-a-test")
public class RibbonController {
	
	@Autowired
	RestTemplate  restTemplate;
	
	@GetMapping("/test")
	@ResponseBody
	public String test() {
		return this.restTemplate.getForObject("http://ribbon-test/getProducto", String.class);
	}
	
	@LoadBalanced
	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
