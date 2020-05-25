package com.example.happiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class locationclickpage extends AppCompatActivity implements OnMapReadyCallback {


    SharedPreferences testUserInfo;
    String id_check;

    SharedPreferences.Editor useruploadContentsEditor;
    String receive_write_info;

    int ReceivePosition;
    TextView receive_locationname;
    TextView receive_locationaddress;

    TextView think_location;

    private GoogleMap receiveclcickMap;
    LatLng HappyLocationclick;

    String Receive_latitude;
    String Receive_longitude;
    String Receive_location;
    String Receive_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationclickpage);


        // SupportMapFragment는 Fragment의 서브 클래스로, Fragment에 지도를 배치할 수 있도록 해준다.
        SupportMapFragment mapreceiveFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.locationclickmap);
        mapreceiveFragment.getMapAsync(this);

        receive_locationname = (TextView) findViewById(R.id.location_name);
        receive_locationaddress = (TextView) findViewById(R.id.location_address);

        ImageButton btn_backicon = (ImageButton) findViewById(R.id.location_back_icon);
        btn_backicon.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), myhappinessfeedActivity.class);
                        startActivity(intent);

                    }
                }
        );

        Button btn_seecontents= (Button) findViewById(R.id.seecontents);
        btn_seecontents.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), MYHAPPINESSFEEDIMAGECLICK.class);
                        startActivity(intent);

                    }
                }
        );

        // 메인피드화면에서 position 값을 받아온다.
        ReceivePosition = getIntent().getIntExtra("location_position", 0);
        Log.v("myhappinessfeedimageclick로 position 받아왔는지 확인 알림", " position 값 가져왔는지 확인 : " + ReceivePosition);

        // 회원정보에 다가가기 위한 id키 값
        // 로그인할 때 저장해준 아이디를 가지고 있는 쉐어드 파일을 불러온다.
        SharedPreferences userid = getSharedPreferences("useridFile", MODE_PRIVATE);

        // 로그인할 때 저장해준 벨류값을 가지고 있는 key를 useridString에 넣어줘서 이 key로 언제든지 회원정보에 접근할 수 있게 한다.
        String useridString = userid.getString("userid", "");
        Log.v("myhappinessfeedimageclick로 아이디 받아왔는지 확인 알림", " id_check 키 값 가져왔는지 확인 : " + useridString);

        // 위의 id키값으로 회원정보 불러와준다.
        // 저장된 회원정보를 불러오기 위해 같은 네임파일을 찾음.
        // SharedPreferences testUserInfo
        testUserInfo = getSharedPreferences("testuserinfo", 0);

        // 해당 아이디(KEY)로 해당 유저에 대한 정보를 가져올 수 있다.
        // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key) - useridString와 value 값을 불러와준다.
        String thisUserinfo = testUserInfo.getString(useridString, "");
        Log.v("myhappinessfeedimageclick 회원정보 받아왔는지 확인 알림", " thisUserinfo 키 값 가져왔는지 확인 : " + thisUserinfo);

        // 이름, 닉네임, 아이디, 패스워드 순으로 이루어진 thisUserInfo의 0,1,2의 2에 해당하는 세번째 값을 가지고 오기 위해서
        // id_check에 아이디를 담아줌
        // String id_check
        id_check = thisUserinfo.split(",")[2];
        Log.v("myhappinessfeedimageclick로 아이디 받아왔는지 확인 알림 2 ", " id_check 키 값 가져왔는지 확인 : " + id_check);
        // write화면에서 글쓴 값들 불러와주기 위해 해당 값이 담겨있는 shared 파일 불러옴 >> 근데 그러려면 키가 있어야 하는데 키가 아이디다. 해당 키를 어떻게 불러와줄 수 있을까

        SharedPreferences UserUploadContentsPreferences = getSharedPreferences("UserUploadContentsPreferences", 0);

        // editor라는게 필요해서 만들어줬다.
        // SharedPreferences.Editor useruploadContentsEditor
        useruploadContentsEditor = UserUploadContentsPreferences.edit();

        // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key)와 value 값을 불러와준다.
        // String receive_write_info
        receive_write_info = UserUploadContentsPreferences.getString(id_check, "");
        Log.v("myhappinessfeedimageclick 알림", " 메인피드 write 창에서 제목, 내용, 이미지 가져왔는지 확인 알림 : " + receive_write_info);

        // https://onepinetwopine.tistory.com/116 참고
        String[] splitReceiveInfo = receive_write_info.split("&");
        Log.v("myhappinessfeedimageclick 알림", " receive_write_info로 받아온 제목, 내용, 이미지 등의 긴 데이터들을 split 하겠다 : " + splitReceiveInfo);
        // 리사이클러뷰 맨위에 쌓인 게시물의 position이 0, 근데 이 position값에 해당하는 split index는 맨 아래에 있는 값임
        // 그래서 이걸 splitReceiveInfo.length-1-position 통해 거꾸로 해줘야함.
        Log.v("myhappinessfeedimageclick 알림", "" + splitReceiveInfo[splitReceiveInfo.length - 1 - ReceivePosition]);

        // 회원정보가 해당 아이디를 가지고 있다면,
        if (testUserInfo.contains(id_check)) {

            String Receive_Title = splitReceiveInfo[splitReceiveInfo.length-1-ReceivePosition].split(",")[0];
            Log.v("myhappinessfeedimageclick 알림", " write페이지에서 제목 split 확인 : " + Receive_Title);

            String Receive_Contents = splitReceiveInfo[splitReceiveInfo.length-1-ReceivePosition].split(",")[1];
            Log.v("myhappinessfeedimageclick 알림", " write페이지에서 내용 split 확인 : " + Receive_Contents);

            String Receive_Imageuri = splitReceiveInfo[splitReceiveInfo.length-1-ReceivePosition].split(",")[2];
            Log.v("myhappinessfeedimageclick 알림", " write페이지에서 이미지 split 확인 : " + Receive_Imageuri);

            // 위치 받아옴
            // String Receive_location
            Receive_location = splitReceiveInfo[splitReceiveInfo.length-1-ReceivePosition].split(",")[3];
            Log.v("myhappinessfeedimageclick 알림", " write페이지에서 이미지 split 확인 : " + Receive_location);

            // 주소 받아옴
            // String Receive_address
            Receive_address = splitReceiveInfo[splitReceiveInfo.length-1-ReceivePosition].split(",")[4];
            Log.v("myhappinessfeedimageclick 알림", " write페이지에서 이미지 split 확인 : " + Receive_address);

            // 위도 받아옴
            // String Receive_latitude
            Receive_latitude = splitReceiveInfo[splitReceiveInfo.length-1-ReceivePosition].split(",")[5];
            Log.v("myhappinessfeedimageclick 알림", " write페이지에서 이미지 split 확인 : " + Receive_latitude);
            // 경도받아옴
            // String Receive_longitude
            Receive_longitude = splitReceiveInfo[splitReceiveInfo.length-1-ReceivePosition].split(",")[6];
            Log.v("myhappinessfeedimageclick 알림", " write페이지에서 이미지 split 확인 : " + Receive_longitude);

            Log.v("myhappinessfeedimageclick 알림 1 ", "Receive_Title : " + Receive_Title + " Receive_Contents : " + Receive_Contents + " Receive_Imageuri : " + Receive_Imageuri);
            Log.v("myhappinessfeedimageclick 알림 2 ", "Receive_location : " + Receive_location + " Receive_address : " + Receive_address + " Receive_latitude : " + Receive_latitude + " Receive_longitude : " + Receive_longitude);

            receive_locationname.setText(Receive_location);
            receive_locationaddress.setText(Receive_address);

//            // 삭제하려는 값을 삭제는 못하고 대신 & + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
//            String setContentsInfo = receive_write_info.replace("&" + splitReceiveInfo[splitReceiveInfo.length-1-position],"");
//            Log.v("myhappinessfeedimageclick 알림", "" + setContentsInfo);

//            // 기존 value인 first_contents에 추가되는 값들(sb_contentsinfo.toString())을 더해준다.
//            useruploadContentsEditor.putString(id_check, deleteContentsInfo);
//
//            // 파일에 최종 반영함
//            useruploadContentsEditor.commit();

            // 구글맵 밑에 설명해줌
            think_location= (TextView) findViewById(R.id.location_explain) ;
            think_location.setText(Receive_location + "에서 무슨 일이 있으셨나요?");

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // 이 코드 없으면 receiveclickMap 널값 뜸.
        receiveclcickMap = googleMap;


        // 위도와 경도가 아닌 이상 happylocation이란 좌표 생성한다.
        if(Receive_latitude != null && Receive_longitude != null ) {

            // LatLng HappyLocation : 위도와 경도로 좌표를 생성
            HappyLocationclick = new LatLng(Double.parseDouble(Receive_latitude), Double.parseDouble(Receive_longitude));

            // MarkerOptions 구글 맵에 표시할 마커에 대한 옵션 설정
            // https://mailmail.tistory.com/19
            MarkerOptions markerOptions = new MarkerOptions();

            //  Position 위치(필수)
            // 지도에서 마커의 위치에 대한 LatLng 값입니다. 이는 Marker 객체의 유일한 필수 속성입니다.
            markerOptions.position(HappyLocationclick);
            markerOptions.title(Receive_location);
            markerOptions.snippet(Receive_address);
            //.showInfoWindow(); 추가하면 화면에 마카가 뜸
            receiveclcickMap.addMarker(markerOptions).showInfoWindow();

            // CameraUpdateFactory
            // movecamera : camera 좌표를 서울역 근처로 옮겨 봅니다
            receiveclcickMap.moveCamera(CameraUpdateFactory.newLatLng(HappyLocationclick));
            // animateCamera() 는 근거리에선 부드럽게 변경합니다
            //출처: https://bitsoul.tistory.com/145 [Happy Programmer~]
            // 15로 설정하면 적당하게 zoom 되는듯.
            receiveclcickMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        }
    }
}
