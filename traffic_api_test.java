package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.IOException;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class traffic_api_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			//일단 쓸 수는 있을듯!
			StringBuilder urlBuilder = new StringBuilder("http://data.ex.co.kr/openapi/trafficapi/trafficIc"); /*URL*/
			urlBuilder.append("?key=8171987461&type=json&tmType=2&numOfRows=99");
			/*
			urlBuilder.append("?" + URLEncoder.encode("key","UTF-8") + "= 8171987461"); 
			urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("tmType","UTF-8") + "=" + URLEncoder.encode("2", "UTF-8")); 
			urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("99", "UTF-8"));
			*/
			
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
			conn.setRequestMethod("GET");
			//conn.setRequestProperty("type", "JSON");
        
			System.out.println("Response code: " + conn.getResponseCode());
        
			BufferedReader rd;
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"UTF-8"));
			}
        
			StringBuilder sb = new StringBuilder();
			String line;
        
			while ((line = rd.readLine()) != null) {
				sb.append(line + "\n" );
			}
        
			rd.close();
        
			conn.disconnect();
			System.out.println(sb);
			System.out.println();
        
		
		
       //
			String temp = sb.toString();
       
		
    	   JSONParser jparser = new JSONParser();
    	   JSONObject jobject = (JSONObject) jparser.parse(temp);
    	   JSONArray jarray = (JSONArray) jobject.get("trafficIc");
    	   
    	   System.out.println(jarray);
       
    	   String name = "", new_name;
    	   int traffic=0, inout = -1, new_inout = -1;
    	   
    	   for(int i =0; i<jarray.size(); i++)
    	   {
    		   JSONObject obj = (JSONObject)jarray.get(i);
    		   
    		   new_name = (String)obj.get("unitName");
    		   new_inout = Integer.parseInt((String)obj.get("inoutType"));
 
    		   
    		   if((name.equals(new_name)) && (inout == new_inout))
    		   {
    			   traffic += Integer.parseInt((String)obj.get("trafficAmout"));
    			   
    		   }else
    		   {
    			   //이전 정보 모아진거 출력
    			   //System.out.println("짠");
    			   System.out.println("unitName: " + name);
    			   System.out.println("inoutType: " + inout);
    			   System.out.println("trafficAMount: " + traffic);
    			   System.out.println();
    			   
    			   name = new_name;
    			   inout = new_inout;
    			   traffic = Integer.parseInt((String)obj.get("trafficAmout"));
    		   }
    		   
    		   
    		   
    		   
        	
    	   }
		}catch (ParseException e)
		{
			e.printStackTrace();
		}catch (IOException r)
		{
			r.printStackTrace();
		}
	}

}
