package test4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Vector;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;


public class testcode4_methods {
	
	
	public static final int spring	=0;
	public static final int summer 	=1;
	public static final int fall 	=2;
	public static final int winter	=3;
	//feeling: 감정들을 저장하는 공간, season: 당시 계절, season_feeling: 각 계절 인덱스 별로 느낀다고 저장해 둔 감정
	//get_season: 계절을 토대로 현재 느끼는 감정을 feeling 벡터공간에 저장하는 메소드
	void get_season(Vector<String> feeling, String season, Vector<String> season_feeling)
	{
		if (season.compareTo("spring")==0) feeling.add(season_feeling.get(spring));
		else if(season.compareTo("summer")==0) feeling.add(season_feeling.get(summer));
		else if(season.compareTo("fall")==0) feeling.add(season_feeling.get(fall));
		else feeling.add(season_feeling.get(winter));
		System.out.println("+계절: "+feeling);
	}
	
	
	public static final int morning		=0;
	public static final int launch		=1;
	public static final int afternoon	=2;
	public static final int night		=3;
	public static final int dawn		=4;
	//feeling: 감정들을 저장하는 공간, now: 당시 시간대, time_feeling: 각 시간대 별로 느낀다고 저장해 둔 감정
	//get_time: 시간대를 토대로 현재 느끼는 감정을 feeling 벡터공간에 저장하는 메소드
	void get_time(Vector<String> feeling, int now, Vector<String> time_feeling)
	{
		if(now>=6 && now<11) feeling.add(time_feeling.get(morning));
		else if(now>=11 && now<14) feeling.add(time_feeling.get(launch));
		else if(now>=14 && now<19) feeling.add(time_feeling.get(afternoon));
		else if(now>=19 && now<=24) feeling.add(time_feeling.get(night));
		else feeling.add(time_feeling.get(dawn));
		System.out.println("+시간대: "+feeling);
	}
	
	
	public static final int rain	=0;
	public static final int sunny	=1;
	public static final int cloudy	=2;
	public static final int thunder	=3;
	public static final int snow	=4;
	public static final int hot		=5;
	public static final int cold	=6;
	public static final int mid		=7;
	//feeling: 감정들을 저장하는 공간, weather: 당시 계측된 날씨, weather_feeling: 각 날씨 인덱스 별로 느낀다고 저장해 둔 감정
	//get_season: 계측된 날씨를 토대로 현재 느끼는 감정을 feeling 벡터공간에 저장하는 메소드
	void get_whether(Vector<String> feeling, Vector<String> weather, Vector<String> weather_feeling)
	{
		for(int i=0; i<weather.size(); i++)
		{
			if((weather.get(i)).compareTo("rain")==0) feeling.add(weather_feeling.get(rain));
			else if((weather.get(i)).compareTo("sunny")==0) feeling.add(weather_feeling.get(sunny));
			else if((weather.get(i)).compareTo("cloudy")==0) feeling.add(weather_feeling.get(cloudy));
			else if((weather.get(i)).compareTo("thunder")==0) feeling.add(weather_feeling.get(thunder));
			else if((weather.get(i)).compareTo("snow")==0) feeling.add(weather_feeling.get(snow));
			else if((weather.get(i)).compareTo("hot")==0) feeling.add(weather_feeling.get(hot));
			else if((weather.get(i)).compareTo("cold")==0) feeling.add(weather_feeling.get(cold));
			else feeling.add(weather_feeling.get(mid));
		}
		System.out.println("+날씨: "+feeling);
	}
	
	
	public static final int fast =0;
	public static final int slow =1;
	//feeling: 감정들을 저장하는 공간, velocity: 교통량을 반영하고 있는 변수로 해당 도로의 통행속도, velocity_feeling: 각 빠르기 별로 느낀다고 저장해 둔 감정
	//get_traffic: 통행속도를 토대로 현재 느끼는 감정을 feeling 벡터공간에 저장하는 메소드
	void get_traffic(Vector<String> feeling, double velocity, Vector<String> velocity_feeling, int roadtype) {
		switch(roadtype) 
		{
		case ex:
			
			if(velocity >=90) feeling.add(velocity_feeling.get(fast));
			else feeling.add(velocity_feeling.get(slow));
			break;
		case its:
			
			if(velocity >= 70) feeling.add(velocity_feeling.get(fast));
			else feeling.add(velocity_feeling.get(slow));
			}
		System.out.println("+교통량: "+feeling);
	}
	
	
	//기상청 api를 통해서 현재 날씨를 받아온 후 weather 벡터 공간에 저장하는 메소드
	void get_weather_api(Vector<String> weather, double latitude, double longitude)
	{
		translocalpoint transpoint = new translocalpoint();
		
		translocalpoint.grid_xy temp2 = transpoint. new grid_xy();
		temp2 = transpoint.transLocalpoint(latitude, longitude);
		
		Date date = new Date();
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat day = new SimpleDateFormat("dd");
		SimpleDateFormat hour = new SimpleDateFormat("HH");
		SimpleDateFormat min = new SimpleDateFormat("mm");
		
		
		String today = year.format(date)+month.format(date)+day.format(date);
		
		String minute = min.format(date);
		String hour_string = hour.format(date);
		
		if(Integer.parseInt(minute) <45 ){
			minute = "30";
			if(Integer.parseInt(hour_string)<=10) hour_string = "0" + Integer.toString(Integer.parseInt(hour_string)-1);
			else hour_string = Integer.toString(Integer.parseInt(hour_string)-1);
		}else
		{
			minute = "00";
		}
		
		String base_time = hour_string + minute;
	
		
		try {
			System.out.println("//////////////////////////날씨 api 시도////////////////////////////"); 
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=i83M6V%2BP4tMSbmzvE0Gnz7eKfKv9P5IXaSuIh2caGg4O3Ai1lztUtzV0aRdtZcMuS6tNGotNhwBHNLZb2hOkiA%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); 
	        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); 
	        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(base_time, "UTF-8")); 
	        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + Integer.toString(temp2.x)); 
	        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + Integer.toString(temp2.y)); 
			URL url = new URL(urlBuilder.toString());
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
			conn.setRequestMethod("GET");
			conn.setRequestProperty("format", "JSON");
        
			//System.out.println("Response code: " + conn.getResponseCode());
        
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
			
			String temp = sb.toString();
		       
			
	    	JSONParser jparser = new JSONParser();
	    	JSONObject jobject = (JSONObject) jparser.parse(temp);
	    	//System.out.println("1. jobject: " + jobject);
	    	jobject = (JSONObject)jobject.get("response");
	    	//System.out.println("2. jobject(response): " +jobject);
	    	jobject = (JSONObject)jobject.get("body");
	    	//System.out.println("3. jobject(body): "+jobject);
	    	jobject = (JSONObject)jobject.get("items");
	    	//System.out.println("4. jobject(items): "+jobject);
	    	
	    	JSONArray jarray = (JSONArray)jobject.get("item");
	    	//System.out.println(" 5. jarray(item): "+ jarray);
	    	  
	   	   
	    	   for(int i =0; i<jarray.size(); i=i+6)
	    	   {
	    		   JSONObject obj = (JSONObject)jarray.get(i);
	    		   
	    		   String category = (String)obj.get("category");
	    		   String fcstVal = (String)obj.get("fcstValue");
	    		   String fcstTime = (String)obj.get("fcstTime");
	    		  // System.out.println("category("+i+"):" + category);
	    		  // System.out.println("fcstVal("+i+"):" + fcstVal);
	    		  // System.out.println("fcstTime("+i+"): " + fcstTime);
	    		   //System.out.println("url("+i+"):"+ url2);
	    		  // System.out.println();
	    		   
	    		   if(category.compareTo("PTY")==0)
	    		   {
	    			   if(fcstVal.compareTo("1")==0) weather.add("rain");
	    			   else if(fcstVal.compareTo("2")==0 || fcstVal.compareTo("3")==0) weather.add("snow");
	    		   }else if(category.compareTo("SKY")==0)
	    		   {
	    			   if(fcstVal.compareTo("1")==0) weather.add("sunny");
	    			   else if(fcstVal.compareTo("3")==0 || fcstVal.compareTo("4")==0) weather.add("cloudy");
	    		   }else if(category.compareTo("LGT")==0)
	    		   {
	    			   if(fcstVal.compareTo("0")!=0) weather.add("thunder");
	    		   }else if(category.compareTo("T1H")==0)
	    		   {
	    			   int val = Integer.parseInt(fcstVal);
	    			   if(val < 15) weather.add("cold");
	    			   else if(val<25) weather.add("mid");
	    			   else weather.add("hot");
	    		   }
	    		   
	    	   }
			
	    	//System.out.println("weather: " + weather);  
		}catch (ParseException e)
		{
			e.printStackTrace();
		}catch (IOException r)
		{
			r.printStackTrace();
		}
		
	}
	
	
	int change (String emotion) {
		if(emotion.compareTo("annoying")==0) return 0;
		else if(emotion.compareTo("angry")==0) return 1;
		else if(emotion.compareTo("nervous")==0) return 2;
		else if(emotion.compareTo("sad")==0) return 3;
		else if(emotion.compareTo("bored")==0) return 4;
		else if(emotion.compareTo("sleepy")==0) return 5;
		else if(emotion.compareTo("calm")==0) return 6;
		else if(emotion.compareTo("peaceful")==0) return 7;
		else if(emotion.compareTo("relaxed")==0) return 8;
		else if(emotion.compareTo("pleased")==0) return 9;
		else if(emotion.compareTo("happy")==0) return 10;
		else return 11;
		
	}
	
	
	public static final int excited			=0;
	public static final int delighted		=1;
	public static final int happy			=2;
	public static final int content			=3;
	public static final int relaxed			=4;
	public static final int calm			=5;
	public static final int tired			=6;
	public static final int bored			=7;
	public static final int depressed		=8;
	public static final int frustrated		=9;
	public static final int angry			=10;
	public static final int tense			=11;
	
	void change_music(Vector<String> feeling, Vector<String> mood, Vector<String> feeling_to_mood)
	{
		for(int i=0; i<feeling.size(); i++)
		{
			if((feeling.get(i)).compareTo("excited")==0) 		mood.add(feeling_to_mood.get(excited));
			else if((feeling.get(i)).compareTo("delighted")==0) mood.add(feeling_to_mood.get(delighted));
			else if((feeling.get(i)).compareTo("happy")==0) 	mood.add(feeling_to_mood.get(happy));
			else if((feeling.get(i)).compareTo("content")==0) 	mood.add(feeling_to_mood.get(content));
			else if((feeling.get(i)).compareTo("relaxed")==0) 	mood.add(feeling_to_mood.get(relaxed));
			else if((feeling.get(i)).compareTo("calm")==0) 		mood.add(feeling_to_mood.get(calm));
			else if((feeling.get(i)).compareTo("tired")==0) 	mood.add(feeling_to_mood.get(tired));
			else if((feeling.get(i)).compareTo("bored")==0) 	mood.add(feeling_to_mood.get(bored));
			else if((feeling.get(i)).compareTo("depressed")==0) mood.add(feeling_to_mood.get(depressed));
			else if((feeling.get(i)).compareTo("frustrated")==0)mood.add(feeling_to_mood.get(frustrated));
			else if((feeling.get(i)).compareTo("angry")==0) 	mood.add(feeling_to_mood.get(angry));
			else mood.add(feeling_to_mood.get(tense));
			
			
		}
	}
	
	
	public static final int ex =99;
	public static final int its = 100;
	int get_traffic_info(String roadname, double latitude, double longitude)
	{
		
		try {
			System.out.println("//////////////////////////교통소통정보 api 시도////////////////////////////"); 
			StringBuilder urlBuilder = new StringBuilder("https://openapi.its.go.kr:9443/trafficInfo"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("apiKey","UTF-8") + "=2fe7b9e181564900b45102bfb47d68ab"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("all", "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("drcType","UTF-8") + "=" + URLEncoder.encode("all", "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("routeNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("minX","UTF-8") + "=" + URLEncoder.encode(Double.toString(longitude-0.005), "UTF-8")); 
	        urlBuilder.append("&" + URLEncoder.encode("maxX","UTF-8") + "=" + URLEncoder.encode(Double.toString(longitude+0.005), "UTF-8")); 
	        urlBuilder.append("&" + URLEncoder.encode("minY","UTF-8") + "=" + URLEncoder.encode(Double.toString(latitude-0.005), "UTF-8")); 
	        urlBuilder.append("&" + URLEncoder.encode("maxY","UTF-8") + "=" + URLEncoder.encode(Double.toString(latitude+0.005), "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("getType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
	        
	        URL url = new URL(urlBuilder.toString());
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
			conn.setRequestMethod("GET");
			//conn.setRequestProperty("format", "JSON");
        
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
			System.out.println("//////////////////////////////////////////////////////////");
			System.out.println(sb);
			//System.out.println();
			
			String temp = sb.toString();
		       
			
	    	JSONParser jparser = new JSONParser();
	    	JSONObject jobject = (JSONObject) jparser.parse(temp);
	    	System.out.println("1. jobject: " + jobject);
	    	jobject = (JSONObject)jobject.get("body");
	    	JSONArray jarray = (JSONArray) jobject.get("items");
	    	System.out.println("2. jarray: "+ jarray);
	    	
	    	int n=0;
	    	double sum=0;
	    	
	    	for(int i=0; i< jarray.size(); i++)
	    	{
	    		JSONObject obj = (JSONObject)jarray.get(i);
	    		String name = (String)obj.get("roadName");
	    		if(roadname.compareTo(name)==0 && name.contains("고속도로")) {
	    			return ex;
	    		}
	    	}
	    	
	    	
	    	return its;
	    	
	    	
		}catch (ParseException e)
		{
			e.printStackTrace();
		}catch (IOException r)
		{
			r.printStackTrace();
		}
		
		return -1;
		
	}
	
	
	

}
