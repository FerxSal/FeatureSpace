package com.feature.test;


import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.featurespace.PostCodesRepo;

import org.junit.Ignore;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;


public class getPostCodeTest {

	private static String uri;
	static ObjectMapper mapper;
	static Client client;
	List<LinkedHashMap<String,String>> list;
	Map<String, Object> map;
	
	
	
	 @BeforeClass
	  public static void runOnceBeforeClass() {
			uri="http://api.postcodes.io";
			mapper = new ObjectMapper();	
			ClientBuilder.newClient();
	    }

	
	  @Test
	   public void testGetCodePost() throws JsonParseException, JsonMappingException, MalformedURLException, IOException {	
	      System.out.println("Inside testPrintMessage()"); 
	      assertEquals(uri+"/postcodes?q="+StringUtils.deleteWhitespace("CB3 0FA"),"http://api.postcodes.io/postcodes?q=CB30FA");
	      
	      map = mapper.readValue(new URL(uri+"/postcodes?q="+StringUtils.deleteWhitespace("CB3 0FA")), new TypeReference<Map<String,Object>>(){});
		  list = (List<LinkedHashMap<String,String>>)(map.get("result"));
		  assertTrue((!map.isEmpty()));
		  assertTrue(!list.isEmpty());
		  assertEquals(list.size(),1);
		  
		  assertEquals(list.get(0).get("country"),"England");
		  assertEquals(list.get(0).get("region"),"East of England");
		  
	      map = mapper.readValue(new URL(uri+"/postcodes?q="+StringUtils.deleteWhitespace("C?3 0F9")), new TypeReference<Map<String,Object>>(){});
		  list = (List<LinkedHashMap<String,String>>)(map.get("result"));
		  
		  System.out.println(map);
		  
		  assertTrue(!(map.isEmpty()));
		  assertNull(list);
		     
	   }
	
	  @Test
	   public void testGetNearestPostCodes() throws JsonParseException, JsonMappingException, MalformedURLException, IOException{
		  
		  
		  assertEquals(uri+"/postcodes/"+StringUtils.deleteWhitespace("CB3 0FA")+"/nearest","http://api.postcodes.io/postcodes/CB30FA/nearest");
		  map = mapper.readValue(new URL(uri+"/postcodes/"+StringUtils.deleteWhitespace("CB3 0FA")+"/nearest"), new TypeReference<Map<String,Object>>(){});
		  list = (List<LinkedHashMap<String,String>>)(map.get("result"));
		  
		  assertEquals(list.get(0).get("postcode"),"CB3 0FA");
		  assertEquals(list.get(1).get("postcode"),"CB3 0GT");
		  assertEquals(list.get(2).get("postcode"),"CB3 0FT");
		  
	  }
	  
	  @Test
	  public void getValidationPostCodes() throws JsonParseException, JsonMappingException, MalformedURLException, IOException{
		  
		  //getUri()+"/postcodes/"+postCode.trim()+"/validate";
		  assertEquals(uri+"/postcodes/"+StringUtils.deleteWhitespace("CB3 0FA")+"/validate","http://api.postcodes.io/postcodes/CB30FA/validate"); 
		  map = mapper.readValue(new URL(uri+"/postcodes/"+StringUtils.deleteWhitespace("CB3 0FA")+"/validate"), new TypeReference<Map<String,Object>>(){});
		  
		  assertEquals(map.get("result").toString(),"true");
		  assertEquals(map.get("status").toString(),"200");
		  
	  }
	  
	
}
