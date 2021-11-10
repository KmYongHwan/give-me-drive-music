package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class traffic_test_code2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			StringBuilder urlBuilder = new StringBuilder("https://openapi.its.go.kr:9443/trafficInfo"); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("apiKey","UTF-8") + "=2fe7b9e181564900b45102bfb47d68ab"); 
			urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("all", "UTF-8")); 
			urlBuilder.append("&" + URLEncoder.encode("minX","UTF-8") + "=" + URLEncoder.encode("126.800000", "UTF-8")); 
			urlBuilder.append("&" + URLEncoder.encode("maxX","UTF-8") + "=" + URLEncoder.encode("126.900000", "UTF-8")); 
			urlBuilder.append("&" + URLEncoder.encode("minY","UTF-8") + "=" + URLEncoder.encode("34.900000", "UTF-8")); 
			urlBuilder.append("&" + URLEncoder.encode("maxY","UTF-8") + "=" + URLEncoder.encode("34.900000","UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("getType","UTF-8") + "=" + URLEncoder.encode("json","UTF-8"));
			
			
			//https://openapi.its.go.kr:9443/trafficInfo?apiKey=2fe7b9e181564900b45102bfb47d68ab&type=all&minX=126.800000&maxX=127.890000&minY=34.900000&maxY=35.100000&getType=json
			
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
			conn.setRequestMethod("GET");
			conn.setRequestProperty("format", "JSON");
        
			System.out.println("Response code: " + conn.getResponseCode());
        
			BufferedReader rd;
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
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
    	   
    	   System.out.println(jobject);
    	   jobject = (JSONObject) jobject.get("body");
    	   JSONArray jarray = (JSONArray) jobject.get("items");
       
    	   for(int i =0; i<jarray.size(); i++)
    	   {
    		   JSONObject obj = (JSONObject)jarray.get(i);
    		   double speed = Double.parseDouble((String)obj.get("speed"));
    		   String roadname = (String)obj.get("roadName");
    		   
    		   System.out.println("road name("+i+"): " + roadname);
    		   System.out.println("speed("+i+"):" + speed);
    		   System.out.println();
        	
    	   }
    	   
    	   
    	   System.out.println(jobject);
		}catch (ParseException e)
		{
			e.printStackTrace();
		}catch (IOException r)
		{
			r.printStackTrace();
		}
	}

}
