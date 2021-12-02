package com.example.survey;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

public class Recommend_result extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;

    String[] result_song;
    String[] result_artist;
    static ArrayList<String> result = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_result);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent_result = getIntent();
        Log.d("thread", "Intent is : " + intent_result);

        //String result = intent_result.getExtras().getString("test_str");
        //text_view.setText(result);

        result_song = intent_result.getExtras().getStringArray("result_song");
        result_artist = intent_result.getExtras().getStringArray("result_artist");

        /*
        Log.d("thread", "result_song is : ");
        for (int i = 0; i < result_song.length ; i++)
        {
            Log.d("thread", result_song[i]);
        }
        Log.d("thread", "result_artist is : ");
        for (int j = 0; j < result_artist.length ; j++)
        {
            Log.d("thread", result_artist[j]);
        }*/


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.db_list_view);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String[] tempData = result.get(position).split("\\s+");
                Log.d("test", Arrays.toString(tempData));

                Intent intent_result1 = new Intent(getApplicationContext(), YoutubeActivity.class);
                intent_result1.putExtra("result", tempData);
                startActivity(intent_result1);
            }
        });

        getArrayData();
        //listView.setOnItemClickListener(onClickListener);
        //listView.setOnItemLongClickListener(longClickListener);


        //ArrayList<String> result_s = new ArrayList<>(Arrays.asList(result_song));
        //ArrayList<String> result_a = new ArrayList<>(Arrays.asList(result_artist));


    }

    public void getArrayData(){
        result.clear();
        for (int k = 0 ; k < result_song.length; k++)
        {
            String result_1 = result_song[k] + ",\t" + result_artist[k];
            result.add(result_1);
        }
        arrayAdapter.addAll(result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

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

    public void clickHandler(View view)
    {
        switch(view.getId())
        {
            case R.id.btn_home:
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_next:
                finish();
                Intent intent2 = new Intent(getApplicationContext(), Whether_survey1.class);
                startActivity(intent2);
                break;
        }
    }

}
