import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;




public class FileTransferReceiver {
    public static final int DEFAULT_BUFFER_SIZE = 10000;
    public static String result_str = "";
    
    public static void main(String[] args) {
    	int port = 8000;
    	String filename = "image.jpg";
        //int port =  Integer.parseInt(args[0]);  //int port =  9999;
        //String filename = args[1];              //String filename = "test.mp4"; //저장할 파일 이름
        
    	APIExamFace api_examface = new APIExamFace();
    	
    	
    	try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("This server is listening... (Port: " + port  + ")");
            
            while(true) {
            	 Socket socket = server.accept();  //새로운 연결 소켓 생성 및 accept대기
                 InetSocketAddress isaClient = (InetSocketAddress) socket.getRemoteSocketAddress();
                 	
                 
                 System.out.println("A client("+isaClient.getAddress().getHostAddress()+
                               " is connected. (Port: " +isaClient.getPort() + ")");
                       
                 FileOutputStream fos = new FileOutputStream(filename);
                 InputStream is = socket.getInputStream();
                 
                         
                         
                          
                 double startTime = System.currentTimeMillis(); 
                 byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                 int readBytes;
                         
                         
                         
                 while ((readBytes = is.read(buffer)) != -1) {
                     fos.write(buffer, 0, readBytes);
              
                 }
                 double endTime = System.currentTimeMillis();
                 double diffTime = (endTime - startTime)/ 1000;;
      
                 System.out.println("time: " + diffTime+ " second(s)");
                 
                 
                 socket.close();
                 
                 socket = server.accept();
                 
                 
                 
                 result_str = api_examface.using_API();
                 System.out.println("result_str is : ");
                 System.out.println(result_str);
                 
                 
                 
                 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                 out.println(result_str);
                 
                 socket.close();
                 
                 
            	
            }
            
           
            
            	
            
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	
    	
    	/*
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("This server is listening... (Port: " + port  + ")");
            
            while (true) {
            	Socket socket = server.accept();  //새로운 연결 소켓 생성 및 accept대기
            	InetSocketAddress isaClient = (InetSocketAddress) socket.getRemoteSocketAddress();
            	
            	try {
            		System.out.println("A client("+isaClient.getAddress().getHostAddress()+
                            " is connected. (Port: " +isaClient.getPort() + ")");
                     
                    FileOutputStream fos = new FileOutputStream(filename);
                    InputStream is = socket.getInputStream();
                    
                    System.out.println("111111111111111");
                     
                    double startTime = System.currentTimeMillis(); 
                    byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                    int readBytes;
                    
                    System.out.println("2222222222222222");
                    
                    while ((readBytes = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, readBytes);
         
                    }
                    System.out.println("3333333333333333");
                    /*
                    System.out.println("111111111111111");
                    fos.flush();
                    System.out.println("222222222222222");
                    double endTime = System.currentTimeMillis();
                    double diffTime = (endTime - startTime)/ 1000;;
         
                    System.out.println("time: " + diffTime+ " second(s)");
                    
                    
                    result_str = APIExamFace.using_API();
                    System.out.println("result_str is : ");
                    System.out.println(result_str);
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    out.println(result_str);
                    //out.flush();
                    
                    result_str = null;
                    APIExamFace.result_str = null;
                    
                     
                    is.close();
                    fos.close();
                    socket.close();
                    server.close();
            		
            	}catch(Exception e) {
					System.out.println("Error");
					e.printStackTrace();
				}finally {
					socket.close();
					System.out.println("Done");
				}
            	
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }
}
