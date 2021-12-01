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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Recommend_result extends AppCompatActivity {
    Toolbar toolbar;
    TextView text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_result);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        text_view = (TextView) findViewById(R.id.tv_result);
        Intent intent_result = getIntent();
        Log.d("thread", "Intent is : " + intent_result);

        //String result = intent_result.getExtras().getString("test_str");
        //text_view.setText(result);
        String[] result_song = intent_result.getExtras().getStringArray("result_song");

        String[] result_artist = intent_result.getExtras().getStringArray("result_artist");
        Log.d("thread", "result_song is : ");
        for (int i = 0; i < result_song.length ; i++)
        {
            Log.d("thread", result_song[i]);
        }
        Log.d("thread", "result_artist is : ");
        for (int j = 0; j < result_artist.length ; j++)
        {
            Log.d("thread", result_artist[j]);
        }
        for (int k = 0 ; k < result_song.length; k++)
        {
            text_view.setText(text_view.getText().toString()+ result_song[k] + ", " + result_artist[k] + "\n");
        }
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
