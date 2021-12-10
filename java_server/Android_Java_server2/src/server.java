import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class server implements Runnable {
	
	public static final int ServerPort = 5050;
	public static final String ServerIP = "192.168.0.2";
	public static String[] str_to_array;
	public static String[] str_to_array2;
	public static String Result_Array;
	public static String[] temp_arr;
	public static String result_of_image;
	public static ArrayList arr_of_str = new ArrayList();
	public static double latitude;
	public static double longitude;
	public static double speed;
	
	
	
	

	@Override
	public void run() {
		try {
			System.out.println("Connecting....");
			ServerSocket serverSocket = new ServerSocket(ServerPort);
			
			while (true) {
				//client 접속 대기
				Socket client = serverSocket.accept();
				System.out.println("Receiving....");
				try {
					
					System.out.println("executing face api....");
					APIExamFace api_examface = new APIExamFace();
					result_of_image = api_examface.using_API();
					
					System.out.println("얼굴인식 결과 : " + result_of_image);
					
					
					
					//client data 수신
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					
					String str = in.readLine(); //android에서 보낸 데이터를 str에 저장
					if (str.equals("")) {
						System.out.println("no input.");
						continue;
					}
					str_to_array = str.split(" ");
					System.out.println("Received: " + str);
					
					String str2 = in.readLine();
					
					str_to_array2 = str2.split(" ");
					latitude = Double.parseDouble(str_to_array2[0]);
					longitude = Double.parseDouble(str_to_array2[1]);
					speed = Double.parseDouble(str_to_array2[2]);
					
					System.out.println("latitude : "+latitude);
					System.out.println("longitude : "+longitude);
					System.out.println("speed : "+speed);
					
					
					
					
					
					testcode4_main test = new testcode4_main();
					
					//System.out.println("executing testcode4...");
					
					Result_Array = test.start_main(str_to_array, result_of_image, latitude, longitude, speed);
					//Result_Array = "okay";
					
					
					//========================
					//temp_arr = Result_Array.split(",");
					//System.out.println("temp_arr's length is : "+temp_arr.length);
					//========================
				
					//System.out.println("Result_Array is : "+Result_Array);
					
					
					
					//Result_Array = "testing";
					
					
					//client에 다시 전송
					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
					//out.println("Server Received: " + str);
					out.println(Result_Array);
					
					Result_Array = null;
					
					//test.Result_artist = null;
					//test.Result_name = null;
					
				
					
					
					
					
				}catch(Exception e) {
					System.out.println("Error");
					e.printStackTrace();
				}finally {
					client.close();
					System.out.println("Done");
				}
			}
			
		}catch(Exception e) {
			System.out.println("S : Error");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//APIExamFace api_examface2 = new APIExamFace();
		//String result_of_image2 = api_examface2.using_API();
		//System.out.println("result_of_image2 : ");
		//System.out.println(result_of_image2);
		Thread ServerThread = new Thread(new server());
		ServerThread.start();
		
		
		
		
		
		//weather_api_test weather_api= new weather_api_test();
		//weather_api.usingAPI();
	}

}
