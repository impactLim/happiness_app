package com.example.happiness;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText UserIdEmail;
    EditText UserPassword;

    CheckBox checkSaveId;

    SharedPreferences testUserInfo;
    String thisUserinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        UserIdEmail =(EditText) findViewById(R.id.useridemail);
        UserPassword = (EditText) findViewById(R.id.userpassword);
        checkSaveId = (CheckBox) findViewById(R.id.saveID);

        // oncreat - 아이디저장 체크박스 작업
        SharedPreferences saveidFile = getSharedPreferences("saveidfile",0) ;

        // onstop했을 때 저장했던 입력값과 체크박스 상태여부를 불러온다.
        String saveid = saveidFile.getString("id_save","");
        Boolean checksaveidbox = saveidFile.getBoolean("saveIDcheckbox",false);

        // 체크박스에 체크가 되어 있었다면, UserEmail이란 EditText에 onstop 될 때 저장되었던 값(입력값)을 oncreat 될 때 꺼내서 set 해준다.
        // 체크박스에 체크가 되어 있었다면, checkSaveId란 체크박스에 onstop 될 때 저장되었던 값(체크 or 공란)을 불러와준다. 디폴트 값은 false = 공란이다.
        if(checksaveidbox == true){
            UserIdEmail.setText(saveid);
            checkSaveId.setChecked(checksaveidbox);

            Log.v("아이디저장 체크박스 불러오기 확인 알림"," " + saveid + " " +  checksaveidbox);
        }

        //회원가입 시 저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        testUserInfo = getSharedPreferences("testuserinfo",0);

        // 로그인박스클릭하면 회원가입 시 저장했던 값을 불러온 값과 내가 입력한 이메일과 pw를 비교 후 값이 일치하면 로그인, 그렇지 않으면 다시 입력하세요. // 닉네임값 전달하는 코드
        findViewById(R.id.login_box).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                // 내가 아이디에 입력하는 입력값이 shared의 key값으로 준 아이디가 있는지 일치여부 확인
                if(testUserInfo.contains(UserIdEmail.getText().toString())){

                    // 일치하는 아이디가 있다면 해당 아이디(KEY)로 해당 유저에 대한 정보를 가져올 수 있다.
                    // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key)와 value 값을 불러와준다.
                    thisUserinfo =  testUserInfo.getString(UserIdEmail.getText().toString(), "");
                    // 이름, 닉네임, 아이디, 패스워드 순으로 이루어진 thisUserInfo의 네 번째 패스워드 값을 가지고 오기 위해서
                    // split으로 [3]이란 배열에 들어가 있는 index 4번째의 값을 가져오겠다.
                    String pw = thisUserinfo.split(",")[3];
                    String nickname = thisUserinfo.split(",")[1];

                    if(pw.equals(UserPassword.getText().toString())){

                        // 시간이 걸리는 작업을 하기 때문에 Thread 나 AsyncTask 클래스에 포함되어서 사용됩니다.
                        // AsyncTask 는 Thread 를 사용할 일이 있을 때 좀더 편리하게 이용할수 있도록 안드로이드에서 지원하고 있는 클래스
                        // 메인피드 들어갔을 때 로그인 중입니다 뜨도록.
                        CheckTypesTask task = new CheckTypesTask();
                        // execute()명령어를 통해 asynctask을 실행
                        task.execute();

                        // 해당 id를 담고 있는 걸 벨류값으로 주는 쉐어드 하나 더 만들어준다.
                        SharedPreferences userid = getSharedPreferences("useridFile", MODE_PRIVATE);
                        SharedPreferences.Editor userIDeditor = userid.edit();
                        userIDeditor.putString("userid", UserIdEmail.getText().toString()); // userid 라는 key값으로 로그인 후 가지고 놀 진짜 ID 데이터를 저장한다.
                        userIDeditor.commit(); //완료한다.

//                        출처: https://humble.tistory.com/9 [진규의 Playground]

                    }else{
                        Toast.makeText(LoginActivity.this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                        Log.v("loginactivity 비밀번호를 다시 입력해주세요 알림", " 확인 ");
                    }

                }else{
                    Toast.makeText(LoginActivity.this, "E-mail을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    Log.v("loginactivity 이메일을 다시 입력해주세요 알림", " 확인 ");
                }

            }
        });

        // 회원가입클릭하면 회원가입 화면으로 이동하는 코드
        Button btn_makeaccount = (Button) findViewById(R.id.make_account);
        btn_makeaccount.setOnClickListener(
                new OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), joinActivity.class);
                        startActivity(intent);

                    }
                }

        );

//        // 비밀번호 찾기 클릭하면 비밀번호 찾기 화면으로 이동하는 코드
//
//        Button btn_findpw = (Button) findViewById(R.id.find_pw);
//        btn_findpw.setOnClickListener(
//                new OnClickListener(){
//                    public void onClick(View v){
//
//                        Intent intent = new Intent(getApplicationContext(), findpw01Activity.class);
//
//                        startActivity(intent);
//
//                    }
//                }
//
//        );

    }

    @Override
    protected void onResume() {
        super.onResume();

//        SharedPreferences testUserPref = getSharedPreferences("testuserpref",0);
//        String testreceiveuserinfo = testUserPref.getString("test_user","");
//        Log.v("로그인화면에서 testreceiveuserinfo 불러오기 알림 ",  "  testreceiveuserinfo : " +  testreceiveuserinfo);

    }

    // http://blog.naver.com/PostView.nhn?blogId=2hyoin&logNo=220632209661&parentCategoryNo=126&categoryNo=&viewDate=&isShowPopularPosts=true&from=search
    // https://blog.naver.com/parkshyuk/220269866666  // https://codeman77.tistory.com/29
    // 화면에서 나갔을 때 아이디입력칸에 입력받고 있는 값과 아이디저장 체크박스에 체크되어있는지를 저장하겠다.

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("알림","login 생명주기 onstop!");
//        Toast.makeText(getApplicationContext(), "login 생명주기 onstop!", Toast.LENGTH_SHORT).show();

        //회원가입 시 저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        testUserInfo = getSharedPreferences("testuserinfo",0);

        // 내가 아이디에 입력하는 입력값이 shared의 key값으로 준 아이디가 있는지 일치여부 확인 후 있을 시 - 로그인되었을 때만 아이디저장 되도록.
        if(testUserInfo.contains(UserIdEmail.getText().toString())){

            // onStop
            // 아이디저장 체크박스 작업
            SharedPreferences saveidFile = getSharedPreferences("saveidfile",0);
            SharedPreferences.Editor saveidEditor = saveidFile.edit();

            // UserIdEmail 이란 edittext에 입력받은 값을 onstop할 때 저장한다, checkSaveId라는 체크박스에 체크되어 있는지 여부를 저장한다.
            saveidEditor.putString("id_save", UserIdEmail.getText().toString());
            saveidEditor.putBoolean("saveIDcheckbox",checkSaveId.isChecked());

            Log.v("Login 아이디저장 체크박스 확인 알림","  " + UserIdEmail.getText().toString() + "  " + checkSaveId.isChecked() );

            saveidEditor.commit();

        }else{

        }

    }

    //    출처: https://mainia.tistory.com/2031 [녹두장군 - 상상을 현실로]
//    AsyncTask를 new 하면 나오는 인자 3개에 대해서
//    1 ) Params : Background 작업을 할 때 필요한 데이터 타입을 정의한다.
//    2 ) Progress : Background 작업 도중 진행하는데 사용 될 데이터 타입 정의
//    3 ) Result : 작업 결과로서 리턴 받을 데이터 타입을 정의한다.
//    출처: https://pnot.tistory.com/3 [프로그래밍 노트]
    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(
                LoginActivity.this);


        // 작업시작, ProgressDialog 객체를 생성하고 시작합니다.
        // asynctask로 백그라운드 작업을 실행하기 전에 onpreexcuted() 실행됨. 이 부분에는 이미지 로딩작업이라면 로딩 중 이미지를 띄워 놓기 등, 스레드 작업 이전 수행동작 구현
        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로그인 중입니다..");

            Log.v("로그인 중입니다. 다이어로그 메시지 settext 알림 ", " " + asyncDialog);

            // show dialog
            asyncDialog.show();
            Log.v("로그인 중입니다. 다이어로그 메시지 show 알림 ", " ");
            // doInBackground가 실행되기 전에 실행된다.
            super.onPreExecute();
        }

        // 진행중, ProgressDialog 의 진행 정도를 표현해 줍니다.
        // 중간 중간 진행 상태를 UI에 업데이트 하도록.
        @Override
        protected Void doInBackground(Void... arg0) {
            // 파라미터에는 AsyncTask의 Param(첫번째)의 영향을 받는다.
            // 사용자가 진행할 주요 작업을 수행하는 구간

            try {
                for (int i = 0; i < 4; i++) {
                    //asyncDialog.setProgress(i * 30);
                    Thread.sleep(500);
                    Log.v("로그인 중입니다. thread.sleep(500) 알림 - 3초 간 로그인 중 ", " ");

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.v("로그인 중입니다. 예외처리 알림 ", " ");

            }
            return null; // 리턴값은 AsyncTask의 Result(세번째)의 영향을 받는다.
        }

        // 종료, ProgressDialog 종료 기능을 구현합니다.
        // 리턴값을 통해 스레드 작업이 끝났을 때의 동작 구현
        @Override
        protected void onPostExecute(Void result) {
            // doInBackground가 작업을 마친 후 리턴된 값을 받으며 후속 작업을 한다.

            asyncDialog.dismiss();
            Log.v("로그인 중입니다. 다이어로그 dismiss 알림 ", " ");

            // 다이어로그 사라지고 나서 화면전환될 수 있도록.
            Intent intent = new Intent(LoginActivity.this, myhappinessfeedActivity.class);
            startActivity(intent);
            finish();
            Log.v("loginactivity 로그인되었습니다 알림", " 확인 ");

            super.onPostExecute(result);
        }
    }

}

//                if (UserEmail.getText().toString().equals(loginIdEmail) && UserPassword.getText().toString().equals(loginPw)) {
//
//                    Toast.makeText(LoginActivity.this, nickName +" 님 환영합니다.", Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(LoginActivity.this, myhappinessfeedActivity.class);
//                    startActivity(intent);
//                    finish();
//
//                }else{
//
//                    Toast.makeText(LoginActivity.this, "E-mail과 PW를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
//                    Log.v("loginactivity 다시 입력해주세요 알림", " 확인 ");
//
//                }





//        findViewById(R.id.login_box).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(getApplication(), myhappinessfeedActivity.class);
////                intent.putExtra("Key","세훈이");
////                Toast.makeText(LoginActivity.this, "세훈이 로그인", Toast.LENGTH_SHORT).show();
//
//                startActivity(intent);
//
//
//            }
//        });

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.v("알림","login 생명주기 onstart!");
//        Toast.makeText(getApplicationContext(), "login 생명주기 onstart!", Toast.LENGTH_SHORT).show();
//
//    }
//
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.v("알림","login 생명주기 onresume!");
//        Toast.makeText(getApplicationContext(), "login 생명주기 onresume!", Toast.LENGTH_SHORT).show();
//
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.v("알림","login 생명주기 ondestroy!");
//        Toast.makeText(getApplicationContext(), "login 생명주기 ondestroy!", Toast.LENGTH_SHORT).show();
//
//    }

    // 이 화면으로 onrestart 되었을 때 아이디와 비밀번호 값 초기화하기!!
    //edittext 사용법 다시 숙지하기!!
    //username과 pw를 onrestart 안에 해주면 oncreat는 사용 못함. >> 생각은 하고 있기

//    @Override
//    protected void onRestart() {
//        super.onRestart();
////        Log.v("알림","login 생명주기 onrestart!");
////        Toast.makeText(getApplicationContext(), "login 생명주기 onrestart!", Toast.LENGTH_SHORT).show();
//
//
//        EditText username = (EditText) findViewById(R.id.username) ;
//        EditText password = (EditText) findViewById(R.id.password) ;
//
//        username.setText(null);
//        password.setText(null);
//
//    }



//        try {
//                // receivejsonArray // jsonarray() 안에 intent.getStringExtra(receiveUserInfo)
//                JSONArray receivejsonArray = new JSONArray(receiveUserInfo);
//
//                // JSONObject를 담아야 되는데 안에 JSONObject가 몇개 올지 모르니(회원가입을 몇 명이나 할지 모르니) 반복문을 사용해준다.
//                // 내가 가입하기를 하는 만큼 저장이 된다.
//                for (int i = 0; i < receivejsonArray.length(); i++) {
//        // 그 다음 JSONArray에서 JSONObject를 추출한다.
//        JSONObject receivejsonObject = receivejsonArray.getJSONObject(i);
//
//        // 추출이 완료되면 "name"이란 Key값으로 uesrinfo에 접근해서 해당 key값에 대한 Value값(jsonObject.getString("name"))을 구한 후 List에 그 값을 차곡차곡 담아준다.
//        receivenameList.add(receivejsonObject.getString("name"));
//        Log.v("receivenamelist 확인 알림", " receivenameList : " + receivenameList + " name이란 키로 받아온 값 확인 : " + receivejsonObject.getString("name"));
//
//        receivenicknameList.add(receivejsonObject.getString("nickname"));
//        Log.v("receivenicknameList 확인 알림", " receivenicknameList : " + receivenicknameList + " nickname이란 키로 받아온 값 확인 : " + receivejsonObject.getString("nickname"));
//
//        receiveidList.add(receivejsonObject.getString("id"));
//        Log.v("receiveidList 확인 알림", " receiveidList : " + receiveidList + " id란 키로 받아온 값 확인 : " + receivejsonObject.getString("id"));
//
//        receivepwList.add(receivejsonObject.getString("pw"));
//        Log.v("receivepwList 확인 알림", " receivepwList : " + receivepwList + " pw란 키로 받아온 값 확인 : " + receivejsonObject.getString("pw"));
//
//        receivepwcheckList.add(receivejsonObject.getString("pwcheck"));
//        Log.v("receivepwcheckList 확인 알림", " receivepwcheckList : " + receivepwcheckList + " pwcheck란 키로 받아온 값 확인 : " + receivejsonObject.getString("pwcheck"));
//
//        }
//
//        } catch (JSONException e) {
//        e.printStackTrace();
//        }
//
//        Log.v("회원가입 receiveUserInfo 입력 및 저장 알림 2 ",  "  receiveUserInfo : " +  receiveUserInfo);

//        //회원가입 시 저장된 값을 불러오기 위해 같은 네임파일을 찾음.
//        final SharedPreferences newUserPreferences = getSharedPreferences("newuserpreferences",0);
//
//        // "new_idemail"이란 key값으로 회원가입한 ID(email) 받아오기 - 이 key 값에 new_name 키와 함께 입력된 값이 불러져서 온다.
//        loginIdEmail = newUserPreferences.getString("new_idemail", "");
//        newUserPreferences.
//        Log.v("loginIdEmail 확인 알림", " loginIdEmail : " + loginIdEmail);
//
//        // "new_pw"이란 key값으로 회원가입할 때 저장한 pw 받아오기 - 이 key 값에 new_pw 키와 함께 입력된 값이 불러져서 온다.
//        loginPw = newUserPreferences.getString("new_pw", "");
//        Log.v("loginIdPw 확인 알림", " loginIdPw : " + loginPw);
//
//        nickName = newUserPreferences.getString("new_nickname","");
//        Log.v("loginnickName 확인 알림", " nickName: " + nickName);