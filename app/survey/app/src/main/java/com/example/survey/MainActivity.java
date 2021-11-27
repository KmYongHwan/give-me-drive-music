package com.example.survey;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

public class MainActivity extends AppCompatActivity {

    private Handler mHandler;
    Socket socket;
    private String ip = "192.168.0.2";
    private int port = 5050;
    EditText et;
    TextView msgTV;

    private DatabaseReference mPostReference;
    Toolbar toolbar;

    private Button btRecom;
    private Button btVoice;
    private TextView txVoice;
    private final int myVoiceCode = 1234;

    private TextView txData;
    private Button btData;

    Long que;
    String ans;
    String sort = "que";
    ArrayAdapter<String> arrayAdapter;
    public ArrayList<String> arrayIndex = new ArrayList<>();
    public ArrayList<String> arrayData = new ArrayList<>();
    ArrayList<String> db_data;
    ArrayList<String> db_data_mood;
    String db_data_string = "";
    String db_data_mood_string = "";

    String sort2 = "que";
    ArrayAdapter<String> arrayAdapter2;
    public ArrayList<String> arrayIndex2 = new ArrayList<>();
    public ArrayList<String> arrayData2 = new ArrayList<>();

    public static String[] read_Result;
    public static String[] result_song;
    public static String[] result_artist;

    public static int k;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        getFirebaseDatabase();
        getFirebaseDatabase2();

        db_data = arrayData;
        Log.d("test", "db_data : "+db_data);
        Log.d("test", "arrayData : "+arrayData);


        for(String str: db_data){
            db_data_string += str+" ";
        }

        btRecom = findViewById(R.id.dataBtn);
        btVoice = findViewById(R.id.voice);
        txVoice = findViewById(R.id.textView);


        btVoice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startVoiceIntent();
            }


        });

        mHandler = new Handler();

        //et = (EditText) findViewById(R.id.EditText01);
        Button btn = (Button) findViewById(R.id.dataBtn);
        //final TextView tv = (TextView)findViewById(R.id.TextView01);
        msgTV = (TextView)findViewById(R.id.textView);

        btRecom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "추천 눌렸음", Toast.LENGTH_SHORT).show();

                db_data = arrayData;
                db_data_string = "";
                for(String str:db_data){
                    db_data_string += str+" ";
                }

                db_data_mood = arrayData2;
                db_data_mood_string = "";
                for(String str:db_data_mood){
                    db_data_mood_string += str+" ";
                }



                ConnectThread th = new ConnectThread();
                th.start();
                //if (et.getText().toString() != null || !et.getText().toString().equals("")){
                   // ArrayList<String> db_data = ((SurveyResultActivity)mContext).getFirebaseDatabase();
                    //String db_data_string = "";
                    //for(String str:db_data){
                       // db_data_string += str+" ";
                    //}
                   // Log.d("temp", "db_data_string is : " + db_data_string);
                    //ConnectThread th = new ConnectThread();
                    //th.start();

                //}

            }
        });

    }
    public void getFirebaseDatabase(){


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        //ListView listView = (ListView) findViewById(R.id.db_list_view);
        //listView.setAdapter(arrayAdapter);

        //Log.d("temp", "arrayAdapter : "+arrayAdapter);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("getFirebaseDatabase", "key: " + dataSnapshot.getChildrenCount());
                arrayData.clear();
                arrayIndex.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    Firebase get = postSnapshot.getValue(Firebase.class);
                    String[] info = {String.valueOf(get.que), get.ans};
                    //String Result = setTextLength(info[0],10) + setTextLength(info[1],10);
                    //String Result = setTextLength(info[1],10);
                    String Result = info[1];

                    arrayData.add(Result);//결과
                    arrayIndex.add(key);//번호
                    Log.d("getFirebaseDatabase", "key: " + key);
                    Log.d("getFirebaseDatabase", "info: " + info[0] + info[1]);
                    Log.d("getFirebaseDatabase", "arrayData: " + arrayData);
                    Log.d("getFirebaseDatabase", "arrayIndex: " + arrayIndex);
                    String temp = arrayData.toString();
                    Log.d("getFirebaseDatabase", "temp: " + temp);

                }
                arrayAdapter.clear();
                arrayAdapter.addAll(arrayData);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("getFirebaseDatabase","loadPost:onCancelled", databaseError.toException());
            }
        };
        Query sortbyAge = FirebaseDatabase.getInstance().getReference().child("survey_test").orderByChild(sort);
        sortbyAge.addListenerForSingleValueEvent(postListener);

    }

    public void getFirebaseDatabase2(){

        arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        //ListView listView = (ListView) findViewById(R.id.db_list_view);
        //listView.setAdapter(arrayAdapter2);

        //Log.d("temp", "arrayAdapter : "+arrayAdapter);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("getFirebaseDatabase", "key: " + dataSnapshot.getChildrenCount());
                arrayData2.clear();
                arrayIndex2.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    Firebase get = postSnapshot.getValue(Firebase.class);
                    String[] info = {String.valueOf(get.que), get.ans};
                    //String Result = setTextLength(info[0],10) + setTextLength(info[1],10);
                    //String Result = setTextLength(info[1],10);
                    String Result = info[1];

                    arrayData2.add(Result);//결과
                    arrayIndex2.add(key);//번호
                    Log.d("getFirebaseDatabase", "key: " + key);
                    Log.d("getFirebaseDatabase", "info: " + info[0] + info[1]);
                    Log.d("getFirebaseDatabase", "arrayData: " + arrayData2);
                    Log.d("getFirebaseDatabase", "arrayIndex: " + arrayIndex2);
                    String temp = arrayData2.toString();
                    Log.d("getFirebaseDatabase", "temp: " + temp);

                }
                arrayAdapter2.clear();
                arrayAdapter2.addAll(arrayData2);
                arrayAdapter2.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("getFirebaseDatabase","loadPost:onCancelled", databaseError.toException());
            }
        };
        Query sortbyAge = FirebaseDatabase.getInstance().getReference().child("mood_test").orderByChild(sort2);
        sortbyAge.addListenerForSingleValueEvent(postListener);

    }


    class ConnectThread extends Thread{
        public void run(){
            try{
                //소켓 생성
                InetAddress serverAddr = InetAddress.getByName(ip);
                socket = new Socket(serverAddr,port);
                //입력 메시지


                //SurveyResultActivity dbload;
                //ArrayList<String> db_data = dbload.getFirebaseDatabase();


                //String sndMsg = et.getText().toString();

                String sndMsg = "";
                sndMsg = db_data_string + db_data_mood_string;

                Log.d("temp", "sndMsg : " + sndMsg);
                Log.d("temp", "After sndMsg, db_data_string : " + db_data_string);
                Log.d("temp", "After sndMsg, db_data_mood_string : " + db_data_mood_string);
                //데이터 전송
                PrintWriter out = new PrintWriter(new BufferedWriter(new
                        OutputStreamWriter(socket.getOutputStream())),true);


                //out.println(sndMsg);//eclipse에 보낼 내용
                out.println(sndMsg);//eclipse에 보낼 내용
                sndMsg = "";
                db_data_string = "";
                db_data_mood_string = "";

                //데이터 수신
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String read = input.readLine();
                read_Result = (null);
                read_Result = read.split(",");
                int arr_split_index = (int)((read_Result.length + 1) / 2);



                Log.d("test", "11111111111");
                Log.d("test", "read_Result is : " +read_Result);
                //for (int i = 0; i < arr_split_index; i++)
                //{
                    //result_song[i] = read_Result[i];
                    //Log.d("test", "result_song is ")
                //}
                result_song = (null);
                result_artist = (null);
                result_song = Arrays.copyOfRange(read_Result, 0, arr_split_index);
                result_artist = Arrays.copyOfRange(read_Result, arr_split_index, read_Result.length);

                Log.d("test", "22222222222");
                //for (int j = 0 ; j < arr_split_index; j++)
                //{
                    //result_artist[j] = read_Result[arr_split_index + j];
                //}
                Log.d("test", "33333333333333");
                Log.d("test", "read_Result.length is : "+read_Result.length);
                Log.d("test", "arr_split_index is : " +arr_split_index);
                Log.d("test", "result_song is : "+result_song[0] +"\n" +result_song.length);
                Log.d("test", "result_artist is : "+result_artist[0] +"\n"+result_artist.length);

                Log.d("test", "result_song is : "+result_song[1] +"\n" +result_song.length);
                Log.d("test", "result_artist is : "+result_artist[1] +"\n"+result_artist.length);



                //화면 출력
                k = 0;
                for (k = 0; k<arr_split_index ; k++){
                    mHandler.post(new msgUpdate(result_song[k], result_artist[k]));
                }

               // for (int k = 0; k<read_Result.length ; k++){
                    //mHandler.post(new msgUpdate(read_Result[k]));
                //}

                msgTV.setText(null);



                Log.d("temp", read);
                socket.close();



            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }
    //받은 메시지 출력
    class msgUpdate implements Runnable{
        private String msg;
        private String msg2;
        //public msgUpdate(String str){
            //this.msg = str;
        //}
        //public void run(){
            //msgTV.setText(msgTV.getText().toString()+ msg + "\n");
            //msgTV.setText(msg + "\n");
            //msgTV.setText("msgTV.setText\n");
        //}
        public msgUpdate(String str, String str2){
            this.msg = str; this.msg2 = str2;
        }
        public void run(){
            msgTV.setText(msgTV.getText().toString()+ msg + "," +msg2+ "\n");

            //msgTV.setText(msg + "\n");
            //msgTV.setText("msgTV.setText\n");
        }


    };







    protected void startVoiceIntent(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "인식 중 입니다...");
        startActivityForResult(intent, myVoiceCode);
    }

    protected String getRecogStr(Intent data){
        ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        return result.get(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == myVoiceCode && resultCode == RESULT_OK && data != null){
            String msg = getRecogStr(data);
            txVoice.setText(msg);
            FuncVoiceOrderCheck(msg);
        }
    }

    private void FuncVoiceOrderCheck(String VoiceMsg){
        if(VoiceMsg.length() < 1) return;
        if(VoiceMsg.indexOf("메뉴") > -1){
            finish();
            Intent intent = new Intent(getApplicationContext(), SurveyActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_survey:
                // User chose the "Settings" item, show the app settings UI...
                finish();
                Intent intent = new Intent(getApplicationContext(), SurveyActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_survey_result:
                finish();
                Intent intent2 = new Intent(getApplicationContext(), SurveyResultActivity.class);
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}