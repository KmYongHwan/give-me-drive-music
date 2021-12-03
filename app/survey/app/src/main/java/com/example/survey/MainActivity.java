package com.example.survey;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import android.graphics.Matrix;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceViewHolder;
    private Handler mHandler;
    private ImageReader mImageReader;
    private CameraDevice mCameraDevice;
    private CaptureRequest.Builder mPreviewBuilder;
    private CameraCaptureSession mSession;
    private int mDeviceRotation;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private SensorManager mSensorManager;
    private DeviceOrientation deviceOrientation;
    int mDSI_height, mDSI_width;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(ExifInterface.ORIENTATION_NORMAL, 0);
        ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_90, 90);
        ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_180, 180);
        ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_270, 270);
    }

    private Handler mHandler2;
    Socket socket;
    Socket socket_image;
    private String ip = "58.76.178.181";
    private int port = 5050;
    private int port_image = 8000;
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


    String mCurrentPhotoPath;

    private Button btintent;

    //============================gps관련 추가 ===================================

    LocationManager locationManager;
    Location mLastlocation = null;
    List<String> listProviders;
    String TAG = "LocationProvider";

    TextView tvGpsEnable, tvGpsLatitude, tvGpsLongitude;

    int on = 1;

    TextView Speed;
    double speed;

    boolean isNetworkEnabled = false;

    // 최소 GPS 정보 업데이트 거리 10미터
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    // 최소 GPS 정보 업데이트 시간 밀리세컨이므로 1분
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    Location location;
    String last_latitude = "";
    String last_longitude = "";
    String last_speed = "0";

    //==========================================================================


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 화면 켜진 상태를 유지합니다.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.take_photo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("sleep", "캡쳐 버튼 눌림.");
                takePicture();
                Toast.makeText(MainActivity.this, "캡쳐 성공!", Toast.LENGTH_SHORT).show();

            }
        });

        mSurfaceView = findViewById(R.id.surfaceView);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        deviceOrientation = new DeviceOrientation();

        initSurfaceView();

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
        //txVoice = findViewById(R.id.textView);


        btVoice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startVoiceIntent();
            }
        });

        mHandler2 = new Handler();

        //et = (EditText) findViewById(R.id.EditText01);
        Button btn = (Button) findViewById(R.id.dataBtn);
        //final TextView tv = (TextView)findViewById(R.id.TextView01);
        msgTV = (TextView)findViewById(R.id.textView);


        btRecom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "추천 진행중.......", Toast.LENGTH_SHORT).show();

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
                //takePicture();;

                ConnectThread2 th2 = new ConnectThread2();
                ConnectThread3 th3 = new ConnectThread3();
                /*
                try{
                    handlerThread.join();
                }catch(Exception e){
                    e.printStackTrace();
                }*/
                Log.d("thread", "th3 시작");
                th3.start();
                try{
                    th3.join();
                }catch(Exception e){
                    e.printStackTrace();
                }
                Log.d("thread", "th3 종료");

                Log.d("thread", "th 시작");
                ConnectThread th = new ConnectThread();
                th.start();
                try{
                    th.join();
                }catch(Exception e){
                    e.printStackTrace();
                }
                Log.d("thread", "th 종료");
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
                Intent intent_result = new Intent(getApplicationContext(), Recommend_result.class);
                intent_result.putExtra("result_song", result_song);
                intent_result.putExtra("result_artist", result_artist);
                startActivity(intent_result);
            }
        });


        //=======================gps관련============================


        //setContentView(R.layout.activity_main);
        //tvGpsEnable = (TextView) findViewById(R.id.tvGpsEnable);
        //tvGpsLatitude = (TextView) findViewById(R.id.GpsLatitude);
        //tvGpsLongitude = (TextView) findViewById(R.id.GpsLongitude);
        Speed = (TextView) findViewById(R.id.tv_velocity);

        double latitude = 0;
        double longitude = 0;



        //double latitude = current_location.getLatitude();
        //double longitude = current_location.getLongitude();
        //tvGpsLongitude.setText(": " + Double.toString(latitude));
        //tvGpsLongitude.setText(": " + Double.toString(longitude));


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return ;
        }

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);



        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(lastKnownLocation != null) {
            double lng = lastKnownLocation.getLongitude();
            double lat = lastKnownLocation.getLatitude();
            Log.d(TAG, "longitude=" +lng+ ", latitude=" + lat);
            last_latitude = Double.toString(lat);
            last_longitude = Double.toString(lng);
            //tvGpsLatitude.setText(Double.toString(lat));
            //tvGpsLongitude.setText(Double.toString(lng));
        }

        listProviders = locationManager.getAllProviders();
        boolean [] isEnable = new boolean[3];
        for(int i=0; i< listProviders.size(); i++) {
            if(listProviders.get(i).equals(LocationManager.GPS_PROVIDER)) {
                isEnable[0] = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                //tvGpsEnable.setText(": "+ String.valueOf(isEnable[0]));

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, (android.location.LocationListener)this);
            }
        }

        Log.d(TAG, listProviders.get(0) + '/' + String.valueOf(isEnable[0]));




        //==============첫 실행시 위도경도 초기화=============================================
        isNetworkEnabled = locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER);


        if (isNetworkEnabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);

            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    // 위도 경도 저장
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
        }
        //tvGpsLongitude.setText(": " + Double.toString(latitude));
       // tvGpsLongitude.setText(": " + Double.toString(longitude));

        String getSpeed = String.format("%.3f", location.getSpeed());

        last_latitude = Double.toString(latitude);
        last_longitude = Double.toString(longitude);

        last_speed = getSpeed;
        Log.d("test", "last_latitude : "+last_latitude);
        Log.d("test", "last_longitude : "+last_longitude);
        Log.d("test", "last_speed : "+ last_speed);



        //=======================gps관련 끝===================================
    }

    //=========================gps관련 추가 ===========================================
    @Override
    public void onProviderEnabled(String provider) {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, (LocationListener) this);
    }

    @Override
    public void onLocationChanged(Location location){

        double latitude = 0.0;
        double longitude = 0.0;
        Log.d("test", "is it executed?????===============================");

        if(location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            tvGpsLongitude.setText(": " + Double.toString(latitude));
            tvGpsLongitude.setText(": " + Double.toString(longitude));
            last_latitude = Double.toString(latitude);
            last_longitude = Double.toString(longitude);
            Log.d("test", "latitude is : " + Double.toString(latitude));
            Log.d("test", "longitude is : " + Double.toString(longitude));
            Log.d(TAG + "GPS: ", Double.toString(latitude) + '/' + Double.toString(longitude));
        }

        String getSpeed = String.format("%.3f", location.getSpeed());
        last_speed = getSpeed;
        Speed.setText(getSpeed);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){

    }

    @Override
    public void onProviderDisabled(String provider){

    }

    @Override
    protected void onStart(){
        super.onStart();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            } else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            }
        }

    }








    //=========================gps관련 추가 끝===========================================




    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(deviceOrientation.getEventListener(), mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(deviceOrientation.getEventListener(), mMagnetometer, SensorManager.SENSOR_DELAY_UI);

        //gps관련 추가
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, (LocationListener) this);

    }

    @Override
    protected void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(deviceOrientation.getEventListener());

        //gps관련 추가
        locationManager.removeUpdates((LocationListener) this);
    }

    public void initSurfaceView() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mDSI_height = displayMetrics.heightPixels;
        mDSI_width = displayMetrics.widthPixels;


        mSurfaceViewHolder = mSurfaceView.getHolder();
        mSurfaceViewHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d("Debug", "1111111111");
                initCameraAndPreview();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                if (mCameraDevice != null) {
                    mCameraDevice.close();
                    mCameraDevice = null;
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d("sleep", "surfaceChanged");

            }


        });
    }

    HandlerThread handlerThread = new HandlerThread("CAMERA2");

    @TargetApi(21)
    public void initCameraAndPreview() {
       // HandlerThread handlerThread = new HandlerThread("CAMERA2");
        Log.d("sleep", "handlerThread.start()");
        handlerThread.start();
        //try{
        //    handlerThread.join();
        //}catch(Exception e){

        //}

        Log.d("sleep", "체크포인트 6");
        mHandler = new Handler(handlerThread.getLooper());
        Log.d("sleep", "체크포인트 7");
        Handler mainHandler = new Handler(getMainLooper());
        Log.d("sleep", "try문 시작");
        try {
            //String mCameraId = "" + CameraCharacteristics.LENS_FACING_FRONT; // 후면 카메라 사용
            String mCameraId = "" + CameraCharacteristics.LENS_FACING_BACK; // 전면 카메라 사용

            CameraManager mCameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
            CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(mCameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

            Size largestPreviewSize = map.getOutputSizes(ImageFormat.JPEG)[0];
            Log.i("LargestSize", largestPreviewSize.getWidth() + " " + largestPreviewSize.getHeight());

            setAspectRatioTextureView(largestPreviewSize.getHeight(),largestPreviewSize.getWidth());

            Log.d("sleep", "체크포인트 1");
            mImageReader = ImageReader.newInstance(largestPreviewSize.getWidth(), largestPreviewSize.getHeight(), ImageFormat.JPEG,/*maxImages*/7);
            Log.d("sleep", "체크포인트 2");
            mImageReader.setOnImageAvailableListener(mOnImageAvailableListener, mainHandler);
            Log.d("sleep", "체크포인트 3");
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Log.d("sleep", "체크포인트 4");
            mCameraManager.openCamera(mCameraId, deviceStateCallback, mHandler);
            Log.d("sleep", "체크포인트 5");

        } catch (CameraAccessException e) {
            Toast.makeText(this, "카메라를 열지 못했습니다.", Toast.LENGTH_SHORT).show();
        }
    }


    private ImageReader.OnImageAvailableListener mOnImageAvailableListener = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            Log.d("sleep", "체크포인트 8");
            Image image = reader.acquireNextImage();
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            Log.d("sleep", "체크포인트 9");
            new SaveImageTask().execute(bitmap);
            Log.d("sleep", "체크포인트 10");
        }
    };


    private CameraDevice.StateCallback deviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {

            mCameraDevice = camera;
            try {
                takePreview();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            Toast.makeText(MainActivity.this, "카메라를 열지 못했습니다.", Toast.LENGTH_SHORT).show();
        }
    };


    public void takePreview() throws CameraAccessException {
        mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        mPreviewBuilder.addTarget(mSurfaceViewHolder.getSurface());
        mCameraDevice.createCaptureSession(Arrays.asList(mSurfaceViewHolder.getSurface(), mImageReader.getSurface()), mSessionPreviewStateCallback, mHandler);
    }

    private CameraCaptureSession.StateCallback mSessionPreviewStateCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(@NonNull CameraCaptureSession session) {
            mSession = session;

            try {

                mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                mSession.setRepeatingRequest(mPreviewBuilder.build(), null, mHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
            Toast.makeText(MainActivity.this, "카메라 구성 실패", Toast.LENGTH_SHORT).show();
        }
    };

    private CameraCaptureSession.CaptureCallback mSessionCaptureCallback = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
            mSession = session;
            unlockFocus();
        }

        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
            mSession = session;
        }

        @Override
        public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
            super.onCaptureFailed(session, request, failure);
        }
    };



    public void takePicture() {

        Log.d("sleep", "takePicture 호출됨");
        try {
            Log.d("sleep", "takePicture 호출됨, 1");
            CaptureRequest.Builder captureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);//用来设置拍照请求的request
            captureRequestBuilder.addTarget(mImageReader.getSurface());
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);

            Log.d("sleep", "takePicture 호출됨, 2");
            // 화면 회전 안되게 고정시켜 놓은 상태에서는 아래 로직으로 방향을 얻을 수 없어서
            // 센서를 사용하는 것으로 변경
            //deviceRotation = getResources().getConfiguration().orientation;
            mDeviceRotation = ORIENTATIONS.get(deviceOrientation.getOrientation());
            Log.d("@@@", mDeviceRotation+"");

            Log.d("sleep", "takePicture 호출됨, 3");
            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, mDeviceRotation);
            CaptureRequest mCaptureRequest = captureRequestBuilder.build();
            mSession.capture(mCaptureRequest, mSessionCaptureCallback, mHandler);
            Log.d("sleep", "takePicture 호출됨, 4");
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getRotatedBitmap(Bitmap bitmap, int degrees) throws Exception {
        if(bitmap == null) return null;
        if (degrees == 0) return bitmap;

        Matrix m = new Matrix();
        m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
    }



    /**
     * Unlock the focus. This method should be called when still image capture sequence is
     * finished.
     */
    private void unlockFocus() {
        try {
            // Reset the auto-focus trigger
            mPreviewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
                    CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
            mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            mSession.capture(mPreviewBuilder.build(), mSessionCaptureCallback,
                    mHandler);
            // After this, the camera will go back to the normal state of preview.
            mSession.setRepeatingRequest(mPreviewBuilder.build(), mSessionCaptureCallback,
                    mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    //출처 - https://codeday.me/ko/qa/20190310/39556.html
    /**
     * A copy of the Android internals  insertImage method, this method populates the
     * meta data with DATE_ADDED and DATE_TAKEN. This fixes a common problem where media
     * that is inserted manually gets saved at the end of the gallery (because date is not populated).
     * @see android.provider.MediaStore.Images.Media#insertImage(ContentResolver, Bitmap, String, String)
     */
    public static final String insertImage(ContentResolver cr,
                                           Bitmap source,
                                           String title,
                                           String description) {

        ContentValues values = new ContentValues();
        Log.d("test", "current tilte is : " + title);
        values.put(MediaStore.Images.Media.TITLE, title);

        values.put(MediaStore.Images.Media.DISPLAY_NAME, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        // Add the date meta data to ensure the image is added at the front of the gallery
        Log.d("test", "System.currentTimeMillis() : " + System.currentTimeMillis());
        //values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        //values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());

        Uri url = null;
        String stringUrl = null;    /* value to be returned */

        try {
            url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            if (source != null) {
                OutputStream imageOut = cr.openOutputStream(url);
                try {
                    source.compress(Bitmap.CompressFormat.JPEG, 50, imageOut);
                } finally {
                    imageOut.close();
                }

            } else {
                cr.delete(url, null, null);
                url = null;
            }
        } catch (Exception e) {
            if (url != null) {
                cr.delete(url, null, null);
                url = null;
            }
        }

        if (url != null) {
            stringUrl = url.toString();
        }

        return stringUrl;
    }


    private class SaveImageTask extends AsyncTask<Bitmap, Void, Void> {


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("sleep", "SaveImageTask 사진을 저장하였습니다 toast");
        }

        @Override
        protected Void doInBackground(Bitmap... data) {
            Log.d("sleep", "doinbackground 시작");
            Bitmap bitmap = null;


            try {
                bitmap = getRotatedBitmap(data[0], mDeviceRotation);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Build.VERSION.SDK_INT >= 29){
                insertImage(getContentResolver(), bitmap, "driver", "");
            }
            else{
                saveBitmapToJpg(bitmap, "driver");
            }



            return null;
        }

    }

    private String saveBitmapToJpg(Bitmap bitmap, String name){
        File storage = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File storage2 = "/storage/self/primary/Pictures/";
        String fileName = name+".jpg";
        //File imgFile = new File(storage, fileName);
        File imgFile = new File("/storage/self/primary/Pictures/", fileName);
        try{
            imgFile.createNewFile();
            FileOutputStream out = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.close();
        } catch (FileNotFoundException e){
            Log.e("saveBitmapToJpg", "FileNotFoundException : " + e.getMessage());
        } catch (IOException e){
            Log.e("saveBitmapToJpg","IOException : " + e.getMessage());
        }
        Log.d("test", "imgPath : " + getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/"+fileName);
        return  getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/"+fileName;

    }

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



    // 출처 https://stackoverflow.com/a/43516672
    private void setAspectRatioTextureView(int ResolutionWidth , int ResolutionHeight )
    {
        if(ResolutionWidth > ResolutionHeight){
            int newWidth = mDSI_width;
            int newHeight = ((mDSI_width * ResolutionWidth)/ResolutionHeight);
            updateTextureViewSize(newWidth,newHeight);

        }else {
            int newWidth = mDSI_width;
            int newHeight = ((mDSI_width * ResolutionHeight)/ResolutionWidth);
            updateTextureViewSize(newWidth,newHeight);
        }

    }

    private void updateTextureViewSize(int viewWidth, int viewHeight) {
        Log.d("@@@", "TextureView Width : " + viewWidth + " TextureView Height : " + viewHeight);
        mSurfaceView.setLayoutParams(new FrameLayout.LayoutParams(viewWidth, viewHeight));
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

    class ConnectThread3 extends Thread{
        public static final int  DEFAULT_BUFFER_SIZE = 10000;
        public void run(){
            try{
                Log.d("sleep", "sleep started");
                //sleep(10000);
                Log.d("sleep", "sleep ended");

                InetAddress serverAddr = InetAddress.getByName(ip);

                //이미지 전송 part===========================================================
                //socket_image = new Socket(serverAddr,port_image);

                //String serverIP = "192.168.0.2";              //String serverIP = "127.0.0.1";
                //int port = 8000;   //int port = 9999;
                //String FileName = "/storage/self/primary/Pictures/image.jpg";              //String FileName = "test.mp4";
                String FileName = "/storage/self/primary/Pictures/driver.jpg";
                Log.d("test", "filename?" + FileName);


                File file = new File(FileName);
                if (!file.exists()) {
                    System.out.println("File not Exist.");
                    System.exit(0);
                }

                long fileSize = file.length();
                long totalReadBytes = 0;
                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                int readBytes;
                double startTime = 0;

                try {
                    socket_image = new Socket(serverAddr,port_image);

                    //socket = new Socket(serverIP, port);
                    if(!socket_image.isConnected()){
                        System.out.println("Socket Connect Error.");
                        System.exit(0);
                    }


                    FileInputStream fis = new FileInputStream(file);


                    startTime = System.currentTimeMillis();
                    OutputStream os = socket_image.getOutputStream();
                    while ((readBytes = fis.read(buffer)) > 0) {
                        os.write(buffer, 0, readBytes);
                        totalReadBytes += readBytes;
                        System.out.println("In progress: " + totalReadBytes + "/"
                                + fileSize + " Byte(s) ("
                                + (totalReadBytes * 100 / fileSize) + " %)");
                    }

                    System.out.println("File transfer completed.");

                    fis.close();
                    os.close();
                        /*

                        //데이터 수신
                        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String read = input.readLine();
                        Log.d("test", "read is : " + read);*/

                    socket_image.close();
                } catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                double endTime = System.currentTimeMillis();
                double diffTime = (endTime - startTime)/ 1000;;
                double transferSpeed = (fileSize / 1000)/ diffTime;

                System.out.println("time: " + diffTime+ " second(s)");
                System.out.println("Average transfer speed: " + transferSpeed + " KB/s");

                //socket_image.close();
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    class ConnectThread2 extends Thread{
        public static final int DEFAULT_BUFFER_SIZE = 10000;
        public void run(){
            try{
                takePicture();
                Log.d("sleep", "picture captured");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    class ConnectThread extends Thread{
        public static final int DEFAULT_BUFFER_SIZE = 10000;
        public void run(){
            try{
                InetAddress serverAddr = InetAddress.getByName(ip);


                //설문결과 전송 part=====================================================
                //소켓 생성
                //InetAddress serverAddr = InetAddress.getByName(ip);
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
                out.println(last_latitude + " " + last_longitude + " " + last_speed);
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
                //배열
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


                /*
                //화면 출력
                k = 0;
                for (k = 0; k<arr_split_index ; k++){
                    mHandler2.post(new msgUpdate(result_song[k], result_artist[k]));
                }

               // for (int k = 0; k<read_Result.length ; k++){
                    //mHandler2.post(new msgUpdate(read_Result[k]));
                //}

                msgTV.setText(null);
                */



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
        if(VoiceMsg.indexOf("캡처") > -1 ){
            takePicture();
            Toast.makeText(MainActivity.this, "캡처 성공!", Toast.LENGTH_SHORT).show();
        }
        if(VoiceMsg.indexOf("캡쳐") > -1 ){
           takePicture();
            Toast.makeText(MainActivity.this, "캡처 성공!", Toast.LENGTH_SHORT).show();
        }
        if(VoiceMsg.indexOf("추천") > -1){
            //takePicture();

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
            //takePicture();;

            ConnectThread2 th2 = new ConnectThread2();
            ConnectThread3 th3 = new ConnectThread3();
                            /*
                try{
                    handlerThread.join();
                }catch(Exception e){
                    e.printStackTrace();
                }*/

            th3.start();
            try{
                th3.join();
            }catch(Exception e){
                e.printStackTrace();
            }


            ConnectThread th = new ConnectThread();
            th.start();
            try{
                th.join();
            }catch(Exception e){
                e.printStackTrace();
            }


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
            Intent intent_result = new Intent(getApplicationContext(), Recommend_result.class);
            intent_result.putExtra("result_song", result_song);
            intent_result.putExtra("result_artist", result_artist);
            startActivity(intent_result);


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