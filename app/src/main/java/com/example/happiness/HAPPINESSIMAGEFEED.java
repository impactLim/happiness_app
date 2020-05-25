package com.example.happiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class HAPPINESSIMAGEFEED extends AppCompatActivity {

    // 메인 행복이미지피드에서 리사이클러뷰, 어댑터, 레이아웃매니저의 객체 생성 - 이 객체들은 리사이클러뷰 클래스의 속성과 기능 쓸 수 있다.
    private RecyclerView imageRecyclerView;
    private RecyclerView.Adapter imageAdapter;
    private RecyclerView.LayoutManager imageLayoutManager;

    // ImageData 클래스가 가지고 있는 데이터들을 쌓는다.
    private ArrayList<ImageAdapter.ImageData> mainImageDataset;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happinessimagefeed);

        // xml(activity_happinessimagefeed)의 리사이클러뷰를 쓰기 위해서 findViewById를 사용한다.
        // xml에서 오는 리사이클러뷰와 현재 메인클래스의 리사이클러뷰를 일치시켜주기 위해서다.
        imageRecyclerView = (RecyclerView) findViewById(R.id.image_recyclertest) ;

        // 리사이클러뷰의 사이즈를 고정시킨다.
        imageRecyclerView.setHasFixedSize(true);

        // 이 imagerecyclerview는 리니어레이아웃 매니저를 사용하겠다.
        // RecyclerView는 데이터를 사용자에게 어떻게 보여줄지 그림담당만 한다.
        imageLayoutManager = new LinearLayoutManager(this);
        imageRecyclerView.setLayoutManager(imageLayoutManager);

        // ImageData 클래스의 객체를 생성.
        mainImageDataset = new ArrayList<>();
        // ImageDataset를 가지고 있는 ImageAdapter의 객체를 생성해주고
        // 어댑터가 데이터를 가지고 있으니 데이터와 리사이클러뷰를 연결시켜주는 것이다.
        imageAdapter = new ImageAdapter(mainImageDataset);
        // 리사이클러뷰의 이 어댑터를 껴준다.
        imageRecyclerView.setAdapter(imageAdapter);

        // 데이터를 더해준다.
        mainImageDataset.add(new ImageAdapter.ImageData("account", R.drawable.account_icon));
        mainImageDataset.add(new ImageAdapter.ImageData("bell", R.drawable.bell_icon));
        mainImageDataset.add(new ImageAdapter.ImageData("x", R.drawable.x_icon));

        //write 아이콘 누르면 imagefeed에서 이미지피드로 넘어가기
        ImageButton upload_image = (ImageButton) findViewById(R.id.write_icon);
        upload_image.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v){
                Intent intent=(new Intent(getApplicationContext(),HAPPINESSIMAGEFEED_WRITE.class));

                Toast.makeText(HAPPINESSIMAGEFEED.this, "imagefeed write 입장", Toast.LENGTH_SHORT).show();

                startActivity(intent);

            }

        });



    }
}
