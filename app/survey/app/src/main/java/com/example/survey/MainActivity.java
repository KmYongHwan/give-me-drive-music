package com.example.survey;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mPostReference;

    Toolbar toolbar;

    private Button btVoice;
    private TextView txVoice;
    private final int myVoiceCode = 1234;

    private TextView txData;
    private Button btData;

    Long que;
    String ans;
    String sort = "que";
    ArrayAdapter<String> arrayAdapter;
    static ArrayList<String> arrayIndex = new ArrayList<>();
    static ArrayList<String> arrayData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btVoice = findViewById(R.id.voice);
        txVoice = findViewById(R.id.textView);


        btVoice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startVoiceIntent();
            }
        });

        btData = findViewById(R.id.dataBtn);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.db_list_view);
        listView.setAdapter(arrayAdapter);

        btData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getFirebaseDatabase();
            }
        });
    }

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
            //txVoice.setText(msg);
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

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void getFirebaseDatabase(){
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
                    String Result = setTextLength(info[0],10) + setTextLength(info[1],10);
                    arrayData.add(Result);
                    arrayIndex.add(key);
                    Log.d("getFirebaseDatabase", "key: " + key);
                    Log.d("getFirebaseDatabase", "info: " + info[0] + info[1]);
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

    public String setTextLength(String text, int length){
        if(text.length()<length){
            int gap = length - text.length();
            for (int i=0; i<gap; i++){
                text = text + " ";
            }
        }
        return text;
    }
}