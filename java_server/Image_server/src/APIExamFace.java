
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// ���̹� ���ν� API ����
public class APIExamFace {
	public static String result_str = "";
	public static Double result2;
/*
    public static void main(String[] args) {

        StringBuffer reqStr = new StringBuffer();
        String clientId = "iqeJ_1R5DF0zkOnHl595";//���ø����̼� Ŭ���̾�Ʈ ���̵�";
        String clientSecret = "inalRrOQsR";//���ø����̼� Ŭ���̾�Ʈ ��ũ����";

        try {
            String paramName = "image"; // �Ķ���͸��� image�� ����
            String imgFile = "D://image_input//image.jpg";   //�м��� �̹����� ���
            File uploadFile = new File(imgFile);
            //String apiURL = "https://openapi.naver.com/v1/vision/celebrity"; // ������ �� �ν�
            String apiURL = "https://openapi.naver.com/v1/vision/face"; // �� ����
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);
            // multipart request
            String boundary = "---" + System.currentTimeMillis() + "---";
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            OutputStream outputStream = con.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
            String LINE_FEED = "\r\n";
            // file �߰�
            String fileName = uploadFile.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
            writer.append("Content-Type: "  + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();
            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();
            BufferedReader br = null;
            int responseCode = con.getResponseCode();
            if(responseCode==200) { // ���� ȣ��
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // ���� �߻�
                System.out.println("error!!!!!!! responseCode= " + responseCode);
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }
            String inputLine;
            if(br != null) {
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null ) {
                    response.append(inputLine);
                }
                br.close();
                //System.out.println("test2");
                //System.out.println(response.toString());
               // System.out.println("test");
                String temp = response.toString();
                JSONParser jparser = new JSONParser();
                JSONObject jobject = (JSONObject) jparser.parse(temp);
                
                //jobject = (JSONObject) jobject.get("faces");
                JSONArray jarray = (JSONArray) jobject.get("faces");
                //JSONObject obj = (JSONObject)jarray.get(0);
               // System.out.println("jarray");
                //System.out.println(jarray);
                
                JSONObject obj = (JSONObject)jarray.get(0);
               // System.out.println("obj");
                //System.out.println(obj);
                
                //JSONArray jarray2 = (JSONArray) obj.get("emotion");
                
                obj = (JSONObject) obj.get("emotion");
               // System.out.println("");
                //System.out.println("obj after emotion");
               // System.out.println(obj);
                
                //==================================================================================================================
                
                //JSONObject obj2 = (JSONObject)obj.get("value");
                //System.out.println("obj2");
                //System.out.println(obj2);
                
                //result_str = (String)obj.get("value");
                //System.out.println("result_str");
                System.out.println(result_str);
      		    
                
                
                
            } else {
                System.out.println("error !!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    */
    
    public static String using_API() {

        StringBuffer reqStr = new StringBuffer();
        String clientId = "iqeJ_1R5DF0zkOnHl595";//���ø����̼� Ŭ���̾�Ʈ ���̵�";
        String clientSecret = "inalRrOQsR";//���ø����̼� Ŭ���̾�Ʈ ��ũ����";

        try {
            String paramName = "image"; // �Ķ���͸��� image�� ����
            //String imgFile = "D://image_input//image.jpg";   //�м��� �̹����� ���
            String imgFile = "C://Users//������//eclipse-workspace//Image_server//image.jpg";
            File uploadFile = new File(imgFile);
            //String apiURL = "https://openapi.naver.com/v1/vision/celebrity"; // ������ �� �ν�
            String apiURL = "https://openapi.naver.com/v1/vision/face"; // �� ����
            URL url = new URL(apiURL);
            
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);
            // multipart request
            String boundary = "---" + System.currentTimeMillis() + "---";
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            OutputStream outputStream = con.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
            String LINE_FEED = "\r\n";
            // file �߰�
            String fileName = uploadFile.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
            writer.append("Content-Type: "  + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();
            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();
            BufferedReader br = null;
            int responseCode = con.getResponseCode();
            if(responseCode==200) { // ���� ȣ��
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // ���� �߻�
                System.out.println("error!!!!!!! responseCode= " + responseCode);
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }
            String inputLine;
            if(br != null) {
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null ) {
                    response.append(inputLine);
                }
                br.close();
                
                
                //System.out.println("test2");
                //System.out.println(response.toString());
               // System.out.println("test");
                String temp = response.toString();
                JSONParser jparser = new JSONParser();
                JSONObject jobject = (JSONObject) jparser.parse(temp);
                System.out.println("jobject");
                System.out.println(jobject);
                
                //jobject = (JSONObject) jobject.get("faces");
                JSONArray jarray = (JSONArray) jobject.get("faces");
                //JSONObject obj = (JSONObject)jarray.get(0);
               // System.out.println("jarray");
                //System.out.println(jarray);
                
                JSONObject obj = (JSONObject)jarray.get(0);
               // System.out.println("obj");
                //System.out.println(obj);
                
                //JSONArray jarray2 = (JSONArray) obj.get("emotion");
                
                obj = (JSONObject) obj.get("emotion");
               // System.out.println("");
               // System.out.println("obj after emotion");
                //System.out.println(obj);
                
                //==================================================================================================================
                
                //JSONObject obj2 = (JSONObject)obj.get("value");
                //System.out.println("obj2");
                //System.out.println(obj2);
                
                result_str = (String)obj.get("value");
                //System.out.println("result_str");
                System.out.println(result_str);
                result2 = (Double)obj.get("confidence");
                System.out.println(result2);
                
                
      		    
                
                
                
            } else {
                System.out.println("error !!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return result_str;
    }
}