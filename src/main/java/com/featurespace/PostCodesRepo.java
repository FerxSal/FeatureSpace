package com.featurespace;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class PostCodesRepo {

	private final static Logger LOGGER = Logger.getLogger(PostCodesRepo.class.getName()); 
	private static String uri="http://api.postcodes.io";
	static ObjectMapper mapper = new ObjectMapper();	
	

	//Query for postcode
	public static String getUri() {
		return uri;
	}


	public static void setUri(String uri) {
		PostCodesRepo.uri = uri;
	}

     //"postcode": "CB3 0FA",
	private static boolean validatePostCode(String postCode){
		
		String regex = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$"; 
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(postCode);
		
		return matcher.matches();		
	}
	

	public static void getPostCode(String postCode){
		
		
		String request = getUri()+"/postcodes?q="+StringUtils.deleteWhitespace(postCode.trim());
		//http://api.postcodes.io/postcodes?q=CB3%200FA
	 	
	 try{	
	
		Map<String, Object> map = mapper.readValue(new URL(request), new TypeReference<Map<String,Object>>(){});
		List<LinkedHashMap<String,String>> list = (List<LinkedHashMap<String,String>>)(map.get("result")); 
        
		if (list !=null && !list.isEmpty() && list.size() == 1){
		  out.println(list.get(0).get("country"));
	 	  out.println(list.get(0).get("region"));
		}
		
	 }
		
	 catch (Exception e) {
         e.printStackTrace();
         LOGGER.info("Problem parsing Json "+ (PostCodesRepo.class).getName()); 
     }	
		
	}
	
	///postcodes/{POSTCODE}/nearest
	//api.postcodes.io/postcodes/:postcode/nearest
	public static void getNearestPostCodes(String postCode){
	
		String request = getUri()+"/postcodes/"+postCode.trim()+"/nearest";
		
		try{
		  
			Map<String, Object> map = mapper.readValue(new URL(request), new TypeReference<Map<String,Object>>(){});
		    List<LinkedHashMap<String,String>> list = (List<LinkedHashMap<String,String>>)(map.get("result")); 
		  
		    if (list !=null && !list.isEmpty() && list.size() > 1){
		    
		       for (LinkedHashMap<String,String> postcode :list)
		    	   out.println( postcode.get("postcode") +"-"+ postcode.get("country")+ "-"+ postcode.get("region"));
	    		
		    }

		}
		
		 catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	         LOGGER.info("Problem parsing Json "+ (PostCodesRepo.class).getName()); 
	     }
	}
	
	//GET /postcodes/{POSTCODE}/validate
	public static void getValidationPostCodes(String postCode){
	
		String request = getUri()+"/postcodes/"+postCode.trim()+"/validate";
	   try{  
		
		    Map<String, Object> map = mapper.readValue(new URL(request), new TypeReference<Map<String,Object>>(){});
		    if (map.get("result").toString().equals("true")  && map.get("status").toString().equals("200")){
		       	
		        out.println("Valid PostCode !!");	
		        out.println(map.get("result").toString() +"-"+ map.get("status").toString());
		    }
		
	   }
	   catch (Exception e) {
	         e.printStackTrace();
	         LOGGER.info("Problem parsing Json "+ (PostCodesRepo.class).getName()); 
	     }
		
	}
	
	
	public static void main(String[] args){
		
      if (validatePostCode("CB3 0FA")){    
		
		  PostCodesRepo.getPostCode("CB3 0FA"); 
		  PostCodesRepo.getNearestPostCodes("CB3 0FA");
		  PostCodesRepo.getValidationPostCodes("CB3 0FA");
		  System.out.println(PostCodesRepo.validatePostCode("CB3 0FA"));
		
      }
      else 
      System.out.println("Invalid Postal Code !!");
	}

	
}
