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

public class PROFILEedit extends AppCompatActivity {

    EditText Nickname_Edit;
    EditText HappyContents_Edit;

    String profileedit_receive_id;
    String profileedit_receive_nickname;
    String profileedit_receive_name;
    String profileedit_receive_pw;

    SharedPreferences userid;
    SharedPreferences testUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileedit);

        // BACK 버튼을 누르면 설정(SETTING) 화면에 들어갈 수 있다.
        ImageButton profileeditbackIcon = (ImageButton ) findViewById(R.id.profile_edit_back_icon);
        profileeditbackIcon.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), SETTING.class);
                        Toast.makeText(PROFILEedit.this, "설정 화면", Toast.LENGTH_SHORT).show();
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
        // String profileedit_receive_nickname
        profileedit_receive_nickname = thisUserinfo.split(",")[1];

        // 가입했을 때 닉네임 값을 불러와 편집창에 입력받게 해준다.
        Nickname_Edit = (EditText) findViewById(R.id.nickname_edit);
        Nickname_Edit.setText(profileedit_receive_nickname);
        Log.v("Profileedit_nickName 확인 알림", " 프로필 닉네임 수정란(회원가입 시 등록했었던)에 입력되어 있는 nickName : " + profileedit_receive_nickname);

        // split으로 [0]이란 배열에 들어가 있는 이름 index 0번째의 값을 가져오겠다.
        // String profileedit_receive_name
        profileedit_receive_name = thisUserinfo.split(",")[0];

        // split으로 [2]이란 배열에 들어가 있는 index 2번째의 값을 가져오겠다.
        // String profileedit_receive_id
        profileedit_receive_id = thisUserinfo.split(",")[2];

        // split으로 [3]이란 배열에 들어가 있는 index 2번째의 값을 가져오겠다.
        // String profiledit_receive_pw
        profileedit_receive_pw = thisUserinfo.split(",")[3];


                // 프로필 - 내가 생각하는 행복은 ? 불러와주기
                // 프로필 수정창에서 "내가 생각하는 행복은?"에 대한 내용을 썼던 값을 불러와주기 위해 같은 네임파일을 찾음.
                SharedPreferences UserThinkHappyPreferences = getSharedPreferences("UserThinkHappyPreferences",0);

                // xml파일에 있는 whatisyourhappiness_edit이란 Edittext를 PROFILEedit 클래스에서 사용하기 위해 HappyContents_Edit이라는 Edittext 변수명을 설정.
                HappyContents_Edit = (EditText) findViewById(R.id.whatisyourhappiness_edit) ;

                // profileedit_receive_happycontents라는 String 값에 profileedit_receive_id라는 키에 담긴 value값을 가져와준다.
                String profileedit_receive_happycontents = UserThinkHappyPreferences.getString(profileedit_receive_id,"");
                Log.v("profileedit_receive_happycontents 확인 알림", " PROFILEedit화면 happy contents : " + profileedit_receive_happycontents);

                // 그 string 값을 textview에 고정시켜준다.
                HappyContents_Edit.setText(profileedit_receive_happycontents);


        // check 버튼을 누르면 ACCOUNTPAGE 계정창으로 넘어감과 동시에 프로필(닉네임 + 내가 생각하는 행복)의 값이 수정되어야 한다.
        // 특히 accountpage의 닉네임, mainfeed의 닉네임 + 행복피드 의 닉네임 값이다.
        ImageButton profileedit_btn = (ImageButton ) findViewById(R.id.profile_edit_button);
        profileedit_btn.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), ACCOUNTPAGE.class);
                        startActivity(intent);

                        // 회원가입 시 저장했던 닉네임값이 있는 sharedpreferences를 가져온다.
                        //예제출처: https://bitsoul.tistory.com/120 // mode_private 설명 출처 : http://www.fun25.co.kr/blog/android-sharedpreferences-example // https://copycoding.tistory.com/90
                        SharedPreferences testUserInfo = getSharedPreferences("testuserinfo",0);

                        // 변경한 값을 저장하기 위해 editor가 필요하다?
                        SharedPreferences.Editor testuserEditor = testUserInfo.edit();

                        // 내가 Nickname_Edit에 입력한 값
                        String editNickName = Nickname_Edit.getText().toString();

                        // 기존 회원가입했던 데이터에 닉네임만 수정해주고 다시 저장시켜줌.
                        String UserInfoString2 = profileedit_receive_name + ","
                                + editNickName + ","
                                + profileedit_receive_id  + ","
                                + profileedit_receive_pw ;

                        Log.v("프로필 화면 editNickName 수정 및 저장 알림",  " profileedit_receive_name : " +  profileedit_receive_name + " editNickName : " + editNickName + " profileedit_receive_id : " + profileedit_receive_id + " profileedit_receive_pw : " +  profileedit_receive_pw);

                        // 회원가입 시 저장했던 아이디를 그대로 key 값으로 가져왔고 기존 회원정보에서 닉네임 값만 변경시켜주고 다시 저장하겠다.
                        testuserEditor.putString(profileedit_receive_id, UserInfoString2);
                        Log.v("프로필 화면 UserInfoString2 수정 및 저장 알림 ",  " UserInfoString2 : " +  UserInfoString2);

                        // 파일에 최종 반영함
                        testuserEditor.commit();

                        if(testUserInfo.contains(profileedit_receive_id)){

                            // 프로필 수정 화면의 "내가 생각하는 행복은?" edittext 입력란에 입력한 값이
                            // 체크 버튼을 눌렀을 때 저장되어서 accountpage의 textview에 불러와야함.
                            // 그러기 위해 shared 파일을 하나 만들어줬고
                            SharedPreferences UserThinkHappyPreferences = getSharedPreferences("UserThinkHappyPreferences",0);

                            // editor라는게 필요해서 만들어줬다.
                            SharedPreferences.Editor userHappyThinkEditor = UserThinkHappyPreferences.edit();

                            // 내가 어떤 EditText에 입력하는건지 xml파일을 참조해 profiledit 클래스에서 참조해주는거고
                            HappyContents_Edit = (EditText) findViewById(R.id.whatisyourhappiness_edit);

                            // 여기에 들어간 값은 editHappyContents라는 string값에 넣어진다.
                            String editHappyContents = HappyContents_Edit.getText().toString();

                            // "HappyContents"란 key 값을 가져왔고 vaule 값인 editHappyContents에 들어갈 값을 저장할 것이다.
                            userHappyThinkEditor.putString(profileedit_receive_id, editHappyContents);
                            Log.v("프로필 화면 editHappyContents 수정 및 저장 알림",  "  editHappyContents : " +  editHappyContents);

                            // 파일에 최종 반영함
                            userHappyThinkEditor.commit();

                            finish();
                        }

                    }
                }
        );

    }
}
