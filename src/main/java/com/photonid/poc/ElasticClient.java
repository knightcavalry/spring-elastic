package com.photonid.poc;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMethod;

public class ElasticClient {

	public String storeData(String directoryUrl, String jsonData) {
		ElasticHttpRequestHandler elasticHttpRequestHandler = new ElasticHttpRequestHandler();
		return elasticHttpRequestHandler.putRequest(directoryUrl, jsonData);
	}

	public String searchData(String directoryUrl, String searchQuery) {
		ElasticHttpRequestHandler elasticHttpRequestHandler = new ElasticHttpRequestHandler();
		return elasticHttpRequestHandler.postRequest(directoryUrl, searchQuery);
	}

	public String removeData(String directoryUrl) {
		ElasticHttpRequestHandler elasticHttpRequestHandler = new ElasticHttpRequestHandler();
		return elasticHttpRequestHandler.deleteRequest(directoryUrl);
	}

	/*
	 * Handle request from main class
	 */
	public String handleRequest(String requestBody, String contentType, RequestMethod requestMethod) {
		// 1: parse the request body if any
		JSONObject jsonData = new JSONObject();
		JSONObject jsonDirectory = null;
		String directoryUrl = "";
		if (requestBody != null) {
			if (contentType.equalsIgnoreCase("application/json")) {
				// handle json document convert it to JsonObject
				// following is the standard JSON request body :
				/*
				 * { "data": { "first_name": "FirstName", "last_name":
				 * "LastName", "age": 32, "about": "About", "interests": [
				 * "music" ] },
				 * 
				 * "directory": { "index": "index", "type": "type", "id": "id" }
				 * }
				 * 
				 */
				JSONObject jsonBody = new JSONObject(requestBody);
				jsonData = jsonBody.getJSONObject("data");
				jsonDirectory = jsonBody.getJSONObject("directory");

			} else if (contentType.equalsIgnoreCase("application/xml")) {
				// handle xml document, convert it to JSON
			}

			// 2: create a resource path for elastic directory
			directoryUrl = "/" + jsonDirectory.getString("index") + "/" + jsonDirectory.getString("type") + "/"
					+ jsonDirectory.getString("id");
		}

		// 3: call elasticsearch functions based on request method :
		// GET or POST = get or search query = searchData()
		// PUT = indexing = storeData()
		// DELETE = delete data = removeData()
		String responseString = null;
		switch (requestMethod) {
		case GET:
			responseString = searchData(directoryUrl, jsonData.toString());
			break;
		case POST:
			responseString = searchData(directoryUrl, jsonData.toString());
			break;
		case PUT:
			responseString = storeData(directoryUrl, jsonData.toString());
			break;
		case DELETE:
			responseString = removeData(directoryUrl);
			break;
		default:
			responseString = "";
			break;
		}

		return responseString;
	}
}
