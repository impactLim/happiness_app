package com.example.happiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.TreeSet;

public class myhappinessfeedActivity extends AppCompatActivity {

    //리사이클러뷰 및 어댑터 생성
    RecyclerView mRecyclerView = null ;
    RecyclerImageTextAdapter mAdapter = null ;
    ArrayList<RecyclerItem> mList ;

    LinearLayoutManager mLinearLayoutManager;

    private BackPressCloseHandler backPressCloseHandler;

    Uri selectedImageUri;
    Uri editImageUri;

    SharedPreferences testUserInfo;
    String thisUserinfo;

    //    Activity(WriteActivity)를 실행하기에 앞서, Activity 실행 요청을 식별하기 위한 요청 코드(requestCode)를 정의
    private static final int REQ_ADD_CONTENTS = 1;
    private static final int REQ_EDIT_CONTENTS = 2;

    TextView tv_nickname;
    TextView tv_happykeyword;
    String wordM;
//    Date : 현재시간 출력하기
//    출처: https://dhparkdh.tistory.com/105 [DHpark]

    // write 화면에서 받아온 값 split한 값들
    String receive_title;
    String receive_contents;
    String receive_imageuri;
    String receive_location;
    String receive_address;
    String receive_latitude;
    String receive_longitude;


    EditText editsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhappinessfeed);

        //recyclerview 참조
        mRecyclerView = findViewById(R.id.recycler1);
        Log.v("mainfeed oncreat 알림","recyclerview 생성");

        // linearlayoutmanager 참조
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager( mLinearLayoutManager);
        Log.v("main oncreat 알림","recyclerview layoutmanager");

        //arraylist 생성
        mList = new ArrayList<>();
        Log.v("main oncreat 알림","arraylist 생성");


        // 리사이클러뷰에 RecyclerImageTextAdapter 객체 지정.
        mAdapter = new RecyclerImageTextAdapter(mList,this) ;
        Log.v("mainfeed oncreat  알림","adapter 생성");


        mRecyclerView.setAdapter(mAdapter) ;
        Log.v("mainfeed oncreat 알림","recyclerview에 adapter 끼워줌");

        // 리사이클러뷰에 LinearLayoutManager 지정. (vertical)
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
        Log.v("mainfeed oncreat 알림","layoutmanager 지정");


        // 리사이클러뷰 구분선 주기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        Log.v("mainfeed oncreat 알림","recyclerview layoutmanager support");


//        //검색아이콘 누르면 내피드에서 검색하는 화면으로 넘어가기
//        ImageButton myfeed_search = (ImageButton) findViewById(R.id.search_icon);
//        myfeed_search.setOnClickListener(new Button.OnClickListener(){
//
//            public void onClick(View v){
//                Intent intent=(new Intent(getApplicationContext(),myfeed_searchActivity.class));
//
//                Log.v("mainfeed oncreat 알림","myfeed_search imagebuton");
//
//                startActivity(intent);
//
//            }
//
//        });

        //account 아이콘 누르면 내피드에서 계정페이지 화면으로 넘어가기
        ImageButton goAccountPage = (ImageButton) findViewById(R.id.account_icon);
        goAccountPage.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v){
                Intent intent=(new Intent(getApplicationContext(),ACCOUNTPAGE.class));

                Log.v("mainfeed oncreat 알림","myfeed_search imagebuton");

                startActivity(intent);

            }

        });

        //유튜브 아이콘 누르면 내피드에서 유튜브페이지로 넘어가기
        ImageButton goyoutube = findViewById(R.id.happy_youtube);
        goyoutube.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent=(new Intent(getApplicationContext(),happyyoutube.class));
                Log.v("mainfeed oncreat 알림","myfeed_goyoutube");
                startActivity(intent);
            }
        });

//        // 행복카테고리를 누르면 카테고리에 들어갈 수 있다.
//        Button join_categorybutton = (Button) findViewById(R.id.happiness_category);
//        join_categorybutton.setOnClickListener(
//                new Button.OnClickListener(){
//                    public void onClick(View v){
//
//                        Intent intent = new Intent(getApplicationContext(), HAPPINESSCATEGORY.class);
//
//                        startActivity(intent);
//
//                    }
//                }
//
//        );

        //글쓰기 아이콘 누르면 내 피드에 글쓰기 화면으로 넘어가기 // startActivityforresult
        ImageButton myfeed_write = (ImageButton) findViewById(R.id.write_icon);
        myfeed_write.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){

//               startactivityforresult로 데이터 넘길 때

                Intent i = new Intent(myhappinessfeedActivity.this, myhappinessfeed_writeActivity.class);
//                startActivityForResult(i, REQ_ADD_CONTENTS);
                startActivity(i);

                Log.v("mainfeed oncreat 알림","글쓰기 아이콘 누르면 글쓰기 화면으로 이동" + i + REQ_ADD_CONTENTS);
            }
        });

        // 로그인할 때 저장해준 아이디를 가지고 있는 쉐어드 파일을 불러온다.
        SharedPreferences userid = getSharedPreferences("useridFile", MODE_PRIVATE);
        // 로그인할 때 저장해준 벨류값을 가지고 있는 key를 useridString에 넣어줘서 이 key로 언제든지 회원정보에 접근할 수 있게 한다.
        String useridString = userid.getString("userid","");
        Log.v("mainfeed oncreat 알림"," 로그인할 때 저장해준 해당 아이디 확인 : " + useridString);

        // 저장된 회원정보를 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences testUserInfo = getSharedPreferences("testuserinfo",0);

        // 해당 아이디(KEY)로 해당 유저에 대한 정보를 가져올 수 있다.
        // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key) - useridString와 value 값을 불러와준다.
        String thisUserinfo =  testUserInfo.getString(useridString, "");
        Log.v("mainfeed oncreat 알림"," 로그인할 때 저장해준 아이디로 해당 정보에 다가갈 수 있도록 알림 : " + thisUserinfo);

        // 이름, 닉네임, 아이디, 패스워드 순으로 이루어진 thisUserInfo의 두 번째인 닉네임 값을 가지고 오기 위해서
        // split으로 [1]이란 배열에 들어가 있는 index 2번째의 값을 가져오겠다.
        String user_nickname = thisUserinfo.split(",")[1];

        // 가입했을 때 닉네임 값을 불러와 "nickname"의 행복피드로 설정해줌
        tv_nickname = (TextView) findViewById(R.id.nickname_happyfeed) ;
        tv_nickname.setText(user_nickname + "의 데일리행복");

        // 키를 가져와주기 위해 id_check에 아이디를 담아줌
        String id_check = thisUserinfo.split(",")[2];
        Log.v("mainfeed oncreat 알림"," id_check 키 값 가져왔는지 확인 : " + id_check);

        if(testUserInfo.contains(id_check)) {

            // write화면에서 글쓴 값 불러와주기 위해 해당 값이 담겨있는 shared 파일 불러옴
            SharedPreferences UserUploadContentsPreferences = getSharedPreferences("UserUploadContentsPreferences", 0);

            // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key)와 value 값을 불러와준다.
            String receive_write_info = UserUploadContentsPreferences.getString(id_check, "");
            Log.v("mainfeed oncreat 알림", " write페이지에서 제목, 내용, 이미지 가져왔는지 확인 알림 : " + receive_write_info);

            String[] splitReceiveInfo = receive_write_info.split("&");
            Log.v("mainfeed oncreat 알림", " write페이지에서 제목, 내용, 이미지 split 확인 : " + splitReceiveInfo);

                if(receive_write_info != ""){ // receive_write_info가 "" 아니어야 receive_contents와 receive_imageuri 가능

                    for (int i = 0; i < splitReceiveInfo.length; i++) {

                        Log.v("mainfeed split 알림", "" + splitReceiveInfo[i]);
                        // 제목, 내용, 이미지 순으로 이루어진 receive_write_info 각각의 값들을 가져와준다.
                        // String receive_title, String receive_contents, String receive_imageuri

                        receive_title = splitReceiveInfo[i].split(",")[0];
                        Log.v("mainfeed oncreat 알림", " write페이지에서 제목 split 확인 : " + receive_title);

                        receive_contents = splitReceiveInfo[i].split(",")[1];
                        Log.v("mainfeed oncreat 알림", " write페이지에서 내용 split 확인 : " + receive_contents);

                        receive_imageuri = splitReceiveInfo[i].split(",")[2];
                        Log.v("mainfeed oncreat 알림", " write페이지에서 이미지 split 확인 : " + receive_imageuri);

                        receive_location = splitReceiveInfo[i].split(",")[3];
                        Log.v("mainfeed oncreat 알림", "write페이지에서 location slit 확인: " + receive_location);

                        receive_address = splitReceiveInfo[i].split(",")[4];
                        Log.v("mainfeed oncreat 알림", "write페이지에서 receive_address 확인 : " + receive_address);

                        receive_latitude = splitReceiveInfo[i].split(",")[5];
                        Log.v("mainfeed oncreat 알림", "write페이지에서 receive_latitude 확인 : " + receive_latitude);

                        receive_longitude = splitReceiveInfo[i].split(",")[6];
                        Log.v("mainfeed oncreat 알림", "write페이지에서receive_longitude 확인 : " + receive_longitude);

                        Log.v("mainfeed split 알림 1 ", "receive_title : " + receive_title + " receive_contents : " + receive_contents + " receive_imageuri : " + receive_imageuri + " receive_location : " + receive_location);
                        Log.v("mainfeed split 알림 2 ", "receive_address : " + receive_address + " receive_latitude : " + receive_latitude + " receive_longitude : " + receive_longitude );

                        // uri 받아왔다.
                        if (selectedImageUri != null) {
                            selectedImageUri = Uri.parse(receive_imageuri);
                        }

                        // Log.v("mainfeed oncreat 알림", " write페이지에서 제목, 내용, 이미지 각각의 내용들 확인 : receive_title : " + receive_title + " receive_contents : " + receive_contents + " receive_imageuri : " + receive_imageuri);
                        // write 창에서 받아오는 값이 null이 아닐 경우, 그리고 split한 값들이 다 null이 아닐 경우에만 데이터 쌓이도록.
                        if (receive_write_info != null && receive_title != null & receive_contents != null && receive_imageuri != null && receive_location != null && receive_address != null && receive_latitude != null && receive_longitude != null) {

                            // recycleritem 생성자를 사용하여 arraylist에 들어갈 데이터를 만들어 준다.
                            RecyclerItem ri = new RecyclerItem(receive_title, receive_contents, receive_imageuri,receive_location, receive_address, receive_latitude, receive_longitude);

                            //데이터를 삽입한다.
                            mList.add(0, ri); //첫 줄에 삽입

                            // 어댑터에서 RecyclerView에 데이터를 반영하도록 합니다.
                            mAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영

                        }

                    }
                }
        }

        //
//            // 제목, 내용, 이미지 순으로 이루어진 receive_write_info 각각의 값들을 가져와준다.
//            // String receive_title, String receive_contents, String receive_imageuri
//            receive_title = receive_write_info.split(",")[0];
//            receive_contents = receive_write_info.split(",")[1];
//            receive_imageuri = receive_write_info.split(",")[2];
//
//            // uri 받아왔다.
//            if (selectedImageUri != null) {
//                selectedImageUri = Uri.parse(receive_imageuri);
//            }
//
//            Log.v("mainfeed oncreat 알림", " write페이지에서 제목, 내용, 이미지 각각의 내용들 확인 : receive_title : " + receive_title + " receive_contents : " + receive_contents + " receive_imageuri : " + receive_imageuri);
//            // write 창에서 받아오는 값이 null이 아닐 경우, 그리고 split한 값들이 다 null이 아닐 경우에만 데이터 쌓이도록.
//            if(receive_write_info != null && receive_title != null & receive_contents != null && receive_imageuri != null){
//
//                // recycleritem 생성자를 사용하여 arraylist에 들어갈 데이터를 만들어 준다.
//                RecyclerItem ri = new RecyclerItem(receive_title, receive_contents, receive_imageuri);
//
//                //데이터를 삽입한다.
//                mList.add(0, ri); //첫 줄에 삽입
//
//                // 어댑터에서 RecyclerView에 데이터를 반영하도록 합니다.
//                mAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영
//
//            }

//                // Name 값을 String 타입 그대로 표시.
//                String title = data.getStringExtra("title");
//                Log.v("mainfeed onactivityresult 알림", "title 값 getstringextra " + title);
//                // title을 받아왔다.
//
//                String contents = data.getStringExtra("contents");
//                Log.v("mainfeed onactivityresult 알림", "contents 값 getstringextra " + contents);
//                // contents 받아왔다.
//
//                String uri = data.getStringExtra("uri");
//                // uri 받아왔다.
//                if(selectedImageUri != null){
//                    selectedImageUri = Uri.parse(uri );
//                }
//
//                // recycleritem 생성자를 사용하여 arraylist에 들어갈 데이터를 만들어 준다.
//                RecyclerItem ri = new RecyclerItem(title, contents, uri);
//
//                Log.v("mainfeed onactivityresult 알림", "ri 확인" + ri + title + contents);
//
//                //데이터를 삽입한다.
//                mList.add(0, ri); //첫 줄에 삽입
//                //mList.add(ri); //마지막 줄에 삽입
//
//                Log.v("mainfeed onactivityresult 알림", "arraylist 추가 확인" + mList + ri + title + contents);
//
//                // 어댑터에서 RecyclerView에 데이터를 반영하도록 합니다.
//                mAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영
//                Log.v("mainfeed onactivityresult 알림", "adapter에서 recyclerview 반영" + mAdapter + mList);
//
//                Log.i("mainfeed onactivityresult 알림", data.getStringExtra("title"));
//                Log.i("mainfeed onactivityresult 알림", data.getStringExtra("contents"));

        // 뒤로 가기 누르면 "뒤로 버튼을 한 번 더 누르면 종료되도록."
        backPressCloseHandler = new BackPressCloseHandler(this);
        //출처: https://dsnight.tistory.com/14 [Development Assemble]
        Log.v(" 뒤로 버튼을 한 번 더 누르면 종료되도록 ", " " );

//        editsearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String text = editsearch.getText().toString()
//                        .toLowerCase(Locale.getDefault());
//                mAdapter.filter(text);
//
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    // 뒤로 가기 누르면 "뒤로 버튼을 한 번 더 누르면 종료되도록."
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
        Log.v(" 뒤로 버튼을 한 번 더 누르면 종료되도록 ", " " );

    }

}


//      get extra하기

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
////        requestCode는 feedActivity에서 writeActivity를 구별하기위해 사용하는부분
////        resultCode는 writeActivity에서 어떠한 결과코드를 주었는지에 대한 부분이고
////        Intent data에는 writeActivity에서 보낸 결과 데이터가 들어있는 부분입니다.
//
//        Log.d("onActivityResult 알림", "requestCode : "+requestCode+" resultCode : "+resultCode);
//
//        if (requestCode == REQ_ADD_CONTENTS) {
//            if (resultCode == Activity.RESULT_OK) {
//
//                // Name 값을 String 타입 그대로 표시.
//                String title = data.getStringExtra("title");
//                Log.v("mainfeed onactivityresult 알림", "title 값 getstringextra " + title);
//                // title을 받아왔다.
//
//                String contents = data.getStringExtra("contents");
//                Log.v("mainfeed onactivityresult 알림", "contents 값 getstringextra " + contents);
//                // contents 받아왔다.
//
//                String uri = data.getStringExtra("uri");
//                // uri 받아왔다.
//                if(selectedImageUri != null){
//                    selectedImageUri = Uri.parse(uri );
//                }
//
//                // recycleritem 생성자를 사용하여 arraylist에 들어갈 데이터를 만들어 준다.
//                RecyclerItem ri = new RecyclerItem(title, contents, uri);
//
//                Log.v("mainfeed onactivityresult 알림", "ri 확인" + ri + title + contents);
//
//                //데이터를 삽입한다.
//                mList.add(0, ri); //첫 줄에 삽입
//                //mList.add(ri); //마지막 줄에 삽입
//
//                Log.v("mainfeed onactivityresult 알림", "arraylist 추가 확인" + mList + ri + title + contents);
//
//                // 어댑터에서 RecyclerView에 데이터를 반영하도록 합니다.
//                mAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영
//                Log.v("mainfeed onactivityresult 알림", "adapter에서 recyclerview 반영" + mAdapter + mList);
//
//                Log.i("mainfeed onactivityresult 알림", data.getStringExtra("title"));
//                Log.i("mainfeed onactivityresult 알림", data.getStringExtra("contents"));
//
//            } else {
//
//                Toast.makeText(myhappinessfeedActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//
//            }
//
//        }
//
//        else{
//            if (resultCode == Activity.RESULT_OK) {
//
//            // Name 값을 String 타입 그대로 표시.
//            // edit 화면으로부터 "edit_title" 키에 해당한 입력값을 Edit_title로 받아준다.
//            String Edit_title = data.getStringExtra("edit_title") ;
//            Log.v("mainfeed onactivityresult 2 알림","title 값 getstringextra " + Edit_title);
//            // title을 받아왔다.
//
//            // edit 화면으로부터 "edit_contents" 키에 해당한 입력값을 Edit_contents로 받아준다.
//            String Edit_contents = data.getStringExtra("edit_contents") ;
//            Log.v("mainfeed onactivityresult 2 알림","contents 값 getstringextra " + Edit_contents);
//            // contents 받아왔다.
//
//            String Edit_image = data.getStringExtra("edit_image");
//            editImageUri = Uri.parse(Edit_image);
//
//            // position에 title_position2로 getintextra한 position을 넣어준다.
//            int position = data.getIntExtra("title_position2", 0);
//
//            // recycleritem 데이터클래스 생성자로 Edit_title과 Edit_contents를 받아준다.
//            // recycleritem 데이터클래스를 보면 String 값을 두 개 넣어주고 반환해주기 때문이다.
//            RecyclerItem ri = new RecyclerItem(Edit_title, Edit_contents, Edit_image);
//
//            // 수정할 때 해당 position 값을 넣어주고, ri를 입력해준다.
//            mList.set(position, ri);
//
//            // position 값
//            Log.v("mainfeed onactivityresult 2 position 알림","positioin 알림" + position );
//            Toast.makeText(myhappinessfeedActivity.this, " 위치값 " + position, Toast.LENGTH_SHORT).show();
//
//            // 어댑터에서 RecyclerView에 데이터를 반영하도록 합니다.
//            mAdapter.notifyDataSetChanged();
//
//            //변경된 데이터를 화면에 반영
//            Log.v("mainfeed onactivityresult 2 알림", "adapter에서 recyclerview 반영" + mAdapter + mList);
//            Log.i("mainfeed onactivityresult 2 알림", data.getStringExtra("edit_title"));
//            Log.i("mainfeed onactivityresult 2 알림", data.getStringExtra("edit_contents"));
//            Log.i("mainfeed onactivityresult 2 알림", String.valueOf(data.getIntExtra("title_position2", 0)));
//
//            }else{
//
//               Toast.makeText(myhappinessfeedActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }









//            // splitreceiveinfo로 제일 많이 입력된 키워드 설정
//            // https://supplementary.tistory.com/80
//            int result = 0;
//            int sum = 0;
//            // String wordM
//            wordM = null;
//
//            String[] word = receive_write_info.split("\\s");
//
//            ArrayList listSame = new ArrayList();
//            TreeSet listEquals = new TreeSet();
//
//            for(String e : word) {
//
//                listSame.add(e);
//                listEquals.add(e);
//
//            }
//
//            Collections.sort(listSame);
//            Log.v("알림","키워드" + listSame);
//            Log.v("알림 2","키워드" + listEquals);
//
//            for(Object e : listEquals) {
//
//                for(int i=0; i<listSame.size(); i++) {
//
//                    if(e.equals(listSame.get(i))) {
//                        sum++;
//                        if(result<sum) {
//                            result = sum;
//                            wordM = (String)e;
//                        }
//                    }else {
//                        if(result < sum) {
//                            result = sum;
//                            wordM = (String)e;
//                        }
//                        sum = 0;
//                    }
//
//                }
//
//            }
//
////            Log.v("알림 3","키워드" + String.format("%s's count is %s",wordM, result) + " wordM " + wordM + " result :  " + result);
////            Log.v("알림 3","키워드  " + String.format("나의 행복키워드 : %s",wordM));
//
//            tv_happykeyword = (TextView) findViewById(R.id.happy_keyword) ;
//
//            if(wordM == null){
//                Log.v("알림 4","키워드 null 값" );
//            }else{
//                Log.v("알림 5","키워드 들어옴" + String.format("나의 행복키워드 : %s",wordM));
//                tv_happykeyword.setText(String.format("나의 행복키워드 : %s", wordM));
//            }