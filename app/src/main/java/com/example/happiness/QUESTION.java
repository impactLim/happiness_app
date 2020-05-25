package com.example.happiness;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

// 구글맵 좋은 예제
// https://webnautes.tistory.com/647
// + 참고예제
// https://lovefields.github.io/android/2017/04/18/post35.html
// https://bitsoul.tistory.com/145
// 앱에서 지도를 사용하려면 OnMapReadyCallback 인터페이스를 구현하고
// MapFragment 객체에서 getMapAsync(OnMapReadyCallback)를 통해 콜백 인스턴스를 설정해야 한다.

// 문의사항
// https://jizard.tistory.com/170
// https://ghj1001020.tistory.com/750
public class QUESTION extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // SupportMapFragment는 Fragment의 서브 클래스로, Fragment에 지도를 배치할 수 있도록 해준다.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // 이메일 버튼을 누르면 이메일 쓰기 화면에 들어갈 수 있다.
        Button writeemail = (Button ) findViewById(R.id.writeemail);
        writeemail.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        sendEmail("global@naver.com");

                    }
                }
        );

        // 문자 버튼을 눌러 문자쓰기를 할 수 있다.
        Button writemessage = (Button ) findViewById(R.id.writemessage);
        writemessage.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Go_Message("010-1234-5678");

                    }
                }
        );

        // 전화 버튼을 눌러 전화를 할 수 있다.
        Button call = (Button ) findViewById(R.id.call);
        call.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Go_Call();

                    }
                }
        );

        // BACK 버튼을 누르면 계정 화면에 들어갈 수 있다.
        ImageButton questionbackIcon = (ImageButton ) findViewById(R.id.back_icon);
        questionbackIcon.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), SETTING.class);
                        Toast.makeText(QUESTION.this, "계정 화면", Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                    }
                }
        );

    }

    // OnMapReady 는 map이 사용가능하면 호출되는 콜백 메소드입니다
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // LatLng : 위도와 경도로 좌표를 생성
        LatLng office = new LatLng(37.4856445, 126.972076);

        // MarkerOptions 구글 맵에 표시할 마커에 대한 옵션 설정
        // https://mailmail.tistory.com/19
        MarkerOptions markerOptions = new MarkerOptions();
        //  Position 위치(필수)
        // 지도에서 마커의 위치에 대한 LatLng 값입니다. 이는 Marker 객체의 유일한 필수 속성입니다.
        markerOptions.position(office);
        markerOptions.title("< 오늘의 행복 > 사무실 ");
        markerOptions.snippet(" 팀노바 7사무실 맨 뒷자리");
        //.showInfoWindow(); 추가하면 화면에 마카가 뜸
        mMap.addMarker(markerOptions).showInfoWindow();

        // CameraUpdateFactory
        // movecamera : camera 좌표를 서울역 근처로 옮겨 봅니다
        mMap.moveCamera(CameraUpdateFactory.newLatLng(office));
        // animateCamera() 는 근거리에선 부드럽게 변경합니다
        //출처: https://bitsoul.tistory.com/145 [Happy Programmer~]
        // 15로 설정하면 적당하게 zoom 되는듯.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        // 마커클릭 이벤트 처리
        // GoogleMap 에 마커클릭 이벤트 설정 가능.
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // 마커 클릭시 호출되는 콜백 메서드
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    // 이메일 문의 가능
    public void sendEmail(String email){
        try{
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "< 오늘의 행복 > 문의하기");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "앱 버전 (AppVersion):" + "\n기기명 (Device):\n안드로이드 OS (Android OS):\n내용 (Content):\n");
            emailIntent.setType("plain/Text");
            startActivity(Intent.createChooser(emailIntent, "Email Choose"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    // 작성 중인 메시지 저장까지 가능 - 문자어플에 저장되어 있음
//    public void sendSmsIntent(String number){
//        try{
//            Uri smsUri = Uri.parse("sms:" + number);
//            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
//            sendIntent.putExtra("sms_body", "< 오늘의 행복 > 문의하기" + "\n기기명 (Device):\n안드로이드 OS (Android OS):\n내용 (Content):\n" );
//            startActivity(sendIntent);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    // 문자보내기 메쏘드
    private void Go_Message(final String number) {
        new AlertDialog.Builder(this)
                .setTitle("문자문의").setMessage("문의를 하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        try{
                            Uri smsUri = Uri.parse("sms:" + number);
                            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
                            sendIntent.putExtra("sms_body", "< 오늘의 행복 > 문의하기" + "\n기기명 (Device):\n안드로이드 OS (Android OS):\n내용 (Content):\n" );
                            startActivity(sendIntent);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();

    }

    // 전화걸기 메쏘드
    private void Go_Call() {
        new AlertDialog.Builder(this)
                .setTitle("전화문의").setMessage("문의를 하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:01012345678"));
                        //Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:12345"));
                        startActivity(intent);
                        // https://wowon.tistory.com/97

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();

    }

//    public void sendCallintent(String number){
//        try{
//            Uri callUri = Uri.parse(number);
//            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(String.valueOf(callUri)));
//            startActivity(callIntent);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        //                        Intent callnumber = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01077777777"));
////                        startActivity(callnumber);
//    }
}


//    Intent email = new Intent(Intent.ACTION_SEND);
//                        email.setType("plain/Text");
//                                email.putExtra(Intent.EXTRA_EMAIL, getString(R.string.email));
//                                email.putExtra(Intent.EXTRA_SUBJECT, "<" + getString(R.string.app_name) + " " + getString(R.string.report) + ">");
//                                email.putExtra(Intent.EXTRA_TEXT, "앱 버전 (AppVersion):" + "\n기기명 (Device):\n안드로이드 OS (Android OS):\n내용 (Content):\n");
//                                email.setType("message/rfc822");
//                                startActivity(email);


// OnCameraMoveListener : 카메라의 위치가 변경될때 발생
//
//OnCameraIdleListener : 지도의 중심 위치, 확대 수준 변경이 완료된 순간
//
//OnMapClickListener : 지도의 특정 위치를 클릭
//
//OnMapLongClickListener : 지도의 특정 위치를 오랫동안 클릭
