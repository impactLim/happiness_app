package com.example.happiness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MYHAPPINESSCOMMUNITY_EDIT extends AppCompatActivity {

    EditText community_Edit_title;
    EditText community_Edit_contents;
    Button community_Edit_button;
    ImageView community_edit_image;
    Uri EditImageUri;
    private static final int GET_PICTURE_URI2 = 1;

    ArrayList<MYHAPPINESSCOMMUNITY_DATA> myhappinesscommunityData = new ArrayList<MYHAPPINESSCOMMUNITY_DATA>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhappinesscommunity__edit);

        //글쓰기 화면의 제목
        community_Edit_title = (EditText) findViewById(R.id.community_edit_title);
        Log.v("mycommunity_edit_title 알림","edit_title"+ community_Edit_title);

        //글쓰기 화면의 내용
        community_Edit_contents = (EditText) findViewById(R.id.community_edit_contents);
        Log.v("mycommunity_edit_contents 알림","edit_contents"+ community_Edit_contents);

        // edit 화면이 oncreat 될 때,
        // "key-title"이라는 key로 title에 입력된 값을 edit 화면에 입력받게 해준다.
        community_Edit_title.setText(getIntent().getStringExtra("key_mycommunitytitle"));
        Log.v("mycommunity_edit_title 알림","edit_title2"+ community_Edit_title);

        // "key-contents"라는 key로 contents에 입력된 값을 edit 화면에 입력받게 해준다.
        community_Edit_contents.setText(getIntent().getStringExtra("key_mycommunitycontents"));
        Log.v("mycommunity_edit_contents 알림","edit_contents2"+ community_Edit_contents);

        community_edit_image = findViewById(R.id.community_edit_image);
        community_edit_image.setImageURI(Uri.parse(getIntent().getStringExtra("key_image")));
//        image get 할 때 참고
//        String myhappinesscommunity_geturi = data.getStringExtra("uri");
//        selectedImageUri = Uri.parse(myhappinesscommunity_geturi);
//        holder.iv_imageview.setImageURI(Uri.parse(item.getMyhappinesscommunityImageUrlStr()));

        // "title_position"이라는 키도 edit액티비티가 oncreat될 때 전달받는다.
        getIntent().getIntExtra("mycommunity_title_position", 0);

        // position 확인 로그
        Log.d("edit activity 알림","position 확인 "+ getIntent().getIntExtra("title_position", 0));

        findViewById(R.id.gallery_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MYHAPPINESSCOMMUNITY_EDIT.this, "갤러리 열기", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_PICTURE_URI2);
            }
        });

        //글쓰기 화면의 체크 버튼
        community_Edit_button = (Button) findViewById(R.id.mycommunity_edit_button);
        community_Edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                // edit_title과 edit_contents 뷰를 사용할 수 있도록 edittextname과 editcontentsname라는 변수명을 써줌
                // edit_title이란 키에 editTitleName에 들어갈 입력값을 intent로 보내줄 준비를 한다.
                EditText editMycommunityTitleName = (EditText) findViewById(R.id.community_edit_title);
                intent.putExtra("edit_mycommunityTitle", editMycommunityTitleName.getText().toString());

                // edit_contents라는 키에 editContentsName에 들어갈 입력값을 intent로 보내줄 준비를 한다.
                EditText editMycommunityContentsName = (EditText) findViewById(R.id.community_edit_contents);
                intent.putExtra("edit_mycommunityContents", editMycommunityContentsName.getText().toString());

//                ImageView editMycommunityImage = findViewById(R.id.community_edit_image);
                intent.putExtra("edit_image", EditImageUri.toString());

//                String myhappinesscommunity_geturi = data.getStringExtra("uri");
//                selectedImageUri = Uri.parse(myhappinesscommunity_geturi);

//                ImageView imageview = (ImageView) findViewById(R.id.community_write_image);
//                selectedImageUri = data.getData();
//                imageview.setImageURI(selectedImageUri);

                // title_position2라는 키에 위치값을 intent로 보내줄 준비를 한다.
                intent.putExtra("mycommunity_title_position2", getIntent().getIntExtra("mycommunity_title_position", 0));

                setResult(RESULT_OK, intent);

                finish();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

        if (requestCode == GET_PICTURE_URI2 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            ImageView editimageview = (ImageView) findViewById(R.id.community_edit_image);
            EditImageUri = data.getData();
            editimageview.setImageURI(EditImageUri);

        }
    }
}
