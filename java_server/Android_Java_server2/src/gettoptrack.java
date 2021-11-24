import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class gettoptrack {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			StringBuilder urlBuilder = new StringBuilder("https://ws.audioscrobbler.com/2.0/"); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("method","UTF-8") + "=" + URLEncoder.encode("chart.gettoptracks", "UTF-8"));  
			urlBuilder.append("&" + URLEncoder.encode("api_key","UTF-8") + "=6a08a55c41eb47e08a8eaf0905079779"); 
			urlBuilder.append("&" + URLEncoder.encode("format","UTF-8") + "=" + URLEncoder.encode("json","UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("limit","UTF-8") + "=" + URLEncoder.encode("1000","UTF-8"));
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
		//	System.out.println(sb);
	//		System.out.println();
        
		
			File file1 = new File("C:\\Users\\±èÀÎ¼­\\OneDrive\\¹ÙÅÁ È­¸é\\give-me-drive-music-file\\[gettoptracks].txt");
			if(file1.exists()==false) file1.createNewFile();
			
			FileWriter file_writer = new FileWriter(file1); 
			BufferedWriter writer = new BufferedWriter(file_writer);
		
       //
     		String temp = sb.toString();
       
		
    	   JSONParser jparser = new JSONParser();
    	   JSONObject jobject = (JSONObject) jparser.parse(temp);
    	   
    	 //  System.out.println("jobject: " + jobject);
    	   
    	   jobject = (JSONObject)jobject.get("tracks");
    	   
    	//   System.out.println("jobject: " + jobject);
    	   
    	   JSONArray jarray = (JSONArray) jobject.get("track");
    	  // JSONObject jobject2 = (JSONObject) jobject.get("@attr");
    	   
    	 //  System.out.println("jobject2(@attr): " + jobject2);
    	   System.out.println("jarray(track): "+ jarray);
    	   System.out.println();
    	   for(int i =0; i<jarray.size(); i++)
    	   {
    		   JSONObject obj = (JSONObject)jarray.get(i);
    		   //JSONObject jcount = (JSONObject)obj.get("count");
    		   //JSONObject jname = (JSONObject)obj.get("neme");
    		   //JSONObject jurl = (JSONObject)obj.get("url");
    		   
    		 //  long count = (long)obj.get("count");
    		   String track_name = (String)obj.get("name");
    		   
    		   JSONObject artist = (JSONObject)obj.get("artist");
    		   String artist_name = (String)artist.get("name");
    		   
    		   //System.out.println("object("+i+"):" + obj);
    		   System.out.println("track_name("+i+"):" + track_name);
    		   System.out.println("artist_name("+i+"):" + artist_name);
    		   //System.out.println("url("+i+"):"+ url2);
    		   System.out.println();
    		   
    		   writer.write(track_name + "\n");
    		   writer.write(artist_name + "\n\n");
        	
    	   }
    	   
    	   writer.close();
		}catch (ParseException e)
		{
			e.printStackTrace();
		}catch (IOException r)
		{
			r.printStackTrace();
		}
	}

}
