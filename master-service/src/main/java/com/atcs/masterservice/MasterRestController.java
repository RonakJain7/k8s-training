package com.atcs.masterservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(path = "/")
public class MasterRestController {

	@Value("${slave-service-url:None}")
	private String slaveServiceUrl;
	
	@Value("${MESSAGE:No message from master}")
	private String message;

	@GetMapping(produces = { "application/json" })
	@ResponseBody
	public Object name(@RequestHeader HttpHeaders headers) {
		Map<String, Object> output = new HashMap<>();
		output.put("master-data", message);
		output.put("slave-data", getResponse(headers));
		return output;
	}

	public Object getResponse(HttpHeaders headers) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			ResponseEntity<Object> response = restTemplate.exchange(slaveServiceUrl, HttpMethod.GET, entity, Object.class);
			return response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return "Slave connection failed.";
		}
	}
}
