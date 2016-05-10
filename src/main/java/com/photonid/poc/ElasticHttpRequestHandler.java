package com.photonid.poc;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ElasticHttpRequestHandler {
	
	public final String baseURL = "http://search-springelasticpoc-w3wgijb6pcfs2lv45ex4uw7kmm.us-west-2.es.amazonaws.com";
//	public final String baseURL = "http://localhost:9200";
	public enum Method {GET, POST, PUT, DELETE};

	public String postRequest(String url, String data) {
		return callService(url, data, Method.POST);
	}

	public String putRequest(String url, String data) {
		return callService(url, data, Method.PUT);
	}

	public String deleteRequest(String url) {
		return callService(url, null, Method.DELETE);
	}

	public String callService(String url,String data, Method method) {
		try {

			Client client = Client.create();
			WebResource webResource = client.resource(baseURL + url);
			String input = data;
			ClientResponse response = null;
			switch (method) {
			case GET:
				response = webResource.type("application/json").get(ClientResponse.class);
			case POST:
				response = webResource.type("application/json").post(ClientResponse.class, input);
				break;
			case PUT:
				response = webResource.type("application/json").put(ClientResponse.class, input);
				break;
			case DELETE:
				response = webResource.type("application/json").delete(ClientResponse.class);
				break;
			default:
				response = webResource.type("application/json").post(ClientResponse.class, input);
				break;
			}
			
//			if (response.getStatus() != 201 || response.getStatus() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
//			}

			System.out.println("Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
			return output;
		} catch (Exception e) {
			e.printStackTrace();
			return "INTERNAL SERVER ERROR";
		}
	}
}
