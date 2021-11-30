package com.example.survey;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

//import org.techtown.location.R;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;



public class location_time_velocity extends AppCompatActivity implements SensorEventListener{

    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4, textView5, textView6;
    int on = 1;

    SensorManager sensorManager;
    Sensor accelerSensor;
    Sensor linearAccelerSensor;
    float gAccX, gAccY, gAccZ, accX, accY, accZ, laccX, laccY, laccZ;
    float tempX, tempY, tempZ;
    double vX=0, vY=0, vZ=0;
    double gTatal, vtotal, lTotal;
    float alpha = 0.8f;

    public String vtotal_str;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView3 = findViewById(R.id.textView3);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);


        Button button = findViewById(R.id.button);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //linearAccelerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        Timer timer = new Timer();
        TimerTask TT = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startLocationService();
                        startTimeService();
                        startVService();
                    }
                });
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startLocationService();
                // startTimeService();

                switch(on)
                {
                    case 1:
                        //timer.schedule(TT, 0, 10);
                        timer.schedule(TT, 0, 1000);
                        on=0;
                        break;
                    case 0:
                        vtotal_str = Double.toString(vtotal);
                        timer.cancel();
                        on=1;
                        break;
                }


            }
        });

        AndPermission.with(this)
                .runtime()
                .permission(
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        showToast("허용된 권한 갯수 : " + permissions.size());
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        showToast("거부된 권한 갯수 : " + permissions.size());
                    }
                })
                .start();
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        try {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String message = "최근 위치 -> Latitude : " + latitude + "\nLongitude:" + longitude;

                textView.setText(message);
            }

            GPSListener gpsListener = new GPSListener();
            long minTime = 10000;
            float minDistance = 0;

            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime, minDistance, gpsListener);

            Toast.makeText(getApplicationContext(), "내 위치확인 요청함",
                    Toast.LENGTH_SHORT).show();

        } catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    public void startTimeService(){
        TimeZone tz;
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA);
        tz = TimeZone.getTimeZone("Asia/Seoul");
        sdf.setTimeZone(tz);

        String getTime = sdf.format(date);

        textView2.setText(getTime);

    }

    public void startVService(){
        //vX = (accX * 0.01) *3.6 + vX;
        //vY = (accY * 0.01) *3.6 + vY;
        //vZ = (accZ * 0.01) *3.6 + vZ;

        vX = (accX * 1.0) *3.6;
        vY = (accY * 1.0) *3.6;
        vZ = (accZ * 1.0) *3.6;

        vtotal = Math.sqrt(Math.pow(vX, 2) + Math.pow(vY, 2) + Math.pow(vZ, 2));
        String temp = Double.toString(vtotal);

        textView3.setText(temp);

        temp = Double.toString(accX);
        textView4.setText(temp);
        temp = Double.toString(accY);
        textView5.setText(temp);
        temp = Double.toString(accZ);
        textView6.setText(temp);

    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, accelerSensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == accelerSensor) {
            //include gravity
            gAccX = event.values[0];
            gAccY = event.values[1];
            gAccZ = event.values[2];

            tempX = alpha * tempX + (1-alpha) * event.values[0];
            tempY = alpha * tempY + (1-alpha) * event.values[1];
            tempZ = alpha * tempZ + (1-alpha) * event.values[2];

            accX = event.values[0] - tempX;
            accY = event.values[1] - tempY;
            accZ = event.values[2] - tempZ;



        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String message = "내 위치 -> Latitude : "+ latitude + "\nLongitude:"+ longitude;
            textView.setText(message);
        }


        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }


        public void onStatusChanged(String provider, int status, Bundle extras) { }




    }

}
