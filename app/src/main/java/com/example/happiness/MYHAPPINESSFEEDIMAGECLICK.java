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
import android.widget.Toast;

public class MYHAPPINESSFEEDIMAGECLICK extends AppCompatActivity {

    TextView receive_title;
    TextView receive_contents;
    ImageView receive_imageview;

    SharedPreferences testUserInfo;
    String id_check;

    SharedPreferences.Editor useruploadContentsEditor;
    String receive_write_info;

    int ReceivePosition;
    Uri ReceiveImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhappinessfeedimageclick);

        receive_title = (TextView) findViewById(R.id.rc_title) ;
        receive_contents = (TextView) findViewById(R.id.rc_contents) ;
        receive_imageview = (ImageView) findViewById(R.id.rc_image);

        ImageButton btn_xicon = (ImageButton) findViewById(R.id.x_icon);
        btn_xicon.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), myhappinessfeedActivity.class);
                        startActivity(intent);

                    }
                }
        );

        // 메인피드화면에서 position 값을 받아온다.
        ReceivePosition = getIntent().getIntExtra("contents_position", 0);
        Log.v("myhappinessfeedimageclick로 position 받아왔는지 확인 알림"," position 값 가져왔는지 확인 : " + ReceivePosition);

        // 회원정보에 다가가기 위한 id키 값
        // 로그인할 때 저장해준 아이디를 가지고 있는 쉐어드 파일을 불러온다.
        SharedPreferences userid = getSharedPreferences("useridFile", MODE_PRIVATE);

        // 로그인할 때 저장해준 벨류값을 가지고 있는 key를 useridString에 넣어줘서 이 key로 언제든지 회원정보에 접근할 수 있게 한다.
        String useridString = userid.getString("userid","");
        Log.v("myhappinessfeedimageclick로 아이디 받아왔는지 확인 알림"," id_check 키 값 가져왔는지 확인 : " + useridString);

        // 위의 id키값으로 회원정보 불러와준다.
        // 저장된 회원정보를 불러오기 위해 같은 네임파일을 찾음.
        // SharedPreferences testUserInfo
        testUserInfo = getSharedPreferences("testuserinfo",0);

        // 해당 아이디(KEY)로 해당 유저에 대한 정보를 가져올 수 있다.
        // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key) - useridString와 value 값을 불러와준다.
        String thisUserinfo =  testUserInfo.getString(useridString, "");
        Log.v("myhappinessfeedimageclick 회원정보 받아왔는지 확인 알림"," thisUserinfo 키 값 가져왔는지 확인 : " + thisUserinfo);

        // 이름, 닉네임, 아이디, 패스워드 순으로 이루어진 thisUserInfo의 0,1,2의 2에 해당하는 세번째 값을 가지고 오기 위해서
        // id_check에 아이디를 담아줌
        // String id_check
        id_check = thisUserinfo.split(",")[2];
        Log.v("myhappinessfeedimageclick로 아이디 받아왔는지 확인 알림 2 "," id_check 키 값 가져왔는지 확인 : " + id_check);
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
        Log.v("myhappinessfeedimageclick 알림", "" + splitReceiveInfo[splitReceiveInfo.length-1-ReceivePosition]);

        // 회원정보가 해당 아이디를 가지고 있다면,
        if(testUserInfo.contains(id_check)) {

            String Receive_Title = splitReceiveInfo[splitReceiveInfo.length-1-ReceivePosition].split(",")[0];
            Log.v("myhappinessfeedimageclick 알림", " write페이지에서 제목 split 확인 : " + Receive_Title);

            String Receive_Contents = splitReceiveInfo[splitReceiveInfo.length-1-ReceivePosition].split(",")[1];
            Log.v("myhappinessfeedimageclick 알림", " write페이지에서 내용 split 확인 : " + Receive_Contents);

            String Receive_Imageuri = splitReceiveInfo[splitReceiveInfo.length-1-ReceivePosition].split(",")[2];
            Log.v("myhappinessfeedimageclick 알림", " write페이지에서 이미지 split 확인 : " + Receive_Imageuri);

            Log.v("myhappinessfeedimageclick 알림", "Receive_Title : " + Receive_Title + " Receive_Contents : " + Receive_Contents + " Receive_Imageuri : " + Receive_Imageuri);

            // uri 받아왔다. 이 ReceiveImageUri는 항상 null. 이 null값에 Recieve_Imageuri라는 String값을 ReceiveImageUri에 넣어준다.
            if (ReceiveImageUri == null) {
                ReceiveImageUri = Uri.parse(Receive_Imageuri);
            }

            Log.v("myhappinessfeedimageclick 알림", "ReceiveImageUri : " + ReceiveImageUri);

            receive_title.setText(Receive_Title);
            receive_contents.setText(Receive_Contents);
            receive_imageview.setImageURI(ReceiveImageUri);

//            // 삭제하려는 값을 삭제는 못하고 대신 & + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
//            String setContentsInfo = receive_write_info.replace("&" + splitReceiveInfo[splitReceiveInfo.length-1-position],"");
//            Log.v("myhappinessfeedimageclick 알림", "" + setContentsInfo);

//            // 기존 value인 first_contents에 추가되는 값들(sb_contentsinfo.toString())을 더해준다.
//            useruploadContentsEditor.putString(id_check, deleteContentsInfo);
//
//            // 파일에 최종 반영함
//            useruploadContentsEditor.commit();

        }


    }
}
