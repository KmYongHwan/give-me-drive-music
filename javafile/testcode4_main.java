package test4;

import java.util.Vector;
import java.util.Arrays;
import java.util.Random;

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

import java.text.SimpleDateFormat;
import java.util.Date;

public class testcode4_main {
	//������
	public static final int fast =0;
	public static final int slow =1;
	//����
	public static final int spring	=0;
	public static final int summer 	=1;
	public static final int fall 	=2;
	public static final int winter	=3;
	//�ð���
	public static final int morning		=0;
	public static final int launch		=1;
	public static final int afternoon	=2;
	public static final int night		=3;
	public static final int dawn		=4;
	//����
	public static final int rain	=0;
	public static final int sunny	=1;
	public static final int cloudy	=2;
	public static final int thunder	=3;
	public static final int snow	=4;
	public static final int hot		=5;
	public static final int cold	=6;
	public static final int mid		=7;
	//���
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
	//������ "annoying", "angry", "nervous", "sad", "bored", "sleepy", "calm", "peaceful", "relaxed", "pleased", "happy", "excited"
	public static final int mood_annoying		=0;
	public static final int mood_angry			=1;
	public static final int mood_nervous		=2;
	public static final int mood_sad			=3;
	public static final int mood_bored			=4;
	public static final int mood_sleepy			=5;
	public static final int mood_calm			=6;
	public static final int mood_peaceful		=7;
	public static final int mood_relaxed		=8;
	public static final int mood_pleased		=9;
	public static final int mood_happy			=10;
	public static final int mood_excited		=11;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		String roadaddr =null ;
		///////////////////////////////////////// ������ �浵 ������ �ּҸ� �˾Ƴ���. -naver maps reverse geocoder ���
		try {
			StringBuilder urlBuilder = new StringBuilder("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc"); 
	        urlBuilder.append("?" + URLEncoder.encode("coords","UTF-8") + "=" + URLEncoder.encode("127.0199, 37.4845", "UTF-8")); 
	        urlBuilder.append("&" + URLEncoder.encode("orders","UTF-8") + "=" + URLEncoder.encode("roadaddr", "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("output","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); 
	        
	        
	        URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
			conn.setRequestMethod("GET");
			conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "99x7s0vnw0");
			conn.setRequestProperty("X-NCP-APIGW-API-KEY", "gvlTeHID3ZNb4SLXyLDsnCLF0cqlDYAlUIucGmau");
			
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
			//System.out.println(sb);
			//System.out.println();
			
			String temp = sb.toString();
		       
			
	    	JSONParser jparser = new JSONParser();
	    	JSONObject jobject = (JSONObject)jparser.parse(temp);   
	    	//System.out.println("1. "+jobject);
	    	JSONArray jarray = (JSONArray) jobject.get("results");
	    	//System.out.println("2. "+jarray);
	    	
	    	JSONObject tempObject = (JSONObject)jarray.get(0);
	    	JSONObject tempObject2 = (JSONObject)tempObject.get("region");
			//System.out.println("3. "+tempObject2);
			
			JSONObject area = (JSONObject)tempObject2.get("area1");
			//System.out.println("4. "+area);
			
			String area1 = (String)area.get("name");
			//System.out.println("5. area1: "+area1);
			
			area = (JSONObject)tempObject2.get("area2");
			String area2 = (String)area.get("name");
			//System.out.println("6. area2: "+area2);
			
			area = (JSONObject)tempObject2.get("area3");
			String area3 = (String)area.get("name");
			//System.out.println("6. area3: "+area3);
			
			tempObject2 = (JSONObject)tempObject.get("land");
			//System.out.println("7. land: " +tempObject2);
			
			roadaddr = (String)tempObject2.get("name");
			//System.out.println("8. roadaddr: " + roadaddr);
			
		/////////////////////////////////////////////////////////////////////////////////	
		}catch (IOException r)
		{
			r.printStackTrace();
		}catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		
		
		
		//���� ������ ������ ���� �����Ⱑ ����� ���Ͱ���
		Vector<String> feeling = new Vector<String>();
		Vector<String> mood = new Vector<String>(); 
		
		//[�������� ���]�ֺ�ȯ��(����, �ð���, ����, ���뷮-�� �뺯�ϴ� ������)�� ���� ������ ����� ����
		Vector<String> season_feeling = new Vector<String>(); 
		season_feeling.add(spring, "happy");
		season_feeling.add(summer, "tired");
		season_feeling.add(fall, "calm");
		season_feeling.add(winter, "delighted");
		
		
		Vector<String>time_feeling = new Vector<String>();
		time_feeling.add(morning, "tired");
		time_feeling.add(launch, "relaxed");
		time_feeling.add(afternoon, "tired");
		time_feeling.add(night, "happy");
		time_feeling.add(dawn, "calm");
		
		Vector<String>weather_feeling = new Vector<String>();
		weather_feeling.add(rain, "calm");
		weather_feeling.add(sunny, "happy");
		weather_feeling.add(cloudy, "depressed");
		weather_feeling.add(thunder, "tense");
		weather_feeling.add(snow, "delighted");
		weather_feeling.add(hot, "tired");
		weather_feeling.add(cold, "calm");
		weather_feeling.add(mid, "relaxed");
		
		Vector<String>velocity_feeling = new Vector<String>();
		velocity_feeling.add(fast, "excited");
		velocity_feeling.add(slow, "tired");
		
		//�ֺ�ȯ�� �����ڷᰡ ����� ����//////////////////////////////////////////////////////////////////////////////���� ��¥ �ð� �����ϱ�
		
		Date date = new Date();
		
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat hour = new SimpleDateFormat("HH");
		
		int mon = Integer.parseInt(month.format(date));
		int now = Integer.parseInt(hour.format(date));
		
		String season = null;
		switch(mon) {
		case 12:
		case 1:
		case 2:
			season = "winter";
			break;
		case 3:
		case 4:
		case 5:
			season = "spring";
			break;
		case 6:
		case 7:
		case 8:
		case 9:
			season = "summer";
			break;
			default: season = "fall";
		}
		
		 
		
		Vector<String> weather = new Vector<String>();
		String person = "angry";
		String v_a = "bored";
		
		testcode4_methods tools = new testcode4_methods();
		
		//System.out.println("§");
		tools.get_weather_api(weather, 37.4845, 127.0199);  //����latitude �浵longitude 
		//System.out.println("§");
		
		int road_type = tools.get_traffic_info(roadaddr, 37.4845, 127.0199);
		
		double velocity = 70 ; //�ȵ���̵� ��Ʃ������� ���ӵ� ������ ������ ��
		tools.get_traffic(feeling, velocity, velocity_feeling, road_type);
		
		//System.out.println("velocity: "+vel);
		
		//[�������� ���]������ �׿� ���� �����Ⱑ ����� ����
		Vector<String> feeling_to_mood = new Vector<String>();
		feeling_to_mood.add(excited, "excited");
		feeling_to_mood.add(delighted, "relaxed");
		feeling_to_mood.add(happy, "happy");
		feeling_to_mood.add(content, "pleased");
		feeling_to_mood.add(relaxed, "calm");
		feeling_to_mood.add(calm, "sad");
		feeling_to_mood.add(tired, "excited");
		feeling_to_mood.add(bored, "excited");
		feeling_to_mood.add(depressed, "peaceful");
		feeling_to_mood.add(frustrated, "angry");
		feeling_to_mood.add(angry, "nervous");
		feeling_to_mood.add(tense, "calm");
		
		
		// ������ �ֺ�ȯ�� ������ ���������� ��ȯ�Ѵ�. -> feeling�� ����
		tools.get_season(feeling, season, season_feeling);
		tools.get_time(feeling, now, time_feeling);
		tools.get_whether(feeling, weather, weather_feeling);
		feeling.add(person);
		feeling.add(v_a);
		
		//���������� ������ ������ ��ȯ�Ѵ�. -> mood�� ����
		tools.change_music(feeling, mood, feeling_to_mood);
		
		System.out.println(feeling);
		System.out.println(mood);
		
		Vector<Integer> music = new Vector<Integer>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0));
		
		
		// ������ �ε������� �� �������� ����ġ�� �����Ѵ�.
		for(int i=0; i<mood.size(); i++)
		{
			int index = tools.change(mood.get(i));
			music.set(index, music.get(index)+1);
		}
		
		System.out.println(music);
		
		String list[]= {"annoying", "angry", "nervous", "sad", "bored", "sleepy", "calm", "peaceful", "relaxed", "pleased", "happy", "excited"};
		int temps[] = {0,0,0,0,0,0,0,0,0,0,0,0};
		
		Random random = new Random();
		Vector<String> result_mood = new Vector<String>();
		
		//����ġ ���� �Լ�
		for(int i=0 ; i<11; i++)
		{
			int n = random.nextInt(mood.size()+12);
			int sum=0;
			int j=0;
			
			while(sum<=n)
			{ 
				sum= sum+ music.get(j)+1;  //music.get(j)�� ����ġ
				j++;
			}
			result_mood.add(list[j-1]);
		}
		
		System.out.println(result_mood);
		
		for(int i=0; i<result_mood.size(); i++)
		{
			if(result_mood.get(i).compareTo("annoying")==0) temps[mood_annoying]++;
			else if(result_mood.get(i).compareTo("angry")==0) temps[mood_angry]++;
			else if(result_mood.get(i).compareTo("nervous")==0) temps[mood_nervous]++;
			else if(result_mood.get(i).compareTo("sad")==0) temps[mood_sad]++;
			else if(result_mood.get(i).compareTo("bored")==0) temps[mood_bored]++;
			else if(result_mood.get(i).compareTo("sleepy")==0) temps[mood_sleepy]++;
			else if(result_mood.get(i).compareTo("calm")==0) temps[mood_calm]++;
			else if(result_mood.get(i).compareTo("peaceful")==0) temps[mood_peaceful]++;
			else if(result_mood.get(i).compareTo("relaxed")==0) temps[mood_relaxed]++;
			else if(result_mood.get(i).compareTo("pleased")==0) temps[mood_pleased]++;
			else if(result_mood.get(i).compareTo("happy")==0) temps[mood_happy]++;
			else temps[mood_excited]++;
		}
		
		int n=1;
		
		for(int i=0; i<12; i++)
		{
			try{
				StringBuilder urlBuilder = new StringBuilder("http://ws.audioscrobbler.com/2.0/"); /*URL*/
		        urlBuilder.append("?" + URLEncoder.encode("method","UTF-8") + "="+ URLEncoder.encode("tag.gettoptracks", "UTF-8")); 
		        urlBuilder.append("&" + URLEncoder.encode("tag","UTF-8") + "=" + URLEncoder.encode(list[i], "UTF-8")); 
		        urlBuilder.append("&" + URLEncoder.encode("api_key","UTF-8") + "=6a08a55c41eb47e08a8eaf0905079779"); 
		        urlBuilder.append("&" + URLEncoder.encode("format","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); 
		        
		        URL url = new URL(urlBuilder.toString());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        
				conn.setRequestMethod("GET");
				
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
				//System.out.println(sb);
				//System.out.println();
				
				String temp = sb.toString();
			       
				
		    	JSONParser jparser = new JSONParser();
		    	JSONObject jobject = (JSONObject)jparser.parse(temp);   
		    	//System.out.println("1. "+jobject);
		    	
		    	jobject = (JSONObject)jobject.get("tracks");   
		    	//System.out.println("2. "+jobject);
		    	
		    	JSONArray jarray = (JSONArray)jobject.get("track");
		    	//System.out.println("3. "+jarray);
		    	
		    	
		    	
		        for(int j=0; j<temps[i]; j++)
		        {
		        	JSONObject obj = (JSONObject)jarray.get(j);
		        	String name = (String)obj.get("name");
		        	String artist = (String)((JSONObject)obj.get("artist")).get("name");
		        	
		        	System.out.println(n +". "+name + " - "+ artist);
		        	n++;
		        	
		        }
		        
		        
			}catch (IOException r)
			{
				r.printStackTrace();
			}catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
		
		
		 
	}

}
