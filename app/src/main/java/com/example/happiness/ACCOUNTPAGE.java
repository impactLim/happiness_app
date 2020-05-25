package com.example.happiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ACCOUNTPAGE extends AppCompatActivity {


    TextView account_nickname;
    TextView account_happycontents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountpage);

        //back 아이콘 누르면 계정페이지 화면에서 나만의 행복피드로 넘어가기
        ImageButton back = (ImageButton) findViewById(R.id.back_icon);
        back.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v){

                Intent intent=(new Intent(getApplicationContext(),myhappinessfeedActivity.class));

                startActivity(intent);

            }

        });

        Button btm_listenmusic = (Button) findViewById(R.id.golistenmusicpage);
        btm_listenmusic.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), listenmusic.class);
                        startActivity(intent);

                    }
                }
        );

        // 설정 버튼을 누르면 설정 화면에 들어갈 수 있다.
        ImageButton goSettingIcon = (ImageButton ) findViewById(R.id.goSettingIcon);
        goSettingIcon.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), SETTING.class);
                        Toast.makeText(ACCOUNTPAGE.this, "설정 화면", Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                    }
                }

        );


        // 알람설정하기
        Button gosetAlarm = (Button) findViewById(R.id.notice_happiness);
        gosetAlarm.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), happynotificationpage.class);

                        startActivity(intent);

                    }
                }

        );

        // 커뮤니티 가기
        Button goMyhappinessCommunity = (Button) findViewById(R.id.gohappinesscommunity);
        goMyhappinessCommunity.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), MYHAPPINESSCOMMUNITY.class);

                        startActivity(intent);

                    }
                }

        );

        // 로그인할 때 저장해준 아이디를 가지고 있는 쉐어드 파일을 불러온다.
        SharedPreferences userid = getSharedPreferences("useridFile", MODE_PRIVATE);
        // 로그인할 때 저장해준 벨류값을 가지고 있는 key를 useridString에 넣어줘서 이 key로 언제든지 회원정보에 접근할 수 있게 한다.
        String useridString = userid.getString("userid","");

        // 저장된 회원정보를 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences testUserInfo = getSharedPreferences("testuserinfo",0);

        // 해당 아이디(KEY)로 해당 유저에 대한 정보를 가져올 수 있다.
        // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key)와 value 값을 불러와준다.
        String thisUserinfo =  testUserInfo.getString(useridString, "");

        // 이름, 닉네임, 아이디, 패스워드 순으로 이루어진 thisUserInfo의 두 번째인 닉네임 값을 가지고 오기 위해서
        // split으로 [1]이란 배열에 들어가 있는 index 2번째의 값을 가져오겠다.
        String account_receive_nickname = thisUserinfo.split(",")[1];

        // 가입했을 때 닉네임 값을 불러와 "nickname"의 행복피드로 설정해줌
        account_nickname = (TextView) findViewById(R.id.mypage_nickname2) ;
        account_nickname.setText(account_receive_nickname);
        Log.v("accountpage_nickName 확인 알림", " 계정화면에 뜬 nickName: " + account_receive_nickname);


        // 프로필 수정창에서 "내가 생각하는 행복은?"에 대한 내용을 썼던 값을 불러와주기 위해 같은 네임파일을 찾음.
        SharedPreferences UserThinkHappyPreferences = getSharedPreferences("UserThinkHappyPreferences",0);

        // xml파일에 있는 myhappiness_contents를 accountpage 클래스에서 사용하기 위해 account_happycontents라는 textview 변수명을 설정.
        account_happycontents = (TextView) findViewById(R.id.myhappiness_contents) ;

        // account_receive_happycontents라는 String 값에 HappyContents라는 키에 담긴 값을 가져와준다.
        String account_receive_happycontents = UserThinkHappyPreferences.getString(useridString,"");
        Log.v("account_receive_happycontents 확인 알림", " 계정화면에 뜬 happy contents : " + account_receive_happycontents);

        // 그 string 값을 textview에 고정시켜준다.
        account_happycontents.setText(account_receive_happycontents);

    }
}

//        // 순간의 행복저장소를 누르면 이미지 피드에 들어갈 수 있다.
//        Button testPhoto = (Button) findViewById(R.id.test_photo);
//        testPhoto.setOnClickListener(
//                new Button.OnClickListener(){
//                    public void onClick(View v){
//
//                        Intent intent = new Intent(getApplicationContext(), HAPPINESSIMAGEFEED.class);
//                        Toast.makeText(ACCOUNTPAGE.this, "imagefeed 입장", Toast.LENGTH_SHORT).show();
//                        startActivity(intent);
//
//                    }
//                }
//
//        );

