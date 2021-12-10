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

public class weather_api_test {
	
	public static void usingAPI() {
		try
		{
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=i83M6V%2BP4tMSbmzvE0Gnz7eKfKv9P5IXaSuIh2caGg4O3Ai1lztUtzV0aRdtZcMuS6tNGotNhwBHNLZb2hOkiA%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
	        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20211118", "UTF-8")); /*‘21년 6월 28일 발표*/
	        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("1442", "UTF-8")); /*06시 발표(정시단위) */
	        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /*예보지점의 X 좌표값*/
	        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/
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
			//System.out.println(sb);
			//System.out.println();
        
		
		
       //
			String temp = sb.toString();
       
		
    	   JSONParser jparser = new JSONParser();
    	   JSONObject jobject = (JSONObject) jparser.parse(temp);
    	   
    	   System.out.println("1. "+jobject);
    	   jobject = (JSONObject) jobject.get("response");
    	   System.out.println("2(response). "+jobject);
    	   jobject = (JSONObject) jobject.get("body");
    	   System.out.println("3(body). "+jobject);
    	   jobject = (JSONObject) jobject.get("items");
    	   System.out.println("4(items). "+jobject);
    	   JSONArray jarray = (JSONArray) jobject.get("item");
       
    	   for(int i =0; i<jarray.size(); i++)
    	   {
    		   JSONObject obj = (JSONObject)jarray.get(i);
    		  // int count = (int)obj.get("title");
    		   String category = (String)obj.get("category");
    		   double obsrVal = Double.parseDouble((String)obj.get("obsrValue"));
    		   System.out.println("category("+i+"):" + category);
    		   System.out.println("obserVal("+i+"):" + obsrVal);
    		   //System.out.println("url("+i+"):"+ url2);
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=i83M6V%2BP4tMSbmzvE0Gnz7eKfKv9P5IXaSuIh2caGg4O3Ai1lztUtzV0aRdtZcMuS6tNGotNhwBHNLZb2hOkiA%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
	        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20211118", "UTF-8")); /*‘21년 6월 28일 발표*/
	        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("1442", "UTF-8")); /*06시 발표(정시단위) */
	        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /*예보지점의 X 좌표값*/
	        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/
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
    	   
    	   System.out.println("1. "+jobject);
    	   jobject = (JSONObject) jobject.get("response");
    	   System.out.println("2(response). "+jobject);
    	   jobject = (JSONObject) jobject.get("body");
    	   System.out.println("3(body). "+jobject);
    	   jobject = (JSONObject) jobject.get("items");
    	   System.out.println("4(items). "+jobject);
    	   JSONArray jarray = (JSONArray) jobject.get("item");
       
    	   for(int i =0; i<jarray.size(); i++)
    	   {
    		   JSONObject obj = (JSONObject)jarray.get(i);
    		  // int count = (int)obj.get("title");
    		   String category = (String)obj.get("category");
    		   double obsrVal = Double.parseDouble((String)obj.get("obsrValue"));
    		   System.out.println("category("+i+"):" + category);
    		   System.out.println("obserVal("+i+"):" + obsrVal);
    		   //System.out.println("url("+i+"):"+ url2);
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
