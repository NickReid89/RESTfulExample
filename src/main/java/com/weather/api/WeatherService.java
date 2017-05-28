package com.weather.api;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
 
@Path("/getWeather")
public class WeatherService   {
	


	@SuppressWarnings("unchecked")
	@GET
	@Path("/query")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.TEXT_HTML})
	//temp url
	//http://localhost:8080/WeatherAPI/api/getWeather/query?Country=CA&City=California
	public Response getUsers(
			@DefaultValue("Toronto") @QueryParam("City") String city,
			@DefaultValue("CA") @QueryParam("Country") String country) throws Exception {
		System.out.println("Testing 1 - Send Http GET request" + city + " " + country);
		
		JSONObject json;
		
		

		json = new JSONObject(this.sendGet(city,country).trim()); // Convert text to object
	
		
		String weather = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").get("text").toString();
			String time = json.getJSONObject("query").getString("created").toString();

		
		
		return Response
			   .status(200)
			   .entity("<html><body><h1>" + weather + "</h1><h2>" + time+"</h2></body></html>").build();
	
		
	}
	// HTTP GET request
	private String sendGet(String city, String country)  {
		try{
		String firsturl = "https://query.yahooapis.com/v1/public/yql?q=select%20item.condition.text%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22";
		String lasturl = "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
		URL obj = new URL(firsturl + city + "%2C%20" + country + lasturl) ;
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");



		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + firsturl + city + "%20" + country + lasturl);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		String temp = response.toString();
		System.out.println(temp);
		//print result
		return temp;
		}catch(IOException me){
		return "exception called.";
		} 
	}
	


	}
	
	
	
	

 
