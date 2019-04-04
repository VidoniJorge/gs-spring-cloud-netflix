package com.curso.spring.eurekaclient.back;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClientBackController {

	@Autowired
	private DiscoveryClient discoveryClient;
	
	@GetMapping("/getProducto")
	@ResponseBody
	public Producto getProducto() {
		return new Producto("Marca", "Chocolate", "00192");
	}
	
	@RequestMapping("/service-instances")
	@ResponseBody
	public List<ServiceInstance> serviceIntancesByApplicationName(@RequestParam String applicationName){
		return this.discoveryClient.getInstances(applicationName);
	}
	
}
