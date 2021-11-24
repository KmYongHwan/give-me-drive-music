package com.example.survey;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SurveyResultActivity extends AppCompatActivity {
    public static Context mContext;

    Toolbar toolbar;
    private DatabaseReference mPostReference;

    private Button btData;
    private Button bthome;

    Long que;
    String ans;
    String sort = "que";
    ArrayAdapter<String> arrayAdapter;
    static ArrayList<String> arrayIndex = new ArrayList<>();
    static ArrayList<String> arrayData = new ArrayList<>();

    Long que2;
    String ans2;
    String sort2 = "que";
    ArrayAdapter<String> arrayAdapter2;
    static ArrayList<String> arrayIndex2 = new ArrayList<>();
    static ArrayList<String> arrayData2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_result);
        mContext = this;


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btData = findViewById(R.id.dataBtn);





        btData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getFirebaseDatabase();
            }
        });

        bthome = findViewById(R.id.home);
        bthome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    //public void getFirebaseDatabase(){
    public ArrayList<String> getFirebaseDatabase(){
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.db_list_view);
        listView.setAdapter(arrayAdapter);


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

        return arrayData;
    }

    public ArrayList<String> getFirebaseDatabase2(){
        arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView2 = (ListView) findViewById(R.id.db_list_view);
        listView2.setAdapter(arrayAdapter2);

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

        return arrayData2;
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
    public String setText(String text){
        return text;
    }
    public void test(){
        Log.d("temp", "test is executed");
    }
}
