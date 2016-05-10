package com.photonid.poc;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.photonid.poc.ElasticHttpRequestHandler.Method;


@RestController
public class MainController {
		
	@RequestMapping(value="/elastic", method=RequestMethod.GET)
	public String handleGetReq(@RequestParam Map<String,String> allRequestParams) {
		String contentType = allRequestParams.getOrDefault("Content-Type", "application/json");
		String response = handleRequest(null, contentType, RequestMethod.GET);
		return response;
	}
	
	@RequestMapping(value="/elastic", method=RequestMethod.POST)
	public String handlePostReq(@RequestParam Map<String,String> allRequestParams, 
			@RequestBody String requestBody) {
		String contentType = allRequestParams.getOrDefault("Content-Type", "application/json");
		String response = handleRequest(requestBody, contentType, RequestMethod.POST);
		return response;
	}
	
	@RequestMapping(value="/elastic", method=RequestMethod.PUT)
	public String handlePutReq(@RequestParam Map<String,String> allRequestParams, 
			@RequestBody String requestBody) {
		String contentType = allRequestParams.getOrDefault("Content-Type", "application/json");
		String response = handleRequest(requestBody, contentType, RequestMethod.PUT);
		return response;
	}
	
	@RequestMapping(value="/elastic", method=RequestMethod.DELETE)
	public String handleDeleteReq(@RequestParam Map<String,String> allRequestParams, 
			@RequestBody String requestBody) {
		String contentType = allRequestParams.getOrDefault("Content-Type", "application/json");
		String response = handleRequest(requestBody, contentType, RequestMethod.DELETE);
		return response;
	}
	
	public String handleRequest(String requestBody, String contentType, 
			RequestMethod requestMethod){
		ElasticClient elasticClient = new ElasticClient();
		String response = elasticClient.handleRequest(requestBody, 
				contentType, requestMethod);
		return response;
	}
}
