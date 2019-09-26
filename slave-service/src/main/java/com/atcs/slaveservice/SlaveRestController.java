package com.atcs.slaveservice;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(path = "/")
public class SlaveRestController {

	@Autowired
	Environment environment;
	
	@Value("${MESSAGE:No message from slave}")
	private String message;

	@GetMapping(produces = { "application/json" })
	@ResponseBody
	public Object name() {
		Map<String, String> output = new HashMap<>();
		output.put("message", message);
		try {
			output.put("ip_address", InetAddress.getLocalHost().getHostAddress().toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
}
