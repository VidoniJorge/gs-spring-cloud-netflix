package com.curso.spring.eurekaclient.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;

@Controller
public class ClientController {

	@HystrixCommand(fallbackMethod  = "defaultMgs",
			commandProperties =
				{
					@HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="5"),
					@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="5000"),
					@HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="10")
				})
	
	@GetMapping("/getProducto")
	@ResponseBody
	public String getProducto() {
		return new RestTemplate().getForObject("http://eze1-lhp-b01902.synapse.com:8090/getProducto",String.class);
	}
	
	private String defaultMgs() {
		return "mensaje standar";
	}
}
