package com.example.happiness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MYHAPPINESSCOMMUNITY_WRITE extends AppCompatActivity {

    EditText write_mycommunitytitle;
    EditText write_mycommunitycontents;
    ImageButton upload_contents;

//    Bitmap img;
//    Bitmap imgbitmap;
    //    private static final int REQUEST_CODE = 0;
    Uri selectedImageUri;
    private static final int GET_PICTURE_URI = 0;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhappinesscommunity__write);

        //글쓰기 화면의 제목
        write_mycommunitytitle = findViewById(R.id.community_write_title);

        //글스기 화면의 내용
        write_mycommunitycontents = findViewById(R.id.community_write_contents);

        imageView = findViewById(R.id.community_write_image);

        //글쓰기 화면의 체크 버튼
        upload_contents = findViewById(R.id.upload_icon);
        upload_contents.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // mainfeed에서 startActivityForResult 호출 받아 - setresult해주는 내용

                Intent intent = new Intent();
                //intent라는 데이터 통로를 만들어줬다.

                EditText editmycommunityTitleName = (EditText) findViewById(R.id.community_write_title);
                intent.putExtra("myhappinesscommunity_title", editmycommunityTitleName.getText().toString());
                //write activity에서 write_title를 가져와 editTextName 변수명을 설정해주고 editTextName 뷰 안에 있는 텍스트 내용을 getText, 그대로 받겠다.
                //intent로 putextra를 해주겠다.
                Log.v("myhappinesscommunity_title 알림","값 확인 : " + editmycommunityTitleName.getText().toString());

                EditText editmycommunityContentsName = (EditText) findViewById(R.id.community_write_contents);
                intent.putExtra("myhappinesscommunity_contents", editmycommunityContentsName.getText().toString());
                //write activity에서 write_contents를 가져와 editTextContents 변수명을 설정해주고 editTextContents 뷰 안에 있는 텍스트 내용을 getText, 그대로 받겠다.
                //intent로 putextra를 해주겠다.
                Log.v("myhappinesscommunity_contents 알림","값 확인 : " + editmycommunityContentsName.getText().toString());

                if(selectedImageUri != null){
                    intent.putExtra("uri",selectedImageUri.toString()); // 사진 올리지 않았을 때 null 값 예외처리
                    Log.v("myhappinesscommunity_image 알림","값 확인 : selectedImageUri.toString()  " + selectedImageUri.toString()  + "  selectedImageUri  " + selectedImageUri);
                }
//                java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String android.net.Uri.toString()' on a null object reference
//                at com.example.happiness.MYHAPPINESSCOMMUNITY_WRITE$1.onClick(MYHAPPINESSCOMMUNITY_WRITE.java:72)

                setResult(RESULT_OK, intent);
                // 결과코드를 'RESULT_OK'로 세팅. 이후 이전 액티비티로 돌아가 onActivityResult()가 호출됨.
                // intent를 전달하겠다.

                Log.v("write activity 알림", "setResult" + RESULT_OK + intent);

                finish(); // 액티비티를 끝낸다.
            }
        });

        ImageButton cancelbutton = (ImageButton) findViewById(R.id.x_icon);
        cancelbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), MYHAPPINESSCOMMUNITY.class);
                        startActivity(intent);

                    }
                }
        );

        // 갤러리 열어서 이미지 등록하는 단계
        findViewById(R.id.gallery_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MYHAPPINESSCOMMUNITY_WRITE.this, "갤러리 열기", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                startActivityForResult(intent, GET_PICTURE_URI);
            }
        });
    }

    //  Intent intent = new Intent();
    //  intent.setType("image/*");
    //  intent.setAction(Intent.ACTION_GET_CONTENT);
    //  startActivityForResult(intent, REQUEST_CODE);
    //  intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
    //  intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

    // 갤러리에서 불러온 이미지 붙여넣는 단계
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

        if (requestCode == GET_PICTURE_URI && resultCode == RESULT_OK && data != null && data.getData() != null) {

            ImageView imageview = (ImageView) findViewById(R.id.community_write_image);
            selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);

        }
    }
}

//        if (requestCode == GET_PICTURE_URI) {
//            if (resultCode == Activity.RESULT_OK) {
//                try {
//
//                    imgbitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
//                    Log.v("bitmap 이미지 넣기 확인 알림","bitmap 값 확인 " + imgbitmap);
////                    배치해놓은 ImageView에 이미지를 넣어봅시다.
//                    imageView.setImageBitmap(imgbitmap);
////                    Glide.with(mContext).load(data.getData()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView); // OOM 없애기위해 그레들사용
//
//                } catch (Exception e) {
//                    Log.e("test", e.getMessage());
//                }
//            }
//        if(requestCode == GET_PICTURE_URI)
//        {
//            if(resultCode == RESULT_OK)
//            {
//                try{
//                    InputStream in = getContentResolver().openInputStream(data.getData());
//
//                    Log.v("COMMUNITY WRITE _ IMAGE 알림 1","IMAGE data, data.getdata , in 확인 " + data + " " + data.getData() + " " + in);
//
//                    img = BitmapFactory.decodeStream(in);
//                    in.close();
//
//                    Log.v("COMMUNITY WRITE _ IMAGE 알림 2","IMAGE data, data.getdata , img 확인 " + data + " data.getData() :" + data.getData() + " " + img);
//
//                    // imageview에 내가 찍은 사진 박아줌
//                    imageView.setImageBitmap(img);
//
//                    Log.v("COMMUNITY WRITE _ IMAGE 알림 3","IMAGE data, data.getdata , img 확인 " + data + " data.getData() " + data.getData() + " " + img + " " + imageView);
//
//                }catch(Exception e)
//                {
//
//                }
//            }
//            else if(resultCode == RESULT_CANCELED)
//            {
//                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
//            }
//        }

//        }

//                intent.putExtra("myhappinesscommunity_image", imgbitmap);
//                Log.v("write putextra bitmap - byte 알림 1","imgbitmap " + imgbitmap );

//                try{
//                    // bitmap을 byte로 바꿔서 보내준다.
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    img.compress(Bitmap.CompressFormat.JPEG, 1, stream); // java.lang.NullPointerException: Attempt to invoke virtual method 'boolean android.graphics.Bitmap.compress(android.graphics.Bitmap$CompressFormat, int, java.io.OutputStream)' on a null object reference
////              compress 인자 값에는 압축 옵션( JPEG, PNG ) 와 품질 설정 ( 0 - 100까지의 int형 ), 그리고 압축된 바이트배열을 담을 stream을 넘겨줍니다. 출처: https://it77.tistory.com/98 [시원한물냉의 사람사는 이야기]
//                    Log.v("write putextra bitmap - byte 알림 1","write putextra bitmap - byte 알림 1 stream : " + stream + " img  " + img  );
////                Log.v("write putextra bitmap - byte 알림 1","stream : " + stream + " img : " + img );
//
//                    byte[] byteArray = stream.toByteArray();
//                    intent.putExtra("myhappinesscommunity_image", byteArray);
//
//                    Log.v("write putextra bitmap - byte 알림 2","write putextra bitmap - byte 알림 2 stream : " + stream + " img  " + img + " " + byteArray);
//
//                }catch(Exception e)
//                {
//
//                }
////                출처: https://twinw.tistory.com/31 [흰고래의꿈]