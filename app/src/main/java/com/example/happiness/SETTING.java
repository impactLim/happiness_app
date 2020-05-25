package com.example.happiness;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class SETTING extends AppCompatActivity {

    String receive_write_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // BACK 버튼을 누르면 계정 화면에 들어갈 수 있다.
        ImageButton settingbackIcon = (ImageButton ) findViewById(R.id.Setting_back_icon);
        settingbackIcon.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), ACCOUNTPAGE.class);
                        Toast.makeText(SETTING.this, "계정 화면", Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                    }
                }
        );


        // 프로필 편집 버튼을 누르면 프로필 편집 화면에서 프로필과 행복에 대한 정의을 편집할 수 있다.
        Button goeditprofile = (Button ) findViewById(R.id.edit_profile);
        goeditprofile.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), PROFILEedit.class);
                        Toast.makeText(SETTING.this, "프로필 편집 화면", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();

                    }
                }
        );

        // 로그아웃 클릭하면 제일 첫 화면(로그인, 회원가입 둘 중 하나 고르는 화면)으로 이동하는 코드
        Button btn_logout = (Button) findViewById(R.id.logout_button);
        btn_logout.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                    // 로그아웃하기
                    Go_logout();

                    }
                }

        );

        // 회원탈퇴 클릭하면 제일 첫 화면(로그인, 회원가입 둘 중 하나 고르는 화면)으로 이동하는 코드
        Button btn_deleteAccount = (Button) findViewById(R.id.delete_account);
        btn_deleteAccount.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        // 회원탈퇴하기
                        Go_deleteaccount();

                    }
                }
        );

        // 문의사항 클릭하면 문의하기 화면으로 들어감
        Button btn_ask = (Button) findViewById(R.id.ask);
        btn_ask.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), QUESTION.class);
                        Toast.makeText(SETTING.this, "문의하기", Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                    }
                }
        );
    }

    // 로그아웃 메쏘드
    private void Go_logout() {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(SETTING.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();

    }

    // 회원탈퇴 메쏘드
    private void Go_deleteaccount() {
        new AlertDialog.Builder(this)
                .setTitle("회원탈퇴").setMessage("회원탈퇴 하시겠습니까?")
                .setPositiveButton("회원탈퇴", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        // 로그인할 때 저장해준 아이디를 벨류값으로 가지고 있는 쉐어드 파일을 불러온다.
                        SharedPreferences userid = getSharedPreferences("useridFile", MODE_PRIVATE);
                        SharedPreferences.Editor userIDeditor = userid.edit();

                        // 로그인할 때 저장해준 벨류값 - 진짜 ID를 가지고 있는 key를 useridString에 넣어줘서 이 key(useridString)로 언제든지 회원정보에 접근할 수 있게 한다.
                        String useridString = userid.getString("userid","");
                        Log.v("SETTING 설정 페이지 useridString 벨류 값 확인 알림"," useridString : " + useridString);
                        // 저장된 회원정보를 불러오기 위해 같은 네임파일을 찾음.
                        SharedPreferences testUserInfo = getSharedPreferences("testuserinfo",0);
                        SharedPreferences.Editor testuserEditor = testUserInfo.edit();

                        // useridString에 담겨진 아이디(KEY)로 해당 유저에 대한 정보를 가져올 수 있다.
                        // String deleteUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key)인 useridString와 value 값을 불러와준다.
                        String deleteUserinfo =  testUserInfo.getString(useridString, "");
                        Log.v("SETTING 설정 페이지 deleteUserinfo 벨류 값 불러오기 확인 알림"," deleteUserinfo : " + deleteUserinfo);

                        // 그리고 이 데이터를 삭제하고 끝낸다. http://jo.centis1504.net/?p=1627
                        testuserEditor.remove(useridString).commit(); // remove 안에 키값을 넣어줘야 하는데 계속 string 값을 넣어줬음 ;;
                        // 밑에꺼 하면 다 삭제됨
//                        testuserEditor.clear();
//                        testuserEditor.commit();
                        Log.v("SETTING 설정 페이지 deleteUserinfo 벨류 값 삭제 확인 알림 2 "," deleteUserinfo : " + deleteUserinfo);

                        // 로그인할 때 저장해줬던 아이디 key value도 삭제해준다.
                        userIDeditor.remove("userid").commit();
//                        userIDeditor.commit();

                        // 화면 setFlags , https://stickyny.tistory.com/109
                        Intent i = new Intent(SETTING.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();
    }


}
//    public void Go_logout(View v) {
//
//    }

