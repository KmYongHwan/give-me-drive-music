package com.example.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;



public class server_connection extends AppCompatActivity {
    private Handler mHandler;
    Socket socket;
    private String ip = "192.168.0.2";
    private int port = 5050;
    EditText et;
    TextView msgTV;

    @Override
    protected void onStop() {
        super.onStop();
        try{
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();

        //et = (EditText) findViewById(R.id.EditText01);
        Button btn = (Button) findViewById(R.id.dataBtn);
        //final TextView tv = (TextView)findViewById(R.id.TextView01);
        msgTV = (TextView)findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (et.getText().toString() != null || !et.getText().toString().equals("")){
                    ConnectThread th = new ConnectThread();
                    th.start();

                }
            }
        });

    }

    class ConnectThread extends Thread{
        public void run(){
            try{
                //μμΌ μμ±
                InetAddress serverAddr = InetAddress.getByName(ip);
                socket = new Socket(serverAddr,port);
                //μλ ₯ λ©μμ§
                String sndMsg = et.getText().toString();
                Log.d("temp", sndMsg);
                //λ°μ΄ν° μ μ‘
                PrintWriter out = new PrintWriter(new BufferedWriter(new
                        OutputStreamWriter(socket.getOutputStream())),true);
                out.println(sndMsg);//eclipseμ λ³΄λΌ λ΄μ©
                //λ°μ΄ν° μμ 
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String read = input.readLine();
                //νλ©΄ μΆλ ₯
                mHandler.post(new msgUpdate(read));
                Log.d("temp", read);
                socket.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }
    //λ°μ λ©μμ§ μΆλ ₯
    class msgUpdate implements Runnable{
        private String msg;
        public msgUpdate(String str){
            this.msg = str;
        }
        public void run(){
            msgTV.setText(msgTV.getText().toString()+ msg + "\n");
            //msgTV.setText(msg + "\n");
            //msgTV.setText("msgTV.setText\n");
        }
    };

}