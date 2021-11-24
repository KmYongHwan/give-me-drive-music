import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
//import java.io.BufferedReader;
import java.io.IOException;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class testcode2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try
		{
			StringBuilder urlBuilder = new StringBuilder("https://ws.audioscrobbler.com/2.0/"); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("method","UTF-8") + "=" + URLEncoder.encode("track.gettoptags", "UTF-8")); 
			urlBuilder.append("&" + URLEncoder.encode("artist","UTF-8") + "=" + URLEncoder.encode("BTS", "UTF-8")); 
			urlBuilder.append("&" + URLEncoder.encode("track","UTF-8") + "=" + URLEncoder.encode("Butter", "UTF-8")); 
			urlBuilder.append("&" + URLEncoder.encode("api_key","UTF-8") + "=6a08a55c41eb47e08a8eaf0905079779"); 
			urlBuilder.append("&" + URLEncoder.encode("format","UTF-8") + "=" + URLEncoder.encode("json","UTF-8"));
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
			conn.setRequestMethod("GET");
			conn.setRequestProperty("format", "JSON");
        
			System.out.println("Response code: " + conn.getResponseCode());
        
			BufferedReader rd;
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
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
    	   JSONArray jarray = (JSONArray) jobject.get("tag");
       
    	   for(int i =0; i<jarray.size(); i++)
    	   {
    		   JSONObject obj = (JSONObject)jarray.get(i);
    		   int count = (int)obj.get("title");
    		   String name = (String)obj.get("neme");
    		   String url2 = (String)obj.get("url");
    		   System.out.println("count("+i+"):" + count);
    		   System.out.println("name("+i+"):" + name);
    		   System.out.println("url("+i+"):"+ url2);
    		   System.out.println();
        	
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
