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

public class tag_gettoptrack {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		try
		{
			StringBuilder urlBuilder = new StringBuilder("https://ws.audioscrobbler.com/2.0/"); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("method","UTF-8") + "=" + URLEncoder.encode("tag.gettoptracks", "UTF-8")); 
			urlBuilder.append("&" + URLEncoder.encode("tag","UTF-8") + "=" + URLEncoder.encode("peaceful", "UTF-8")); 
			urlBuilder.append("&" + URLEncoder.encode("api_key","UTF-8") + "=6a08a55c41eb47e08a8eaf0905079779"); 
			urlBuilder.append("&" + URLEncoder.encode("format","UTF-8") + "=" + URLEncoder.encode("json","UTF-8"));
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
    	   jobject = (JSONObject) jobject.get("tracks");
    	   JSONArray jarray = (JSONArray) jobject.get("track");
       
    	   for(int i =0; i<jarray.size(); i++)
    	   {
    		   JSONObject obj = (JSONObject)jarray.get(i);
    		   String track_name = (String)obj.get("name");
    		   
    		   //int count = Integer.parseInt((String)obj.get("duration"));
    		   
    		   JSONObject obj2 = (JSONObject)obj.get("artist");
    		   String artist_name = (String)obj2.get("name");
    		   
    		   
    		   
    		   //System.out.println("count("+i+"):" + duration);
    		   System.out.println("track("+i+"):" + track_name);
    		   System.out.println("artist("+i+"):"+ artist_name);
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
