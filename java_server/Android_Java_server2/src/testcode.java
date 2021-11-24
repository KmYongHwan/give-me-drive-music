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

import java.io.FileReader;
import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class testcode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try
		{
			
			BufferedReader reader = new BufferedReader(
					new FileReader("C:\\Users\\±èÀÎ¼­\\OneDrive\\¹ÙÅÁ È­¸é\\give-me-drive-music-file\\[gettoptracks].txt"));
			String track, artist, g="start";
			int n=0;
			
			while( g != null )
			{
				
				track = reader.readLine();
				artist = reader.readLine();
				
				
				System.out.println(artist +" - "+track);   //
				System.out.println();    //
				
				StringBuilder urlBuilder = new StringBuilder("https://ws.audioscrobbler.com/2.0/"); /*URL*/
				urlBuilder.append("?" + URLEncoder.encode("method","UTF-8") + "=" + URLEncoder.encode("track.gettoptags", "UTF-8")); 
				urlBuilder.append("&" + URLEncoder.encode("artist","UTF-8") + "=" + URLEncoder.encode(artist, "UTF-8")); 
				urlBuilder.append("&" + URLEncoder.encode("track","UTF-8") + "=" + URLEncoder.encode(track, "UTF-8")); 
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
		//	System.out.println(sb);
		//	System.out.println();
        
			
			
       //
				String temp = sb.toString();
				
				File file1 = new File("C:\\Users\\±èÀÎ¼­\\OneDrive\\¹ÙÅÁ È­¸é\\give-me-drive-music-file\\"+n+". "+track+ ".txt");
				if(file1.exists()==false) file1.createNewFile();
				
				n++;
				
				FileWriter file_writer = new FileWriter(file1); 
				BufferedWriter writer = new BufferedWriter(file_writer);
       
				
		
				JSONParser jparser = new JSONParser();
				JSONObject jobject = (JSONObject) jparser.parse(temp);
				
				if(jobject == null) continue;
    	   
				System.out.println("jobject: " + jobject);
    	   
				jobject = (JSONObject)jobject.get("toptags");
    	   
				System.out.println("jobject: " + jobject);
				JSONArray jarray = (JSONArray) jobject.get("tag");
				JSONObject jobject2 = (JSONObject) jobject.get("@attr");
    	   
				System.out.println("jobject2(@attr): " + jobject2);
				System.out.println("jarray(tag): "+ jarray);
				System.out.println();
				for(int i =0; i<jarray.size(); i++)
				{
					JSONObject obj = (JSONObject)jarray.get(i);
    		   
    		   
					long count = (long)obj.get("count");
					String name = (String)obj.get("name");
					String url2 = (String)obj.get("url");
    		   
    		   
					System.out.println("count("+i+"):" + count);
					System.out.println("name("+i+"):" + name);
					System.out.println("url("+i+"):"+ url2);
					System.out.println();
    		   
					writer.write("count("+i+"):"+ count + "\n");
		    		writer.write("name("+i+"):"+name + "\n");
		    		writer.write("url("+i+"):"+ url2 + "\n\n");
        	
				}
				
				g = reader.readLine();
				
				writer.close();
			}
			
			
			reader.close();
		}
		
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		catch (IOException r)
		{
			r.printStackTrace();
		}
	}

}


