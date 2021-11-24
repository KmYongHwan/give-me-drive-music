import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class server implements Runnable {
	
	public static final int ServerPort = 5050;
	public static final String ServerIP = "192.168.0.2";
	public static String[] str_to_array;
	public static String Result_Array;
	public static String[] temp_arr;

	@Override
	public void run() {
		try {
			System.out.println("Connecting....");
			ServerSocket serverSocket = new ServerSocket(ServerPort);
			
			while (true) {
				//client ���� ���
				Socket client = serverSocket.accept();
				System.out.println("Receiving....");
				try {
					//client data ����
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					String str = in.readLine(); //android���� ���� �����͸� str�� ����
					if (str.equals("")) {
						System.out.println("no input.");
						continue;
					}
					str_to_array = str.split(" ");
					System.out.println("Received: " + str);
					
					System.out.println("executing testcode4...");
					testcode4_main test = new testcode4_main();
					Result_Array = test.start_main();
					//========================
					temp_arr = Result_Array.split(",");
					System.out.println("temp_arr's length is : "+temp_arr.length);
					//========================
				
					System.out.println("Result_Array is : "+Result_Array);
					
					//Result_Array = "testing";
					
					
					//client�� �ٽ� ����
					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
					//out.println("Server Received: " + str);
					out.println(Result_Array);
					
					Result_Array = null;
					test.Result_artist = null;
					test.Result_name = null;
					
					
					
					
					
					
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
		Thread ServerThread = new Thread(new server());
		ServerThread.start();
		
		
		
		
		
		//weather_api_test weather_api= new weather_api_test();
		//weather_api.usingAPI();
	}

}
