package com.example.survey;

import android.app.Activity;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.Nullable;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Temp_survey3 extends AppCompatActivity {
    Toolbar toolbar;
    private RadioGroup radioGroup;
    private DatabaseReference mPostReference;

    Long que = 8L;
    String ans;

    static ArrayList<String> arrayIndex =  new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_survey3);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

    }

    //메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    //메뉴 버튼
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_survey:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(getApplicationContext(), SurveyActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    //아랫 부분 버튼들
    public void clickHandler(View view)
    {
        switch(view.getId())
        {
            case R.id.btn_before:
                finish();
                Intent intent1 = new Intent(getApplicationContext(), Temp_survey2.class);
                startActivity(intent1);
                break;

            case R.id.btn_home:
                finish();
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
                break;

            case R.id.btn_next:
                finish();
                Intent intent3 = new Intent(getApplicationContext(), Season_survey1.class);
                startActivity(intent3);
                break;
        }
    }
    public boolean IsExistID(){
        boolean IsExist = arrayIndex.contains(que);
        return IsExist;
    }

    public void postFirebaseDatabase(boolean add){
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if(add){
            Firebase post = new Firebase(que, ans);
            postValues = post.toMap();
        }
        childUpdates.put("/survey_test/" + que, postValues);
        mPostReference.updateChildren(childUpdates);
    }
    //라디오 그룹 클릭 리스너
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i){
            if(i == R.id.btn_1){
                ans = "excited";
                postFirebaseDatabase(true);
                Toast.makeText(Temp_survey3.this, "1 입력됐습니다", Toast.LENGTH_SHORT).show();
            }
            else if(i == R.id.btn_2){
                ans = "delighted";
                postFirebaseDatabase(true);
                Toast.makeText(Temp_survey3.this, "2 입력됐습니다", Toast.LENGTH_SHORT).show();
            }
            else if(i == R.id.btn_3){
                ans = "happy";
                postFirebaseDatabase(true);
                Toast.makeText(Temp_survey3.this, "3 입력됐습니다", Toast.LENGTH_SHORT).show();
            }
            else if(i == R.id.btn_4){
                ans = "content";
                postFirebaseDatabase(true);
                Toast.makeText(Temp_survey3.this, "4 입력됐습니다", Toast.LENGTH_SHORT).show();
            }
            else if(i == R.id.btn_5){
                ans = "relaxed";
                postFirebaseDatabase(true);
                Toast.makeText(Temp_survey3.this, "5 입력됐습니다", Toast.LENGTH_SHORT).show();
            }
            else if(i == R.id.btn_6){
                ans = "calm";
                postFirebaseDatabase(true);
                Toast.makeText(Temp_survey3.this, "6 입력됐습니다", Toast.LENGTH_SHORT).show();
            }
            else if(i == R.id.btn_7){
                ans = "tired";
                postFirebaseDatabase(true);
                Toast.makeText(Temp_survey3.this, "7 입력됐습니다", Toast.LENGTH_SHORT).show();
            }
            else if(i == R.id.btn_8){
                ans = "bored";
                postFirebaseDatabase(true);
                Toast.makeText(Temp_survey3.this, "8 입력됐습니다", Toast.LENGTH_SHORT).show();
            }
            else if(i == R.id.btn_9){
                ans = "depressed";
                postFirebaseDatabase(true);
                Toast.makeText(Temp_survey3.this, "9 입력됐습니다", Toast.LENGTH_SHORT).show();
            }
            else if(i == R.id.btn_10){
                ans = "frustrated";
                postFirebaseDatabase(true);
                Toast.makeText(Temp_survey3.this, "10 입력됐습니다", Toast.LENGTH_SHORT).show();
            }
            else if(i == R.id.btn_11){
                ans = "angry";
                postFirebaseDatabase(true);
                Toast.makeText(Temp_survey3.this, "11 입력됐습니다", Toast.LENGTH_SHORT).show();
            }
            else{
                ans = "tense";
                postFirebaseDatabase(true);
                Toast.makeText(Temp_survey3.this, "12 입력됐습니다", Toast.LENGTH_SHORT).show();
            }
        }
    };

}
