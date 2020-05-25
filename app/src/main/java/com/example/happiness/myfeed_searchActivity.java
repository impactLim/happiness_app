package com.example.happiness;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.os.Bundle;

import java.util.ArrayList;

public class myfeed_searchActivity extends AppCompatActivity  {

    //리사이클러뷰 및 어댑터 생성
    RecyclerView mmRecyclerView = null ;
    ExampleAdapter mmAdapter = null ;
    ArrayList<ExampleItem> mmList ;

    LinearLayoutManager mmLinearLayoutManager;

    Uri selectedImageUri;

    SharedPreferences testUserInfo;
    String thisUserinfo;

    // write 화면에서 받아온 값 split한 값들
    String receive_title;
    String receive_contents;
    String receive_imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfeed_search);

        //recyclerview 참조
        mmRecyclerView = findViewById(R.id.recycler2);
        Log.v("mainfeed search 알림","recyclerview 생성");

        // linearlayoutmanager 참조
        mmLinearLayoutManager = new LinearLayoutManager(this);
        mmRecyclerView.setLayoutManager(mmLinearLayoutManager);

        Log.v("mainfeed search 알림","recyclerview layoutmanager");

        //arraylist 생성
        mmList = new ArrayList<>();
        Log.v("mainfeed search 알림","arraylist 생성");


        // 리사이클러뷰에 RecyclerImageTextAdapter 객체 지정.
        mmAdapter = new ExampleAdapter(mmList,this) ;
        Log.v("mainfeed search  알림","adapter 생성");


        mmRecyclerView.setAdapter(mmAdapter) ;
        mmRecyclerView.setHasFixedSize(true);
        Log.v("mainfeed search알림","recyclerview2에 adapter 끼워줌");

        // 리사이클러뷰에 LinearLayoutManager 지정. (vertical)
        mmRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
        Log.v("mainfeed search 알림","layoutmanager 지정");


        // 리사이클러뷰 구분선 주기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mmRecyclerView.getContext(),
                mmLinearLayoutManager.getOrientation());
        mmRecyclerView.addItemDecoration(dividerItemDecoration);
        Log.v("mainfeed search 알림","recyclerview layoutmanager support");

        // 로그인할 때 저장해준 아이디를 가지고 있는 쉐어드 파일을 불러온다.
        SharedPreferences userid = getSharedPreferences("useridFile", MODE_PRIVATE);
        // 로그인할 때 저장해준 벨류값을 가지고 있는 key를 useridString에 넣어줘서 이 key로 언제든지 회원정보에 접근할 수 있게 한다.
        String useridString = userid.getString("userid","");
        Log.v("mainfeed search 알림"," 로그인할 때 저장해준 해당 아이디 확인 : " + useridString);

        // 저장된 회원정보를 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences testUserInfo = getSharedPreferences("testuserinfo",0);

        // 해당 아이디(KEY)로 해당 유저에 대한 정보를 가져올 수 있다.
        // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key) - useridString와 value 값을 불러와준다.
        String thisUserinfo =  testUserInfo.getString(useridString, "");
        Log.v("mainfeed search 알림"," 로그인할 때 저장해준 아이디로 해당 정보에 다가갈 수 있도록 알림 : " + thisUserinfo);

        // 이름, 닉네임, 아이디, 패스워드 순으로 이루어진 thisUserInfo의 두 번째인 닉네임 값을 가지고 오기 위해서
        // split으로 [1]이란 배열에 들어가 있는 index 2번째의 값을 가져오겠다.
        String user_nickname = thisUserinfo.split(",")[1];



        // 키를 가져와주기 위해 id_check에 아이디를 담아줌
        String id_check = thisUserinfo.split(",")[2];
        Log.v("mainfeed search 알림"," id_check 키 값 가져왔는지 확인 : " + id_check);

        if(testUserInfo.contains(id_check)) {

            // write화면에서 글쓴 값 불러와주기 위해 해당 값이 담겨있는 shared 파일 불러옴
            SharedPreferences UserUploadContentsPreferences = getSharedPreferences("UserUploadContentsPreferences", 0);

            // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key)와 value 값을 불러와준다.
            String receive_write_info = UserUploadContentsPreferences.getString(id_check, "");
            Log.v("mainfeed search 알림", " write페이지에서 제목, 내용, 이미지 가져왔는지 확인 알림 : " + receive_write_info);

            // https://onepinetwopine.tistory.com/116 참고
            String[] splitReceiveInfo = receive_write_info.split("&");
            Log.v("mainfeed search 알림", " write페이지에서 제목, 내용, 이미지 split 확인 : " + splitReceiveInfo);

            for (int i = 0; i < splitReceiveInfo.length; i++) {

                Log.v("mainfeed search split 알림", "" + splitReceiveInfo[i]);
                // 제목, 내용, 이미지 순으로 이루어진 receive_write_info 각각의 값들을 가져와준다.
                // String receive_title, String receive_contents, String receive_imageuri

                receive_title = splitReceiveInfo[i].split(",")[0];
                Log.v("mainfeed search 알림", " write페이지에서 제목 split 확인 : " + receive_title);

                receive_contents = splitReceiveInfo[i].split(",")[1];
                Log.v("mainfeed search 알림", " write페이지에서 내용 split 확인 : " + receive_contents);

                receive_imageuri = splitReceiveInfo[i].split(",")[2];
                Log.v("mainfeed search 알림", " write페이지에서 이미지 split 확인 : " + receive_imageuri);

                Log.v("mainfeed search 알림 2", "receive_title : " + receive_title + " receive_contents : " + receive_contents + " receive_imageuri : " + receive_imageuri);

                // uri 받아왔다.
                if (selectedImageUri != null) {
                    selectedImageUri = Uri.parse(receive_imageuri);
                }

                // Log.v("mainfeed oncreat 알림", " write페이지에서 제목, 내용, 이미지 각각의 내용들 확인 : receive_title : " + receive_title + " receive_contents : " + receive_contents + " receive_imageuri : " + receive_imageuri);
                // write 창에서 받아오는 값이 null이 아닐 경우, 그리고 split한 값들이 다 null이 아닐 경우에만 데이터 쌓이도록.
                if (receive_write_info != null && receive_title != null & receive_contents != null && receive_imageuri != null) {

                    // recycleritem 생성자를 사용하여 arraylist에 들어갈 데이터를 만들어 준다.
                    ExampleItem ri = new ExampleItem(receive_title, receive_contents, receive_imageuri);

                    //데이터를 삽입한다.
                    mmList.add(0, ri); //첫 줄에 삽입

                    // 어댑터에서 RecyclerView에 데이터를 반영하도록 합니다.
                    mmAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영

                }

            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mmAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}


