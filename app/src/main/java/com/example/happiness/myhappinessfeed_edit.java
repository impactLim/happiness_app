package com.example.happiness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;

public class myhappinessfeed_edit extends AppCompatActivity {

    EditText Edit_title;
    EditText Edit_contents;
    String Edit_location;
    ImageView Edit_image;

    String Edit_address;
    String Edit_latitude;
    String Edit_longitude;

    Button Edit_button;

    ArrayList<RecyclerItem> mList = new ArrayList<RecyclerItem>();

    private static final int GET_PICTURE_URI2 = 1;
    Uri EditImageUri;

    int position;

    SharedPreferences testUserInfo;
    String id_check;
    String receive_write_info;
    SharedPreferences.Editor useruploadContentsEditor;


    String editContentsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhappinessfeed_edit);

        //글쓰기 화면의 제목
        Edit_title = (EditText) findViewById(R.id.edit_title);

        //글쓰기 화면의 내용
        Edit_contents = (EditText) findViewById(R.id.edit_contents);

        // edit 화면이 oncreat 될 때,
        // "key-title"이라는 key로 title에 입력된 값을 edit 화면에 입력받게 해준다.
        Edit_title.setText(getIntent().getStringExtra("key_title"));

        // "key-contents"라는 key로 contents에 입력된 값을 edit 화면에 입력받게 해준다.
        Edit_contents.setText(getIntent().getStringExtra("key_contents"));

        Edit_image = findViewById(R.id.edit_image);
        // recyclerimagetextadapter 어댑터의 putextra key인 mainfeed_key_image 받음
        Edit_image.setImageURI(Uri.parse(getIntent().getStringExtra("mainfeed_key_image")));

        // "title_position"이라는 키도 edit액티비티가 oncreat될 때 int의 position값으로 전달받는다.
        // int position
        position = getIntent().getIntExtra("title_position", 0);

        //String Edit_location
        Edit_location = getIntent().getStringExtra("mainfeed_key_location");

        Edit_address = getIntent().getStringExtra("mainfeed_key_address");
        Edit_latitude = getIntent().getStringExtra("mainfeed_key_latitude");
        Edit_longitude =getIntent().getStringExtra("mainfeed_key_longitude");

        // 컨텐츠 수정 취소
        ImageButton cancelbutton = (ImageButton) findViewById(R.id.x_icon2);
        cancelbutton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), myhappinessfeedActivity.class);

                        startActivity(intent);

                    }
                }
        );

        // position 확인 로그
        Log.d("edit activity 알림","position 확인 "+ getIntent().getIntExtra("title_position", 0));

        // position 확인 토스트
//        Toast.makeText(myhappinessfeed_edit.this, "title_position getintextra 알림" + getIntent() + getIntent().getIntExtra("title_position", 0), Toast.LENGTH_SHORT).show();

        // 수정할 이미지 붙여넣는 작업 1
        findViewById(R.id.gallery_icon2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myhappinessfeed_edit.this, "갤러리 열기", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_PICTURE_URI2);
            }
        });

        //글쓰기 화면의 체크 버튼
        Edit_button = (Button) findViewById(R.id.edit_button);
        Log.v("myhappinessfeed_edit 알림", "Edit_title, Edit_contents 확인" + Edit_title + Edit_contents);

        Edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                // edit_title과 edit_contents 뷰를 사용할 수 있도록 edittextname과 editcontentsname라는 변수명을 써줌
//                EditText editTitleName = (EditText) findViewById(R.id.edit_title);
//                EditText editContentsName = (EditText) findViewById(R.id.edit_contents);
//
//                // edit_title이란 키에 editTitleName에 들어갈 입력값을 intent로 보내줄 준비를 한다.
//                intent.putExtra("edit_title", editTitleName.getText().toString());
//                Log.v("edit_title 알림","edit_title : " + editTitleName + editTitleName.getText().toString() );
//
//                // edit_contents라는 키에 editContentsName에 들어갈 입력값을 intent로 보내줄 준비를 한다.
//                intent.putExtra("edit_contents", editContentsName.getText().toString());
//                Log.v("edit_contents 알림","edit_contents : " + editContentsName + editContentsName.getText().toString() );
//
//                intent.putExtra("edit_image", EditImageUri.toString());
//
//                // title_position2라는 키에 위치값을 intent로 보내줄 준비를 한다.
//                intent.putExtra("title_position2", getIntent().getIntExtra("title_position", 0));
//                Log.v("title_position2 알림","title_position 2 : " + getIntent().getIntExtra("title_position", 0));
//
//                setResult(RESULT_OK, intent);
//
//                finish();

                // https://onepinetwopine.tistory.com/116 mcontext 참고함
                // 회원정보에 다가가기 위한 id키 값
                // 로그인할 때 저장해준 아이디를 가지고 있는 쉐어드 파일을 불러온다.
                SharedPreferences userid = getSharedPreferences("useridFile", MODE_PRIVATE);
                // 로그인할 때 저장해준 벨류값을 가지고 있는 key를 useridString에 넣어줘서 이 key로 언제든지 회원정보에 접근할 수 있게 한다.
                String useridString = userid.getString("userid","");
                Log.v("mainfeed edit에서 아이디 받아왔는지 확인 알림"," id_check 키 값 가져왔는지 확인 : " + useridString);

                // 위의 id키값으로 회원정보 불러와준다.
                // 저장된 회원정보를 불러오기 위해 같은 네임파일을 찾음.
                // SharedPreferences testUserInfo
                testUserInfo = getSharedPreferences("testuserinfo",0);

                // 해당 아이디(KEY)로 해당 유저에 대한 정보를 가져올 수 있다.
                // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key) - useridString와 value 값을 불러와준다.
                String thisUserinfo =  testUserInfo.getString(useridString, "");
                Log.v("mainfeed edit에서 회원정보 받아왔는지 확인 알림"," thisUserinfo 키 값 가져왔는지 확인 : " + thisUserinfo);

                // 이름, 닉네임, 아이디, 패스워드 순으로 이루어진 thisUserInfo의 0,1,2의 2에 해당하는 세번째 값을 가지고 오기 위해서
                // id_check에 아이디를 담아줌
                // String id_check
                id_check = thisUserinfo.split(",")[2];
                Log.v("mainfeed edit에서 아이디 받아왔는지 확인 알림 2 "," id_check 키 값 가져왔는지 확인 : " + id_check);
                // write화면에서 글쓴 값들 불러와주기 위해 해당 값이 담겨있는 shared 파일 불러옴 >> 근데 그러려면 키가 있어야 하는데 키가 아이디다. 해당 키를 어떻게 불러와줄 수 있을까

                SharedPreferences UserUploadContentsPreferences = getSharedPreferences("UserUploadContentsPreferences", 0);

                // editor라는게 필요해서 만들어줬다.
                // SharedPreferences.Editor useruploadContentsEditor
                useruploadContentsEditor = UserUploadContentsPreferences.edit();

                // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key)와 value 값을 불러와준다.
                // String receive_write_info
                receive_write_info = UserUploadContentsPreferences.getString(id_check, "");
                Log.v("mainfeed edit에서 알림", " 메인피드 edit 창에서 제목, 내용, 이미지 가져왔는지 확인 알림 : " + receive_write_info);

                // https://onepinetwopine.tistory.com/116 참고
                String[] splitReceiveInfo = receive_write_info.split("&");
                Log.v("mainfeed edit에서 알림", " mainfeed edit에서 받아온 제목, 내용, 이미지 등의 긴 데이터들을 split 하겠다 : " + splitReceiveInfo);
                // 리사이클러뷰 맨위에 쌓인 게시물의 position이 0, 근데 이 position값에 해당하는 split index는 맨 아래에 있는 값임
                // 그래서 이걸 splitReceiveInfo.length-1-position 통해 거꾸로 해줘야함.
                Log.v("mainfeed edit에서 알림", "" + splitReceiveInfo[splitReceiveInfo.length-1-position]);

                // 회원정보가 해당 아이디를 가지고 있다면,
                if(testUserInfo.contains(id_check)) {

                    // 이미지 수정하지 않을 가능성이 커서 editimaguri에 null 값을 할당
                    if(EditImageUri == null) {
                        // 삭제하려는 값을 삭제는 못하고 대신 & + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                        // String editContentsInfo;
                        editContentsInfo = receive_write_info.replace("&" + splitReceiveInfo[splitReceiveInfo.length - 1 - position],
                                "&" + Edit_title.getText().toString() + ","
                                        + Edit_contents.getText().toString() + ","
                                        + getIntent().getStringExtra("mainfeed_key_image") + "," // EditImageUri.toString() 대신에 기존에 있던 이미지 string값
                                        + Edit_location + ","
                                        + Edit_address + ","
                                        + Edit_latitude + ","
                                        + Edit_longitude);

                    // 이미지 수정했을 경우.
                    }else{

                        // 삭제하려는 값을 삭제는 못하고 대신 & + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                        // String editContentsInfo;
                        editContentsInfo = receive_write_info.replace("&" + splitReceiveInfo[splitReceiveInfo.length - 1 - position],
                                "&" + Edit_title.getText().toString() + ","
                                        + Edit_contents.getText().toString() + ","
                                        + EditImageUri.toString() + ","
                                        + Edit_location + ","
                                        + Edit_address + ","
                                        + Edit_latitude + ","
                                        + Edit_longitude);

                    }

                    Log.v("mainfeed edit에서 게시물 수정 확인 알림", "" + editContentsInfo);

                    // 기존 value인 first_contents에 추가되는 값들(sb_contentsinfo.toString())을 더해준다.
                    useruploadContentsEditor.putString(id_check, editContentsInfo);

                    // 파일에 최종 반영함
                    useruploadContentsEditor.commit();

                }

                Intent intent = new Intent(getApplicationContext(), myhappinessfeedActivity.class);

                //intent라는 데이터 통로를 만들어줬다.
                startActivity(intent);

            }
        });
    }

    // 수정할 이미지 붙여넣는 작업 2
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

        if (requestCode == GET_PICTURE_URI2 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            ImageView editimageview = (ImageView) findViewById(R.id.edit_image);
            EditImageUri = data.getData();
            editimageview.setImageURI(EditImageUri);

        }
    }
}
