package com.example.happiness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class myhappinessfeed_writeActivity extends AppCompatActivity  implements OnMapReadyCallback {

    EditText write_title;
    EditText write_contents;
    Uri selectedImageUri;

    ImageButton upload_contents;
    private static final int GET_PICTURE_URI = 0;

    String idcheck;
    String write_infotemp;

    // 타이머
    TextView tv_showtime;
    MyTimer myTimer;

    ImageButton checklocation;


    TextView tv_name;
    String locationname;
    String locationaddress;
    String locationlatitude;
    String locationlongitude;

    private GoogleMap receiveMap;
    LatLng HappyLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhappinessfeed_write);
        Log.v("알림", "생명주기 oncreat!");

        // SupportMapFragment는 Fragment의 서브 클래스로, Fragment에 지도를 배치할 수 있도록 해준다.
        SupportMapFragment mapreceiveFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.receivemap);
        mapreceiveFragment.getMapAsync(this);

        tv_name = findViewById(R.id.location_name);
//        tv_address = findViewById(R.id.address);

        // googlemap 화면에서 이름, 주소, point를 받아온다.
        Intent receivelocationintent = getIntent();

        // String locationname
        locationname = receivelocationintent.getStringExtra("name");
        locationaddress = receivelocationintent.getStringExtra("address");
        receivelocationintent.getStringExtra("point");

//        String receivelatitude;
//        String receivelongitude;

        Log.v("receivelocationintent - name, address 확인 알림","  name : " + locationname + " address : " + locationaddress + receivelocationintent.getStringExtra("point") );

        // 받아온 이름이 null이 아닐 때 settext해준다.
//        if(receivelocationintent.getStringExtra("name") != null){
            // 받아온 이름을 setText한다.
        tv_name.setText(receivelocationintent.getStringExtra("name"));
//        tv_address.setText(receivelocationintent.getStringExtra("address"));
//        }



        // button gogooglemap
        checklocation = findViewById(R.id.location_icon);
        Log.v("메인페이지 write 구글맵 버튼클릭 확인 0","버튼 알림 : " + checklocation);
        checklocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(myhappinessfeed_writeActivity.this,"버튼 클릭 알림",Toast.LENGTH_SHORT).show(); // 디버깅할 때 눌렀을 때 이 포인트 안 짚고 넘어감. 로그도 안뜸
                Log.v("메인페이지 write 구글맵 버튼클릭 확인 1","버튼 알림 : ");

                Intent intent = new Intent(getApplicationContext(), myhappinessfeed_googlemap.class);
                startActivity(intent);
                Log.v("메인페이지 write 구글맵 버튼클릭 확인 알림 2  ","버튼 알림 : ");

            }
        });

        //  onCreate() 함수에서는 앞서 정의한 MyTimer 클래스의 객체를 생성
        // 300초의 타이머를 준다. 1초씩 내려간다.
        tv_showtime = findViewById(R.id.showtimeTV);
        // 인자 정보는 타이머의 총 시간으로 60초로 지정하였으며, 두 번째 인자는 onTick() 함수가 호출될 시간 간격으로 1초를 넘겨준다.
        myTimer = new MyTimer(300000, 1000);
        Log.v("mainfeedwrite 5분짜리 타이머 생성 알림 ","" + "5분 타이머 생성");

        //글쓰기 화면의 제목
        write_title = (EditText) findViewById(R.id.write_title);

        //글스기 화면의 내용
        write_contents = (EditText) findViewById(R.id.write_contents);

        //글쓰기 화면의 체크 버튼
        upload_contents = (ImageButton) findViewById(R.id.upload_icon);
        upload_contents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 로그인할 때 저장해준 아이디를 가지고 있는 쉐어드 파일을 불러온다.
                SharedPreferences userid = getSharedPreferences("useridFile", MODE_PRIVATE);
                // 로그인할 때 저장해준 벨류값을 가지고 있는 key를 useridString에 넣어줘서 이 key로 언제든지 회원정보에 접근할 수 있게 한다.
                String useridString = userid.getString("userid","");

                // 저장된 회원정보를 불러오기 위해 같은 네임파일을 찾음.
                SharedPreferences testUserInfo = getSharedPreferences("testuserinfo",0);

                // 해당 아이디(KEY)로 해당 유저에 대한 정보를 가져올 수 있다.
                // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key)와 value 값을 불러와준다.
                String thisUserinfo =  testUserInfo.getString(useridString, "");

                // 이름 -1 , 닉네임 - 2, 아이디 - 3, 비밀번호 - 4로 이루어진 회원정보에서 세 번째 값인(index 2) 아이디를 가져온다.
                // String idcheck
                idcheck = thisUserinfo.split(",")[2];
                Log.v("myhappinessfeed _ write 화면에서 id 값 확인 알림 : "," " + idcheck);

                // 로그인한 아이디가 key가 맞으면 제목 + 내용 + 이미지 저장
                if(testUserInfo.contains(idcheck)) {

                    // 이미지와 위치 둘 다 null이 아닐 경우에만 게시물 등록 가능
                    if (selectedImageUri == null || locationname == null ) {

                        Toast.makeText(myhappinessfeed_writeActivity.this, "위치와 사진을 잘 넣으셨나요?", Toast.LENGTH_SHORT).show();

                    } else {

                        // 메인피드 글쓰기 창에서
                        // 체크 버튼을 눌렀을 때 저장되어서 메인피드의 리사이클러뷰에 불러와져야함.
                        // 그러기 위해 shared 파일을 하나 더 만들어줌
                        SharedPreferences UserUploadContentsPreferences = getSharedPreferences("UserUploadContentsPreferences", 0);

                        // editor라는게 필요해서 만들어줬다.
                        SharedPreferences.Editor useruploadContentsEditor = UserUploadContentsPreferences.edit();

                        // 쉐어드에 값이 있는지 없는지 체크하기 위해 먼저 getString을 해준다.
                        String checkSharedEmpty = UserUploadContentsPreferences.getString(idcheck, "");

                        // 내가 입력한 값들을 받기 위한 변수들과 String 문자열들
                        EditText editTextName = (EditText) findViewById(R.id.write_title);
                        String Write_Title = editTextName.getText().toString();
                        Log.v("myhappinessfeed _ write 화면에서 Write_Title 값 확인 알림 : ", " " + Write_Title);

                        EditText editTextContents = (EditText) findViewById(R.id.write_contents);
                        String Write_Contents = editTextContents.getText().toString();
                        Log.v("myhappinessfeed _ write 화면에서 Write_Contents 값 확인 알림 : ", " " + Write_Contents);

                        if (selectedImageUri != null) {
                            String Select_Image = selectedImageUri.toString();
                            Log.v("myhappinessfeed _ write 화면에서 Select_image 값 확인 알림 : ", " " + Select_Image);
                        }

                        // 쉐어드에 값이 없다면 = 최초 실행했다면,
                        if (checkSharedEmpty.equals("")) {

                            // 제목, 내용, 이미지들을 string에 다 넣어줌.
                            // String write_infotemp
                            write_infotemp = editTextName.getText().toString() + ","
                                    + editTextContents.getText().toString() + ","
                                    + selectedImageUri.toString() + ","
                                    + locationname + ","
                                    + locationaddress + ","
                                    + locationlatitude + ","
                                    + locationlongitude;

                            useruploadContentsEditor.putString(idcheck, write_infotemp);
                            Log.v("myhappinessfeed _ write 화면에서 shared가 빈값인지 여부를 통해 쉐어드에 값이 있냐 없냐 판단 확인 알림 ", " " + write_infotemp);

                            // 최초로 등록되는 게시물 저장 반영함
                            useruploadContentsEditor.commit();

                        } else {

                            // 처음 string값에 넣어줬던 게시물 string값을 first_contents로 불러와줌
                            String first_contents = UserUploadContentsPreferences.getString(idcheck, "");
                            Log.v("myhappinessfeed _ write 화면에서 sb_contentsinfo에 기존 값 더하기 전에 have_contents라는 key값으로 받아온 값 확인 알림  ", " " + first_contents);

                            StringBuffer sb_contentsinfo = new StringBuffer();

                            // write_infotemp라는 string 값에 세 값을 담아줌.
                            // String write_infotemp
                            write_infotemp = "&" + editTextName.getText().toString() + ","
                                    + editTextContents.getText().toString() + ","
                                    + selectedImageUri.toString() + ","
                                    + locationname + ","
                                    + locationaddress + ","
                                    + locationlatitude + ","
                                    + locationlongitude;

                            sb_contentsinfo.append(write_infotemp);
                            Log.v("myhappinessfeed _ write 화면에서 sb_contentsinfo에 write_infotemp(제목 + 내용 + 이미지)가 추가되는지 확인 알림  ", " write_infotemp : " + write_infotemp + "  sb_contentsinfo : " + sb_contentsinfo);

                            // 기존 value인 first_contents에 추가되는 값들(sb_contentsinfo.toString())을 더해준다.
                            useruploadContentsEditor.putString(idcheck, first_contents + sb_contentsinfo.toString());
                            Log.v("myhappinessfeed _ write 화면에서 sb_contentsinfo에 write_infotemp(제목 + 내용 + 이미지)가 추가되는지 확인 알림 2 ", " value 값 = write_infotemp + sb_contentsinfo : " + first_contents + sb_contentsinfo.toString());

                            // 파일에 최종 반영함
                            useruploadContentsEditor.commit();

                        }

                        Intent intent = new Intent(getApplicationContext(), myhappinessfeedActivity.class);
                        //intent라는 데이터 통로를 만들어줬다.
                        startActivity(intent);
                        finish();

                    }
//                // mainfeed에서 startActivityForResult 호출 받아 - setresult해주는 내용
//
//                EditText editTextName = (EditText) findViewById(R.id.write_title);
//                intent.putExtra("title", editTextName.getText().toString());
//                //write activity에서 write_title를 가져와 editTextName 변수명을 설정해주고 editTextName 뷰 안에 있는 텍스트 내용을 getText, 그대로 받겠다.
//                //intent로 putextra를 해주겠다.
//
//                Log.v("write activity 알림","title 값 putextra " + write_title + "title" + editTextName );
//
//                EditText editTextContents = (EditText) findViewById(R.id.write_contents);
//                intent.putExtra("contents", editTextContents.getText().toString());
//                //write activity에서 write_contents를 가져와 editTextContents 변수명을 설정해주고 editTextContents 뷰 안에 있는 텍스트 내용을 getText, 그대로 받겠다.
//                //intent로 putextra를 해주겠다.
//
//                if(selectedImageUri != null){
//                    intent.putExtra("uri",selectedImageUri.toString()); // 사진 올리지 않았을 때 null 값 예외처리
//                    Log.v("myhappinesscommunity_image 알림","값 확인 : selectedImageUri.toString()  " + selectedImageUri.toString()  + "  selectedImageUri  " + selectedImageUri);
//                }
//
//                Log.v("write activity 알림","contents 값 putextra " + write_contents + "contents" + editTextContents );
//
//                setResult(RESULT_OK, intent);
//                // 결과코드를 'RESULT_OK'로 세팅. 이후 이전 액티비티로 돌아가 onActivityResult()가 호출됨.
//                // intent를 전달하겠다.
//
//                Log.v("write activity 알림","setResult" + RESULT_OK + intent );
//
//                finish(); // 액티비티를 끝낸다.
                }
            }
        });

        // 글쓰기 취소
        ImageButton cancelbutton = (ImageButton) findViewById(R.id.x_icon);
        cancelbutton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), myhappinessfeedActivity.class);

                        startActivity(intent);

                    }
                }
        );



        // 갤러리 열어서 이미지 등록하는 단계
        findViewById(R.id.gallery_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myhappinessfeed_writeActivity.this, "갤러리 열기", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                startActivityForResult(intent, GET_PICTURE_URI);
            }
        });

//        //암시적 인텐트로 지도열기
//
//        ImageButton open_map = (ImageButton) findViewById(R.id.location_icon);
//        open_map.setOnClickListener(new Button.OnClickListener() {
//
//            public void onClick(View v) {
//                Intent open_map = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5376294, 126.7377372")); //계양구청 뜨도록.
//
//                startActivity(open_map);
//
//            }
//
//        });

//        // 암시적 인텐트로 카메라 열기
//        ImageButton open_camera = (ImageButton) findViewById(R.id.camera_icon);
//        open_camera.setOnClickListener(new Button.OnClickListener() {
//
//            public void onClick(View v) {
//                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                startActivity(open_camera);
//
//            }
//
//        });


        // 각 화면들에 이런 요소들이 필요할 것 같다 > 리스트업 > 나갔다가 들어왔을 때 현상유지? 계속 진행?
        // 장시간  //임시저장, 비밀번호 찾기 > 이 때 해보면 어떨까? 아이디어? >> 그 다음에 구체적으로 구현.
        //intent - filter
        // activity 리스트업, 암시적 intent, 화면들,

//        //암시적 인텐트로 동영상찍기
//        ImageButton open_video = (ImageButton) findViewById(R.id.video_icon);
//        open_video.setOnClickListener(new Button.OnClickListener() {
//
//            public void onClick(View v) {
//                Intent open_video = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//
//                startActivity(open_video);
//
//            }
//
//        });

    }

//    onstart할 때 위치권한 체크

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // > 권한 없을 경우 최초 권한 요청 또는 사용자에 의한 재요청 확인.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // 권한 재요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            }
        }

    }



    // 갤러리에서 불러온 이미지 붙여넣는 단계
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

        if (requestCode == GET_PICTURE_URI && resultCode == RESULT_OK && data != null && data.getData() != null) {

            ImageView imageview = (ImageView) findViewById(R.id.mainfeed_write_image);
            selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);

        }
    }

    // clickHandler() 함수에서는 버튼 두 개에 대한 이벤트 처리가 구현
    public void clickHandler(View view)
    {

        switch(view.getId())
        {
            // Start 버튼을 클릭했을때는 MyTimer의 start() 함수를 통해 타이머를 시작하고
            case R.id.btnStart:
                myTimer.start();
                Log.v("mainfeedwrite 타이머 start 알림 ","" + "5분 타이머 start");
                break;

            // Reset 버튼을 클릭했을 때는 cancle() 함수를 통해 타이머를 취소
            case R.id.btnReset :
                myTimer.cancel();
                // reset버튼을 누르면 5분이 뜬다.
                tv_showtime.setText("5 : 00");
                Log.v("mainfeedwrite 타이머 reset 알림 ","" + "5분 타이머 reset");
                break;

        }
    }

    // googlemap 화면에서 이름, 주소, 위도, 경도 받아온다.
    @Override
    public void onMapReady(GoogleMap googleMap) {

        receiveMap = googleMap;

        // 초기 좌표값 서울로 지정
        LatLng SEOUL = new LatLng(37.56, 126.97);
        receiveMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        receiveMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        Intent receivelocationintent = getIntent();
        // String locationname
        locationname = receivelocationintent.getStringExtra("name");
        // String receiveaddress
        locationaddress= receivelocationintent.getStringExtra("address");
        receivelocationintent.getStringExtra("point");
        // String receivelatitude
        locationlatitude = receivelocationintent.getStringExtra("String latitude");
        // String receivelongitude
        locationlongitude = receivelocationintent.getStringExtra("String longitude");


        Log.v("receivelocationintent - name, address 확인 알림 2","  locationname : " + locationname + " locationaddress : " + locationaddress );
        Log.v("receivelocationintent - name, address 확인 알림 3"," locationlatitude :  " + locationlatitude  + " locationlongitude :  " + locationlongitude);

        // 위도와 경도가 아닌 이상 happylocation이란 좌표 생성한다.
        if(locationlatitude != null && locationlongitude != null ) {

            // LatLng HappyLocation : 위도와 경도로 좌표를 생성
            HappyLocation = new LatLng(Double.parseDouble(locationlatitude), Double.parseDouble(locationlongitude));

            // MarkerOptions 구글 맵에 표시할 마커에 대한 옵션 설정
            // https://mailmail.tistory.com/19
            MarkerOptions markerOptions = new MarkerOptions();

            //  Position 위치(필수)
            // 지도에서 마커의 위치에 대한 LatLng 값입니다. 이는 Marker 객체의 유일한 필수 속성입니다.
            markerOptions.position(HappyLocation);
            markerOptions.title(receivelocationintent.getStringExtra("name"));
            markerOptions.snippet(receivelocationintent.getStringExtra("address"));
            //.showInfoWindow(); 추가하면 화면에 마카가 뜸
            receiveMap.addMarker(markerOptions).showInfoWindow();

            // CameraUpdateFactory
            // movecamera : camera 좌표를 서울역 근처로 옮겨 봅니다
            receiveMap.moveCamera(CameraUpdateFactory.newLatLng(HappyLocation));
            // animateCamera() 는 근거리에선 부드럽게 변경합니다
            //출처: https://bitsoul.tistory.com/145 [Happy Programmer~]
            // 15로 설정하면 적당하게 zoom 되는듯.
            receiveMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        }

    }

    // CountDownTimer 상속받는 MyTimer 클래스 구현
    public class MyTimer extends CountDownTimer
    {
        // 생성자 함수로 첫 번째 인자(millisinfuture)로 타이머 동작하는 총 시간으로 밀리세컨트(ms) 단위로 넘어온다.
        // 두 번재 인자(countDownInterval)는 카운트다운 되는 시간을 의미.
        public MyTimer(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }

        // CountDownTimer는 추상 클래스로 onTick() 함수와 onFinish() 함수를 반드시 오버라이딩한다.
        // 첫 번째 onTick() 함수는 생성자 인수 countDownInterval로 지정된 시간 간격마다 호출되는 함수
        // 함수에 넘어오는 인자로는 현재 타이머의 남은 시간이 넘어온다.
        @Override
        public void onTick(long millisUntilFinished) {
//            tv_showtime.setText(millisUntilFinished/1000 + " 초");
//            tv_showtime.setText((millisUntilFinished/60000)+":"+(millisUntilFinished/5000));
            tv_showtime.setText("" + String.format("%d : %d ",
                    TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

            // 0분 10초 남았을 때 메시지
            if((TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished)==4) &&
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))==50)
            {
                Toast.makeText(getApplicationContext(), "10초 남았습니다!", Toast.LENGTH_SHORT).show();
                Log.v("mainfeedwrite toast 메시지 확인 알림 ","" + "10초 남았을 때 toast 메시지");
            }

            // 0분 1초 남았을 때 1초 동안 진동
            if((TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished)==4) &&
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))==45)
            {

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // 1초 동안 진동한다. // vibrate 진동 권한 넣어줘야 한다.
                v.vibrate(1000);
                Log.v("mainfeedwrite 진동 확인 알림 ","" + "1초 남았을 때 진동");
            }
        }

        //  onFinish() 함수는 타이머가 끝났을 때 호출되는 함수
        @Override
        public void onFinish()
        {
            tv_showtime.setText("시간이 더 필요하신가요?");
        }
    }

}

//    onstart할 때 위치정보 체크해보기
//
//    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//    LocationListener locationListener = new LocationListener() {
//        @Override public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//        @Override public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override public void onProviderDisabled(String provider) {
//
//        }
//
//        @Override public void onLocationChanged(Location location) {
//            Log.d("Location", location.toString());
//        }
//    };
//
//    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//
////    출처: https://unikys.tistory.com/283 [All-round programmer]
//
//    @Override protected void onStart() {
//        super.onStart(); // Activity가 처음으로 시작하거나 다시 시작한 상태이다. 따라서 GPS의 사용가능 여부를 검사한다.
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//        if (!gpsEnabled) { // GPS가 사용가능하지 않다면 사용자에게 요청하는 다이얼로그를 띄우고 처리한다.
//            // android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS 인텐트로 GPS 설정을 유도한다. } }
//        }
//
//        //        출처: https://unikys.tistory.com/276 [All-round programmer]
//    }



//    // onstop시 데이터 저장되도록
//    @Override
//    protected void onStop() {
//        super.onStop();
////        Log.v("알림","글쓰는 화면 생명주기 onpause!");
////        Toast.makeText(getApplicationContext(), "임시저장되었다.", Toast.LENGTH_SHORT).show();
//        // Activity가 종료되기 전에 저장한다.
//        //SharedPreferences를 sFile이름, 기본모드로 설정
//
//        SharedPreferences sharedPreferences = getSharedPreferences("contentsFile",MODE_PRIVATE);
//
//        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        String text = write_contents.getText().toString(); // 사용자가 입력한 저장할 데이터
//        editor.putString("text",text); // key, value를 이용하여 저장하는 형태
//
//        //다양한 형태의 변수값을 저장할 수 있다.
//        //editor.putString();
//        //editor.putBoolean();
//        //editor.putFloat();
//        //editor.putLong();
//        //editor.putInt();
//        //editor.putStringSet();
//
//        //최종 커밋
//        editor.commit();
//
//    }

//    @Override public void onPause() {
//        super.onPause(); // Always call the superclass method first
//        // Release the Camera because we don't need it when paused
//        // and other activities might need to use it.
//        Log.v("알림","글쓰는 화면 생명주기 onpause!");
//        Toast.makeText(getApplicationContext(), "임시저장되었다.", Toast.LENGTH_SHORT).show();
//        if (mCamera != null) {
//            mCamera.release();
//            mCamera = null; }
//    }


//    //제목과 내용에 입력하면 그 값대로 전달되는 코드
//
//    @Override
//    public void onClick(View v) {
//        // TODO Auto-generated method stub
//        Intent intent = new Intent(this, myhappinessfeedActivity.class);
//        intent.putExtra("name",write_title.getText().toString());
//        intent.putExtra("name2",write_contents.getText().toString());
//
//        startActivity(intent);
//        finish();
//    }

/*        findViewById(R.id.video_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(myhappinessfeed_writeActivity.this, "동영상 찍기", Toast.LENGTH_SHORT).show();

                Intent open_video= new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                startActivity(open_video);

            }
        });*/

//                final EditText editTexttitle = (EditText) findViewById(R.id.write_title);
//                final EditText editTextcontents = (EditText) findViewById(R.id.write_contents);

//                // 4. 사용자가 입력한 내용을 가져와서
//                String Strtitle = editTexttitle.getText().toString();
//                String Strcontents = editTextcontents.getText().toString();

//        //임시저장 작업을 위한 작업
//        write_contents = (EditText)findViewById(R.id.write_contents);


//        datasave = (TextView)findViewById(R.id.datasave);
//
//        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
//        SharedPreferences contents = getSharedPreferences("contentsFile",MODE_PRIVATE);
//
//        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
//        String text = contents.getString("text","");
//        datasave.setText(text);

//         체크 아이콘 누르면 업로드된 화면으로 넘어가는 코드

//        ImageButton upload_contents = (ImageButton) findViewById(R.id.upload_icon);
//        upload_contents.setOnClickListener(new Button.OnClickListener(){
//
//            public void onClick(View v){
//                Intent intent=(new Intent(getApplicationContext(),myhappinessfeedActivity.class));
//
//                startActivity(intent);
//
//            }
//
//        });


//        암시적 intent로 지도 열기
//        findViewById(R.id.location_icon).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(myhappinessfeed_writeActivity.this, "지도 열기", Toast.LENGTH_SHORT).show();
//
//                //암시적 인텐트 목적에 맞는 호출 : 지도보기, 연락처보기, 인터넷, SNS 공유 등등.
//                Intent open_map = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5376294, 126.7377372")); //계양구청 뜨도록.
//
//                startActivity(open_map);
//
//            }
//        });


//        findViewById(R.id.camera_icon).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(myhappinessfeed_writeActivity.this, "사진 찍기", Toast.LENGTH_SHORT).show();
//
//                Intent open_camera= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                startActivity(open_camera);
//
//            }
//        });

//암시적 인텐트로 사진찍기
//        다른 어플리케이션들의 앱 컴포넌트를 실행하려면, 명시적 인텐트가 아닌 묵시적 인텐트가 필요.
//        왜냐하면 이들의 클래스 이름들을 알 수 없기 때문.
//        예를 들면, 카메라 앱을 실행하는 SNS 어플리케이션들은 그것의 클래스 이름을 알지 못한다.


//                    StringBuffer sb_contentsinfo = new StringBuffer();
//
////                    String end_add = "/";
//
//                    // StringBuffer의 값이 없을때 즉 처음 추가할때는 / 를 안붙이고
//                    // 그 다음부터는 무조건 앞에 $ 를 붙인다는 것을 써줬다.
//                    if(!sb_contentsinfo.toString().equals("$"))
//                    {
//                        sb_contentsinfo.append("$");
//                    }
//
//                    // write_infotemp라는 string 값에 세 값을 담아줌.
//                    // String write_infotemp
//                    write_infotemp = editTextName.getText().toString() + ","
//                            + editTextContents.getText().toString() + ","
//                            + selectedImageUri.toString() ;
//
//                    sb_contentsinfo.append(write_infotemp);
//
//                    Log.v("myhappinessfeed _ write 화면에서 게시글 쓰는대로 stringbuffer값 증가하는지 값 확인 알림 : "," " + sb_contentsinfo);
//
//                    // "HappyContents"란 key 값을 가져왔고 vaule 값인 editHappyContents에 들어갈 값을 저장할 것이다.
//                    useruploadContents.putString("have_contents", sb_contentsinfo.toString());
//                    Log.v("myhappinessfeed _ write 화면에서 게시글 쓰는대로 stringbuffer값 증가하는지 값 확인 알림 2 : "," " + sb_contentsinfo.toString());
//
//                    // 파일에 최종 반영함
//                    useruploadContents.commit();

//                    // write_info라는 string 값에 세 값을 담아줌.
//                    String write_info = editTextName.getText().toString() + ","
//                                        + editTextContents.getText().toString() + ","
//                                        + selectedImageUri.toString() ;
//
//
//                    Log.v("myhappinessfeed _ write 화면에서 write_info 값 확인 알림 : "," " + write_info);
//
//                    // "HappyContents"란 key 값을 가져왔고 vaule 값인 editHappyContents에 들어갈 값을 저장할 것이다.
//                    useruploadContents.putString(idcheck, write_info);
//                    Log.v("myhappinessfeed _ write 화면에서 write_info 값 확인 알림 2 : "," " + write_info);
//
//                    // 파일에 최종 반영함
//                    useruploadContents.commit();

//                    SharedPreferences contents_key = getSharedPreferences("contents_key", MODE_PRIVATE);
//                    SharedPreferences.Editor contents_editor = contents_key.edit();
//                    contents_editor.putString("write_title", editTextName.getText().toString());
//                    contents_editor.commit(); //완료한다.