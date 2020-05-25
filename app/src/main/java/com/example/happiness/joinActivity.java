package com.example.happiness;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class joinActivity extends AppCompatActivity {

//    EditText nickname_box;

    EditText Name;
    EditText Nickname;
    EditText Id_Email;
    EditText Pw;
    EditText CheckPw;

    StringBuffer sb_userinfo = new StringBuffer();

    ArrayList<String> sendnameList = new ArrayList<>();
    ArrayList<String> sendnicknameList = new ArrayList<>();
    ArrayList<String> sendidList = new ArrayList<>();
    ArrayList<String> sendpwList = new ArrayList<>();
    ArrayList<String> sendpwcheckList = new ArrayList<>();

    String UserInfoString;
    SharedPreferences testUserInfo;
    SharedPreferences.Editor testuserEditor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

//        nickname_box = (EditText)findViewById(R.id.nickname_box);
        Log.v("회원가입 화면 알림",  "  확인  " );

        Name = findViewById(R.id.name);
        Nickname = findViewById(R.id.nickname_box);
        Id_Email = findViewById(R.id.id_email);
        Pw = findViewById(R.id.pw);
        CheckPw = findViewById(R.id.checkpw);

        // testUserInfo 라는 객체를 만들어주고 해당 객체의 파일이름은 testuserinfo 다.
        testUserInfo = getSharedPreferences("testuserinfo",0);
        testuserEditor = testUserInfo.edit();


        Button btn_idcheck = findViewById(R.id.id_check);
        btn_idcheck.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

//                        if(!testUserInfo.contains("")){

                        // testUserinfo가 null이 아니라면
                        if(testUserInfo != null) {
                            // testUserinfo가 내가 아이디에 입력한 값을 가지고 있다면 이미 존재하는 아이디라고.
                            if (testUserInfo.contains(Id_Email.getText().toString())) {

                                Toast.makeText(joinActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                Log.v("회원가입 중복체크 확인 알림 ", " 중복체크 boolean :  " + testUserInfo.contains(Id_Email.getText().toString()) + " 입력값 확인 " + Id_Email.getText().toString());

                            // testUserinfo가 내가 아이디에 입력한 값을 가지고 있지 않다면 등록 가능하게.
                            }else{

                                Toast.makeText(joinActivity.this, "등록 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                                Log.v("회원가입 중복체크 확인 알림 ", " 중복체크 boolean 2:  " + testUserInfo.contains(Id_Email.getText().toString()) + " 입력값 확인 " + Id_Email.getText().toString());

                            }

                        // testuserinfo가 null이라면 등록가능한 아이디라고 체크
                        }else{
                            Toast.makeText(joinActivity.this, "등록 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
//                            Log.v("회원가입 중복체크 확인 알림 ", " 중복체크 boolean 3 :  " + testUserInfo.contains(Id_Email.getText().toString())+ " 입력값 확인 " + Id_Email.getText().toString());
                        }



                    }
                }
        );

        Button btn_joinbutton = (Button) findViewById(R.id.join_button);
        btn_joinbutton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        // testUserinfo가 null이 아니라면
                        if(testUserInfo != null) {

                            // testUserinfo가 내가 아이디에 입력한 값을 가지고 있다면 이미 존재하는 아이디라고.
                            if (testUserInfo.contains(Id_Email.getText().toString())) {

                                Toast.makeText(joinActivity.this, "이미 존재하는 아이디입니다. 중복체크를 해주세요.", Toast.LENGTH_SHORT).show();
                                Log.v("회원가입 중복체크 확인 알림 2 ", " 중복체크 boolean :  " + testUserInfo.contains(Id_Email.getText().toString()) + " 입력값 확인 " + Id_Email.getText().toString());

                                // testUserinfo가 내가 아이디에 입력한 값을 가지고 있지 않다면 등록 가능하게.
                            } else {

//                                Toast.makeText(joinActivity.this, "등록 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                                Log.v("회원가입 중복체크 확인 알림 2", " 중복체크 boolean 2:  " + testUserInfo.contains(Id_Email.getText().toString()) + " 입력값 확인 " + Id_Email.getText().toString());

                                // 내가 입력한 패스워드와 패드워드 체크한 것 비교.
                                if (Pw.getText().toString().equals(CheckPw.getText().toString())){

                                    // 내가 등록할 유저의 정보
                                    String UserInfoString = Name.getText().toString() + ","
                                            + Nickname.getText().toString() + ","
                                            + Id_Email.getText().toString() + ","
                                            + Pw.getText().toString();

                                    // "입력값"이란 key 값으로 UserInfoString에 들어간 = 내가 입력한 id, pw, 이름, 닉네임을 저장하겠다.
                                    testuserEditor.putString( Id_Email.getText().toString(), UserInfoString );

                                    Log.v("회원가입 UserInfoString 입력 및 저장 알림",  "  UserInfoString에 입력한 값들 >> " +  UserInfoString);
                                    Log.v("회원가입 UserInfoString 입력 및 저장 알림 2 ",  " 이름에 입력한 값 : " +  Name.getText().toString() + " 닉네임에 입력한 값 : " + Nickname.getText().toString());
                                    Log.v("회원가입 UserInfoString 입력 및 저장 알림 3 ",   " 아이디엡 입력한 값 " + Id_Email.getText().toString() + " pw에 입력한 값 :  " + Pw.getText().toString());

                                    testuserEditor.commit();

                                    Toast.makeText(joinActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                                    //  로그인 창으로 넘어간다.
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);

                                } else {

                                    Toast.makeText(joinActivity.this, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

                                }

                            }

                            // testuserinfo가 null이라면 등록가능한 아이디라고 체크
                        } else {
//                            Toast.makeText(joinActivity.this, "등록 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                            Log.v("회원가입 중복체크 확인 알림 3", " 중복체크 boolean 3 :  " + testUserInfo.contains(Id_Email.getText().toString())+ " 입력값 확인 " + Id_Email.getText().toString());

                            // 내가 입력한 패스워드와 패드워드 체크한 것 비교.
                            if (Pw.getText().toString().equals(CheckPw.getText().toString())){

                                // 내가 등록할 유저의 정보
                                String UserInfoString = Name.getText().toString() + ","
                                        + Nickname.getText().toString() + ","
                                        + Id_Email.getText().toString() + ","
                                        + Pw.getText().toString();

                                // "입력값"이란 key 값으로 UserInfoString에 들어간 = 내가 입력한 id, pw, 이름, 닉네임을 저장하겠다.
                                testuserEditor.putString(Id_Email.getText().toString(), UserInfoString);

                                Log.v("회원가입 UserInfoString 입력 및 저장 알림",  "  UserInfoString에 입력한 값들 >> " +  UserInfoString);
                                Log.v("회원가입 UserInfoString 입력 및 저장 알림 2 ",  " 이름에 입력한 값 : " +  Name.getText().toString() + " 닉네임에 입력한 값 : " + Nickname.getText().toString() + " 아이디엡 입력한 값 " + Id_Email.getText().toString() + " pw에 입력한 값 :  " + Pw.getText().toString());
                                Log.v("회원가입 UserInfoString 입력 및 저장 알림 3 ",   " 아이디엡 입력한 값 " + Id_Email.getText().toString() + " pw에 입력한 값 :  " + Pw.getText().toString());

                                testuserEditor.commit();

                                Toast.makeText(joinActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                                //  로그인 창으로 넘어간다.
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);

                            } else {

                                Toast.makeText(joinActivity.this, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

                            }

                        }

                    }
                }
        );




        Button btn_joincancel = (Button) findViewById(R.id.join_cancel);
        btn_joincancel.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        startActivity(intent);

                    }
                }
        );
    }

}


//    // json을 array 형태로 쓰려면 [, ] 로 처음과 끝에 감싸줘야 한다.
//    String startJson = "[";
//    String endJson ="]";
//
//// StringBuffer의 값이 없을때 즉 처음 추가할때는 ,(쉼표)를 안붙이고
//// 그 다음부터는 무조건 앞에 ,(쉼표)를 붙인다는 것을 써줬다.
//                        if(!sb_userinfo.toString().equals(""))
//                                {
//                                sb_userinfo.append(",");
//                                }
//
//                                String userinfotemp = "{\"name\""+":"+"\""+ Name.getText().toString()+"\""+ ","
//                                + "\"nickname\""+":" + "\"" + Nickname.getText().toString() + "\"" + ","
//                                + "\"id\""+":" + "\"" + Id_Email.getText().toString() + "\"" + ","
//                                + "\"pw\""+":" + "\"" + Pw.getText().toString() + "\"" + ","
//                                + "\"pwcheck\""+":" + "\"" + CheckPw.getText().toString() + "\"" +"}";
//
//                                sb_userinfo.append(userinfotemp);
//
//                                // userinfo라는 String 값에 [ + 내가 입력하는 값들(jsonobject) 계속 추가 + ] 을 담아준다.
//                                String userinfo = startJson + sb_userinfo + endJson;
//
//                                try
//                                {
//                                // 데이터의 가장 바깥이 [] 대괄호로 이루어져 있으므로 이 전체를 담기 위해 JSONArray 객체에 값을 담아줍니다
//                                JSONArray sendjsonArray = new JSONArray(userinfo);
//
//                                // JSONObject를 담아야 되는데 안에 JSONObject가 몇개 올지 모르니(회원가입을 몇 명이나 할지 모르니) 반복문을 사용해준다.
//                                // 내가 가입하기를 하는 만큼 저장이 된다.
//                                for (int i = 0; i < sendjsonArray.length(); i++)
//        {
//        // 그 다음 JSONArray에서 JSONObject를 추출한다.
//        JSONObject sendjsonObject = sendjsonArray.getJSONObject(i);
//
//        // 추출이 완료되면 "name"이란 Key값으로 uesrinfo에 접근해서 해당 key값에 대한 Value값(jsonObject.getString("name"))을 구한 후 List에 그 값을 차곡차곡 담아준다.
//        sendnameList.add(sendjsonObject.getString("name"));
//        Log.v(" sendnamelist 확인 알림","  sendnameList : " + sendnameList + " name이란 키로 받아온 값 확인 : " + sendjsonObject.getString("name"));
////                                Log.v("sendnameList size 확인 알림"," sendnameList.size() :  " + sendnameList.size() + " sendjsonArray : " + sendjsonArray + " sendjsonObject : " + sendjsonObject);
//
//        sendnicknameList.add(sendjsonObject.getString("nickname"));
//        Log.v(" sendnicknameList 확인 알림","  sendnicknameList : " + sendnicknameList + " nickname이란 키로 받아온 값 확인 : " + sendjsonObject.getString("nickname"));
//
//        sendidList.add(sendjsonObject.getString("id"));
//        Log.v(" sendidList 확인 알림","  sendidList : " + sendidList + " id란 키로 받아온 값 확인 : " + sendjsonObject.getString("id"));
//
//        sendpwList.add(sendjsonObject.getString("pw"));
//        Log.v(" sendpwList 확인 알림","  sendpwList : " + sendpwList + " pw란 키로 받아온 값 확인 : " + sendjsonObject.getString("pw"));
//
//        sendpwcheckList.add(sendjsonObject.getString("pwcheck"));
//        Log.v(" sendpwcheckList 확인 알림","  sendpwcheckList : " + sendpwcheckList + " pwcheck란 키로 받아온 값 확인 : " + sendjsonObject.getString("pwcheck"));
//
//        }
//
////                            tv_parsing.setText(""+nameList+"\n"+""+nicknameList + "\n" + "" + idList + "\n" + "" + pwList);
//
//        }
//        catch (JSONException e)
//        {
//        e.printStackTrace();
//        }
//
//        // newUserPreferences라는 객체를 만들어주고 해당 객체의 파일이름은 newuserpreferences이다.
//        SharedPreferences newUserPreferences = getSharedPreferences("newuserpreferences",0);
//        SharedPreferences.Editor newuserEditor = newUserPreferences.edit();
//
//        // "new_user"란 key 값으로 userinfo에 들어간 = 내가 입력한 값을 저장하겠다.
//        newuserEditor.putString("new_user", userinfo);
//        Log.v("회원가입 userinfo 입력 및 저장 알림",  "  userinfo : " +  userinfo);
//
//        newuserEditor.commit();
//
////                        // 값 불러와지는지 확인 테스트 로그
////                        String receiveUserInfo = newUserPreferences.getString("new_user","");
////                        Log.v("회원가입 RECEIVERuserinfo 입력 및 저장 불러져왔는지 확인 알림",  "  receiveUserInfo : " +  receiveUserInfo);




// 이거 아님
//                        String startsplit = "[";
//                        String endsplit ="]";

//                        if(!sb_userinfo.toString().equals(""))
//                        {
//                            sb_userinfo.append("/");
//                        }

//                        ArrayList mArrayList = new ArrayList<Integer>();
//
//                        // ArrayList 값 추가
//                        mArrayList.add(1);
//                        mArrayList.add(2);
//                        mArrayList.add(3);
//                        mArrayList.add(4);
//                        mArrayList.add(5);
//
//                        Log.v("arraylist 확인 알림 : ","" + mArrayList.size());
//
//                        // ArrayList 값 확인
//                        for(int i = 0; i < mArrayList.size(); i++) {
//
//                            Log.v("arraylist 확인 알림 : ","index " + i + " : value " + mArrayList.get(i));
//
//                        }
//
//                        testString = Name.getText().toString() + "~"
//                                + Nickname.getText().toString() + "!"
//                                + Id_Email.getText().toString() + "@"
//                                + Pw.getText().toString() + "#"
//                                + CheckPw.getText().toString() + "/";
//
//                        Log.v("splittext 알림 "," name : " + Name.getText().toString() + " nickname : " + Nickname.getText().toString() + " id : " + Id_Email.getText().toString() + " pw : " + Pw.getText().toString() + " pwcheck : " + CheckPw.getText().toString());
//
//                        ArrayList sendarray = new ArrayList<>();
//
//                        sendarray.add(testString);
//
//                        for (int i = 0; i < sendarray.size(); i++) {
//
//                            // testUserPref라는 객체를 만들어주고 해당 객체의 파일이름은 testuserpref이다.
//                            SharedPreferences testUserPref = getSharedPreferences("testuserpref",0);
//                            SharedPreferences.Editor testuserEditor = testUserPref.edit();
//
// "test_user"란 key 값으로 testString에 들어간 = 내가 입력한 값을 저장하겠다.
//                        testuserEditor.
//                            testuserEditor.putString("test_user" + i, testString);
//                            Log.v("회원가입 testString 입력 및 저장 알림",  "  testString에 입력한 값들 >> " +  testString);
//
//                            testuserEditor.commit();
//
//                        }


//                        sb_userinfo.append(testString);

//
//                        String userinfo = "" + sb_userinfo;

//                        "{\"name\""+":"+"\""+ Name.getText().toString()+"\""+ ","
//                                + "\"nickname\""+":" + "\"" + Nickname.getText().toString() + "\"" + ","
//                                + "\"id\""+":" + "\"" + Id_Email.getText().toString() + "\"" + ","
//                                + "\"pw\""+":" + "\"" + Pw.getText().toString() + "\"" + ","
//                                + "\"pwcheck\""+":" + "\"" + CheckPw.getText().toString() + "\"" +"}";

//                        String testString = "{name:" + Name.getText().toString() + "~"
//                                + "nickname:" + Nickname.getText().toString() + "!"
//                                + "id:" + Id_Email.getText().toString() + "@"
//                                + "pw:" + Pw.getText().toString() + "#"
//                                + "pwcheck:" + CheckPw.getText().toString() + "}";

//        String[] splitText = testString.split("\\,");
//        for(int i = 0 ; i < splitText.length ; i++){
//            Log.v("TEST 알림", "text = " + splitText[i]);
//        }
//        String name = testString.split(",")[0];
//        String nickname = testString.split(",")[1];
//        String id = testString.split(",")[2];
//        String pw = testString.split(",")[3];
//        String pwcheck = testString.split(",")[4];

//                                String userinfotemp = "{\"name\""+":"+"\""+ Name.getText().toString()+"\""+ ","
//                                + "\"nickname\""+":" + "\"" + Nickname.getText().toString() + "\"" + ","
//                                + "\"id\""+":" + "\"" + Id_Email.getText().toString() + "\"" + ","
//                                + "\"pw\""+":" + "\"" + Pw.getText().toString() + "\"" + ","
//                                + "\"pwcheck\""+":" + "\"" + CheckPw.getText().toString() + "\"" +"}";



//    @Override
//    public void onClick(View v) {
//        // TODO Auto-generated method stub
//        Intent intent = new Intent(this, LoginActivity.class); /*login activity를 myfeed_searchActivity로 해야 이 창이 뜨면서 바로 넘어가긴 하는데,*/
//        intent.putExtra("name",nickname_box.getText().toString());      /* 내가 원하는 건 login 들렸다가 메인 들렸다가 검색으로 가는거 */
//        startActivity(intent);
//        finish();
//    }


//    // sharedpreferences 얻기
//    // SharedPreferences 클래스에서 newUserPreferences라는 객체를 만들어줬다.
//    // 이 객체가 가지고 있는 파일 이름은 "newuserpreferences"다. // MODE_PRIVATE : 자기 app 내에서 사용할때, 기본값이며, 0 >
//    //예제출처: https://bitsoul.tistory.com/120 // mode_private 설명 출처 : http://www.fun25.co.kr/blog/android-sharedpreferences-example
//    SharedPreferences newUserPreferences = getSharedPreferences("newuserpreferences",0);
//
//    // 이 파일에 데이터를 기록하기 위해 이 editor 객체를 얻어야 한다.
//    // 저장하려면 editor가 필요하다.
//    SharedPreferences.Editor newuserEditor = newUserPreferences.edit();
//
//    // 내가  Name에 입력한 값
//    String writeName = Name.getText().toString();
//// "new_name"이란 key 값으로 writeName에 들어간 = 내가 입력한 값을 저장하겠다.
//                        newuserEditor.putString("new_name", writeName);
//                                Log.v("회원가입 writeName 입력 및 저장 알림",  "  writeName : " +  writeName);
//
//                                // 내가 Nickname에 입력한 값
//                                String writeNickName = Nickname.getText().toString();
//                                // "new_nickname"이란 key 값으로 writeNickName에 들어간 = 내가 입력한 값을 저장하겠다.
//                                newuserEditor.putString("new_nickname", writeNickName);
//                                Log.v("회원가입 writeNickName 입력 및 저장 알림",  "  writeNickName : " +  writeNickName);
//
//                                // 내가 Id_Email에 입력한 값
//                                String writeid_email = Id_Email.getText().toString();
//                                // "new_idemail"이란 key 값으로 writeid_email에 들어간 = 내가 입력한 값을 저장하겠다.
//                                newuserEditor.putString("new_idemail", writeid_email);
//                                Log.v("회원가입 writeid_email 입력 및 저장 알림",  "  writeid_email : " +  writeid_email);
//
//                                // 내가 Pw에 입력한 값
//                                String writePw = Pw.getText().toString();
//                                // "new_pw"이란 key 값으로 writePw에 들어간 = 내가 입력한 값을 저장하겠다.
//                                newuserEditor.putString("new_pw", writePw);
//                                Log.v("회원가입 pw 입력 알림",  "  writePw : " +  writePw);
//
//                                // 내가 Pw에 입력한 값
//                                String writePwcheck = CheckPw.getText().toString();
//
//                                // 파일에 최종 반영함
//                                newuserEditor.commit();
//
//                                if(writePw.equals(writePwcheck)){
//
//        Toast.makeText(joinActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
//
//        }else{
//
//        Toast.makeText(joinActivity.this, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
//
//        }