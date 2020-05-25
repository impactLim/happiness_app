package com.example.happiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class findpw01Activity extends AppCompatActivity {

    EditText UserName_findpw;
    EditText UserEmail_findpw;

    String findpw_name;
    String findpw_email;

    SharedPreferences testUserInfo;
    String thisUserinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw01);

        // 내 이름과 email 등의 입력값들 받는 곳.
        UserName_findpw =(EditText) findViewById(R.id.writeName_findpw);
        UserEmail_findpw = (EditText) findViewById(R.id.writeEmail_findpw);

        // 저장된 회원정보를 불러오기 위해 같은 네임파일을 찾음.
        // SharedPreferences testUserInfo
        testUserInfo = getSharedPreferences("testuserinfo",0);

//        //회원가입 시 저장된 값을 불러오기 위해 같은 네임파일을 찾음.
//        final SharedPreferences newUserPreferences = getSharedPreferences("newuserpreferences",0);
//
//        // "new_name"이란 key값으로 회원가입한 이름 받아오기 - 이 key 값에 new_name 키와 함께 입력된 값이 불러져서 온다.
//        findpw_name = newUserPreferences.getString("new_name", "");
//        Log.v("findpw_name 확인 알림", " findpw_name : " + findpw_name);
//
//        // "new_idemail"이란 key값으로 회원가입할 때 저장한 아이디(이메일) 받아오기 - 이 key 값에 new_idemail 키와 함께 입력된 값이 불러져서 온다.
//        findpw_email = newUserPreferences.getString("new_idemail", "");
//        Log.v("findpw_email 확인 알림", " findpw_email : " + findpw_email);

        // 확인버튼 클릭하면 회원가입 시 저장했던 값을 불러온 값과 내가 입력한 아이디와 이메일를 비교 후 값이 일치하면 2단계로 넘어감, 등록된 아이디와 이메일이 없습니다.

        Button btn_confirmbutton = (Button) findViewById(R.id.button);
        btn_confirmbutton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        // 내가 아이디에 입력하는 입력값이 shared의 key값으로 준 아이디가 있는지 일치여부 확인
                        if(testUserInfo.contains(UserEmail_findpw.getText().toString())){

                            // 일치하는 아이디가 있다면 해당 아이디(KEY)로 해당 유저에 대한 정보를 가져올 수 있다.
                            // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key)와 value 값을 불러와준다.
                            thisUserinfo =  testUserInfo.getString(UserEmail_findpw.getText().toString(), "");

                            // 이름, 닉네임, 아이디, 패스워드 순으로 이루어진 thisUserInfo의 네 번째 패스워드 값을 가지고 오기 위해서
                            // split으로 [3]이란 배열에 들어가 있는 index 4번째의 값을 가져오겠다.
                            String name = thisUserinfo.split(",")[0];

                            if(name.equals(UserName_findpw.getText().toString())){

                                Intent intent = new Intent(findpw01Activity.this, findpw02againActivity.class);
                                startActivity(intent);
                                finish();
                                Log.v("비밀번호 찾기 2단계로 넘어갑니다 알림", " 확인 ");

                                // 해당 id를 담고 있는 걸 벨류값으로 주는 쉐어드 하나 더 만들어준다.
                                SharedPreferences userid_findpw = getSharedPreferences("userid_findpwFile", MODE_PRIVATE);
                                SharedPreferences.Editor userIDeditor = userid_findpw.edit();
                                userIDeditor.putString("userid", UserEmail_findpw.getText().toString()); // userid 라는 key값으로 진짜 ID 데이터를 저장한다.
                                userIDeditor.commit(); //완료한다.

                                // 출처: https://humble.tistory.com/9 [진규의 Playground]

                            }else{
                                Toast.makeText(findpw01Activity.this, "이름을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                                Log.v("findpw01Activity 이름를 다시 입력해주세요 알림", " 확인 ");
                            }

                        }else{
                            Toast.makeText(findpw01Activity.this, "이름과 E-mail을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                            Log.v("findpw01Activity 이메일을 다시 입력해주세요 알림", " 확인 ");
                        }

                    }
                }
        );
    }
}

//                        if (UserName_findpw.getText().toString().equals(findpw_name) && UserEmail_findpw.getText().toString().equals(findpw_email)) {
//
//                            Toast.makeText(findpw01Activity.this, "비밀번호 2단계", Toast.LENGTH_SHORT).show();
//                            Log.v("findpw01Activity 이름과 이메일을 확인완료 알림", " 확인 ");
//
//                            Intent intent = new Intent(getApplicationContext(), findpw02againActivity.class);
//                            startActivity(intent);
//                            finish();
//
//                        }else{
//
//                            Toast.makeText(findpw01Activity.this, "이름과 이메일을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
//                            Log.v("findpw01Activity 이름과 이메일을 다시 확인해주세요 알림", " 확인 ");
//
//                        }