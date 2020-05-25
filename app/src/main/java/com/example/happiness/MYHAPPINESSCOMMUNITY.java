package com.example.happiness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MYHAPPINESSCOMMUNITY extends AppCompatActivity {

    // 내 글과 사람들이 쓴 글이 올라오는 곳이다.
    //내 행복 커뮤니티의 리사이클러뷰 및 어댑터, 어레이리스트, 레이아웃매니저 변수명이름 설정
    RecyclerView myhappinesscommunityRecyclerView;
    myhappinesscommunityAdapter myhappycommunityAdapter;
    ArrayList<MYHAPPINESSCOMMUNITY_DATA> myhappinesscommunityDataArrayList ;
    LinearLayoutManager myhappinessLayoutManager;

    Uri selectedImageUri;
    Uri editImageUri;

    private static final int REQ_ADD_CONTENTS_MYCOMMUNITY = 1;
    private static final int REQ_EDIT_CONTENTS_MYCOMMUNITY = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhappinesscommunity);

        // 리사이클러뷰 아이템 xml에 있는 리사이클러뷰 참조
        myhappinesscommunityRecyclerView = findViewById(R.id.ourhappinesscommunity_recyclerview);
//        myhappinesscommunityRecyclerView.setHasFixedSize(true);

        // linearlayoutmanager 클래스로부터 해당 객체 생성해준 다음에
        // 이 리사이클러뷰엔 linearlayoutmanager을 낀다.
        myhappinessLayoutManager = new LinearLayoutManager(this);
        myhappinesscommunityRecyclerView.setLayoutManager(myhappinessLayoutManager);

        // arraylist 객체 생성
        myhappinesscommunityDataArrayList = new ArrayList<>();

        myhappycommunityAdapter = new myhappinesscommunityAdapter(myhappinesscommunityDataArrayList,this);

        myhappinesscommunityRecyclerView.setAdapter(myhappycommunityAdapter);

        //글쓰기 아이콘 누르면 내 피드에 글쓰기 화면으로 넘어가기 // startActivityforresult
        ImageButton mycommunity_write = (ImageButton) findViewById(R.id.write_icon);
        mycommunity_write.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v){

                Intent i = new Intent(MYHAPPINESSCOMMUNITY.this, MYHAPPINESSCOMMUNITY_WRITE.class);
                startActivityForResult(i, REQ_ADD_CONTENTS_MYCOMMUNITY);

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        requestCode는 feedActivity에서 writeActivity를 구별하기위해 사용하는부분
//        resultCode는 writeActivity에서 어떠한 결과코드를 주었는지에 대한 부분이고
//        Intent data에는 writeActivity에서 보낸 결과 데이터가 들어있는 부분입니다.

        if (requestCode == REQ_ADD_CONTENTS_MYCOMMUNITY) {
            if (resultCode == Activity.RESULT_OK) {

                // Name 값을 String 타입 그대로 표시.
                String myhappinesscommunity_title = data.getStringExtra("myhappinesscommunity_title");
                Log.v("mycommunity 알림","myhappinesscommunity_title" + myhappinesscommunity_title);

                String myhappinesscommunity_contents = data.getStringExtra("myhappinesscommunity_contents");
                Log.v("mycommunity 알림","myhappinesscommunity_contents 알림" + myhappinesscommunity_contents);

                String myhappinesscommunity_geturi = data.getStringExtra("uri");

                if(selectedImageUri != null){
                    selectedImageUri = Uri.parse(myhappinesscommunity_geturi);

                }

                // MYHAPPINESSCOMMUNITY_DATA 생성자를 사용하여 arraylist에 들어갈 데이터를 만들어 준다.
                MYHAPPINESSCOMMUNITY_DATA ri = new MYHAPPINESSCOMMUNITY_DATA(myhappinesscommunity_title, myhappinesscommunity_contents, myhappinesscommunity_geturi); // , bitmap_image
                Log.v("mycommunity 알림","ri 생성 알림");

                //데이터를 삽입한다.
                myhappinesscommunityDataArrayList.add(0, ri); //첫 줄에 삽입
                Log.v("mycommunity 알림","myhappinesscommunityDataArrayList.add 알림");

                // 어댑터에서 RecyclerView에 데이터를 반영하도록 합니다.
                myhappycommunityAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영

            } else {

                Toast.makeText(MYHAPPINESSCOMMUNITY.this, "Failed", Toast.LENGTH_SHORT).show();

            }

        }
        else{
            if (resultCode == Activity.RESULT_OK) {

                // Name 값을 String 타입 그대로 표시.
                // edit 화면으로부터 "edit_mycommunityTitle" 키에 해당한 입력값을 community_Edit_title로 받아준다.
                String community_Edit_title = data.getStringExtra("edit_mycommunityTitle") ;
                // title을 받아왔다.

                // edit 화면으로부터 "edit_mycommunityContents" 키에 해당한 입력값을 community_Edit_contents로 받아준다.
                String community_Edit_contents = data.getStringExtra("edit_mycommunityContents") ;
                // contents 받아왔다.

                String community_Edit_image = data.getStringExtra("edit_image");
                editImageUri = Uri.parse(community_Edit_image);

                // position에 title_position2로 getintextra한 position을 넣어준다.
                int position = data.getIntExtra("mycommunity_title_position2", 0);

                // recycleritem 데이터클래스 생성자로 Edit_title과 Edit_contents를 받아준다.
                // recycleritem 데이터클래스를 보면 String 값을 두 개 넣어주고 반환해주기 때문이다.
                MYHAPPINESSCOMMUNITY_DATA ri = new MYHAPPINESSCOMMUNITY_DATA(community_Edit_title, community_Edit_contents, community_Edit_image);

                // 수정할 때 해당 position 값을 넣어주고, ri를 입력해준다.
                myhappinesscommunityDataArrayList.set(position, ri);

                // position 값
                Log.v("mainfeed onactivityresult 2 position 알림","positioin 알림" + position );
                Toast.makeText(MYHAPPINESSCOMMUNITY.this, " 위치값 " + position, Toast.LENGTH_SHORT).show();

                // 어댑터에서 RecyclerView에 데이터를 반영하도록 합니다.
                myhappycommunityAdapter.notifyDataSetChanged();

                //변경된 데이터를 화면에 반영
                Log.i("mainfeed onactivityresult 2 알림", String.valueOf(data.getIntExtra("title_position2", 0)));

//         수정된 게 위로 올라옴
//         recycleritem 생성자를 사용하여 arraylist에 들어갈 데이터를 만들어 준다.
//         RecyclerItem ri = new RecyclerItem(Edit_title, Edit_contents);
//         Log.v("mainfeed onactivityresult 알림", "ri 확인" + ri + Edit_title+ Edit_contents);
//         mList.set(0, ri);
//         RecyclerItem set = mList.set(position, Edit_title);

            }else{

                Toast.makeText(MYHAPPINESSCOMMUNITY.this, "Failed", Toast.LENGTH_SHORT).show();

            }

        }
    }

}

//                Bitmap myhappinesscommunity_bitmapimage;
//                myhappinesscommunity_bitmapimage = (Bitmap) data.getExtras().get("imgbitmap");


//                Bitmap bitmap_image ;
//
////                try{
//                    // byte로 받아준다. 근데 myhappinesscommunity_image 이게 null이 뜬다. 알림 2 로그가 뜨지 않는다.
//                    byte[] myhappinesscommunity_image = getIntent().getByteArrayExtra("myhappinesscommunity_image");
//                    Log.v("mycommunity 알림","myhappinesscommunity_image  알림 1 : myhappinesscommunity_image" + myhappinesscommunity_image + "getintent~ : " + getIntent().getByteArrayExtra("myhappinesscommunity_image"));
//
//                    // bitmap으로 변환해줘서 밑에 ri 생성자에 bitmap 자리에 bitmap_image를 넣어줌
//                    bitmap_image = BitmapFactory.decodeByteArray(myhappinesscommunity_image, 0, myhappinesscommunity_image.length); // java.lang.NullPointerException: Attempt to get length of null array 에러
//                    Log.v("mycommunity 알림","myhappinesscommunity_image  알림 2 : bitmap_image" + bitmap_image + "myhappinesscommunity_image : " +  myhappinesscommunity_image + myhappinesscommunity_image.length);
//
////                }catch(NullPointerException e)
////                {
////
////                }
//
//
////                String myhappinesscommunity_image = data.getByteExtra("myhappinesscommunity_image");
////                Bitmap myhappinesscommunity_image = data.getByteExtra("myhappinesscommunity_image");