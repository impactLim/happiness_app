package com.example.happiness;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

// https://dreamaz.tistory.com/345 참고
public class HAPPINESSCATEGORY extends AppCompatActivity {

    // 카테고리 리사이클러뷰, 레이아웃매니저 객체 생성.
    // 리사이클러뷰 클래스에서 카테고리리사이클러뷰라는 객체 생성
    // 리사이클러뷰 레이아웃매니저 클래스에서 카테고리 레이아웃 매니저라는 객체 생성

    RecyclerView categoryRecyclerView;
    RecyclerView.LayoutManager categoryLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happinesscategory);


        // 내가 만든 이 카테고리 리사이클러뷰는 activity_happinesscategory.xml에 있는 리사이클러뷰 이름과 같다. 이를 참조한다.
        categoryRecyclerView = findViewById(R.id.category_recyclerview);

        // 내가 생성한 카테고리 레이아웃 매니저는 리니어레이아웃으로 나열되는 형태를 가진다.
        // 리니어레이아웃매니저로 지정해준 것이다.
        categoryLayoutManager = new LinearLayoutManager(this);

        // 내 리사이클러뷰에 리니어레이아웃으로 나열된 카테고리레이아웃매니저를 설정해준다.
        categoryRecyclerView.setLayoutManager(categoryLayoutManager);

        // 어레이리스트 클래스로 HAPPINESSCATEGORY_INFO의 데이터들을 쌓을 수 있는 categoryinfoList라는 객체를 - 저장공간을 만들어줬다.
        // 리사이클러뷰에 표시될 데이터 리스트들을 생성하는 것이다.
        final ArrayList<HAPPINESSCATEGORY_INFO> categoryinfoList = new ArrayList<>();

// 하드코딩했던 카테고리 이름들
//        // 카테고리리스트에 카테고리별 이름들과 각 카테고리별 숫자들을 추가해준다.
//        categoryinfoList.add(new HAPPINESSCATEGORY_INFO("일상"));
//        categoryinfoList.add(new HAPPINESSCATEGORY_INFO("가족"));

        // 어댑터 = 데이터 + 리사이클러뷰
        // HappinessCategoryAdapter로 happinesscategoryAdapter 객체를 생성해준다. 이 객체는 categoryinfoList라는 저장공간으로부터 데이터를 받는다.
        // 이 데이터 목록을 아이템 단위의 뷰로 구성하여 화면에 표시해주기 위해 어댑터를 사용하는 것이다.
        // 어댑터를 통해 만들어진 각 아이템 뷰는 뷰홀더 객체에 저장되어 화면에 표시된다. 여기서 뷰홀더는 어댑터에 의해 관리되며 화면에 표시될 아이템 뷰를 저장하는 객체이다.
        // 아래처럼 HappinessCategoryAdapter를 생성할 때 내가 표시하고자하는 데이터를 전달한다.
        final HappinessCategoryAdapter happinesscategoryAdapter = new HappinessCategoryAdapter (categoryinfoList, this);

        boolean boo = categoryinfoList.isEmpty();
        Log.v("onBindViewHolder 알림","categoryinfoList.size : " + boo);

        //내 리사이클러뷰에 이 어댑터를 설정해준다.
        categoryRecyclerView.setAdapter(happinesscategoryAdapter);

        // 취소 버튼 누르면 메인피드로 나감
        ImageButton btn_cancelbutton = (ImageButton) findViewById(R.id.x_icon);
        btn_cancelbutton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), myhappinessfeedActivity.class);

                        startActivity(intent);

                    }
                }

        );
        //  리사이클러뷰에 있는 textview_title 가져와서 거기
        //  TextView seeCategoryName = (TextView)findViewById(R.id.textView_title);
        //   Log.v("seeCategoryName 알림1","" + categoryNAME + seeCategoryName);

        // categoryName을 seeCategoryName에 넣어주려고 했었음.
        // seeCategoryName.setText(categoryName);
        //  textView.setText();는 TextView에 들어갈 텍스트를 설정하는 코드.
        // EditCategory.getText()는 EditCategory에서 입력된 텍스트를 불러오는 코드
        //   Log.v("seeCategoryName 알림2","" + categoryNAME + seeCategoryName);

        // 데이터클래스에 정한 것
        // 이 categoryNAME이 null 값일 때 처리해줘야 한다.
        // https://jamesdreaming.tistory.com/42
        // NullPointerException 예방코드



        // 더하기 버튼 누르면 더할 수 있게 됨.
        ImageButton btn_addbutton = (ImageButton) findViewById(R.id.add_icon);
        btn_addbutton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        // AlertDialog.Builder로 다이어로그를 생성해줘 디자인할 수 있음(dialog 디자인이라고 생각하면 될듯), 행복카테고리 화면 위에다가
                        AlertDialog.Builder categorybuilder = new AlertDialog.Builder(HAPPINESSCATEGORY.this);

                        // 이 화면에 category 삽입, 수정 박스를 연결시켜주고
                        View view = LayoutInflater.from(HAPPINESSCATEGORY.this).inflate(R.layout.category_editbox_dialog, null, false);

                        // 행복카테고리 다이어로그에 레이아웃을 설정시켜줌
                        categorybuilder.setView(view);

                        // 다이어로그 카테고리 이름 지어줌
                        categorybuilder.setTitle("행복카테고리");

                        // 연결시켜준 레이아웃의 버튼을 이 클래스에서 쓰기 위해 객체로 만들어줌.
                        final Button AddCategory = (Button) view. findViewById(R.id.add_category_button);
                        final EditText EditCategory = (EditText) view.findViewById(R.id.write_categoryname);

                        // 버튼의 텍스트 내용을 "추가"로 설정시켜줌.
                        AddCategory.setText("카테고리 추가");

                        // categorydialog라는 이름의 다이어로그를 만들어줌. // categorybuilder 디자인으로 만들어진 다이어로그를 띄어준다.
                        final AlertDialog categorydialog = categorybuilder.create();

                        // 다이얼로그의 카테고리 추가버튼을 누르면
                        AddCategory.setOnClickListener(new View.OnClickListener(){

                                @Override
                                public void onClick(View v) {

                                // 위의 EditCategory에 입력한 값을 categoryNAME으로 가져와준다.
                                String categoryNAME = EditCategory.getText().toString(); // 사용자가 입력한 저장할 데이터

                                // 입력값만 받는 거라 arraylist에 추가된 상태가 아니다.
                                Log.v("categoryaddclick 알림", "입력값 받았는지 확인, 아직 어레이리스트에 추가된 상태 아님"+ categoryNAME + "현재 어레이리스트 크기" + categoryinfoList.size());

                                // ctegoryNAME에 입력한 값들을 categorydata라는 데이터에 추가해줄 것이다.
                                HAPPINESSCATEGORY_INFO categorydata = new HAPPINESSCATEGORY_INFO(categoryNAME);
                                Log.v("categoryaddclick 알림", "ctegoryNAME에 입력한 값들을 categorydata라는 ArrayList에 추가해줄 것이다.");

                                //내가 입력한 값이 arraylist에 들어온 걸 확인할 수 있다.
                                // categorydata라는 데이터에 내가 입력한 값들을 리사이클러뷰에 더해준다.
                                // categoryinfolist라는 어레이리스트에 내가 입력한 값들을 넣어줄 것이다.
                                categoryinfoList.add(categorydata);
                                Log.v("categoryinfoList 알림 : categoryinfoList라는 ArrayList에 내가 입력한 값들(categorydata)을 리사이클러뷰에 더해주는 알림.","리사이클러뷰에 어떤 데이터가 추가되는지");
                                Log.v("categoryaddclick 알림", "사용자가 입력한 데이터를 categoryNAME으로 가져왔는지 " + " categoryNAME 내가 입력한 걸 가져왔는지 확인하기 위해");
                                Log.v("categoryaddclick 알림", "내가 입력한 데이터  " + categoryNAME +"  어레이리스트 크기  " + categoryinfoList.size());

                                // 다이얼로그 카테고리 추가버튼 누르면 추가되도록
                                // SharedPreferences 클래스에서 categorysaveFile이라는 객체를 만들어줬다.
                                // 이 객체가 가지고 있는 파일 이름은 CategorySavaeFile이다.
                                SharedPreferences categorysaveFile = getSharedPreferences("CategorySaveFile",MODE_PRIVATE);

                                // 이 파일에 데이터를 기록하기 위해 이 editor 객체를 얻어야 한다.
                                SharedPreferences.Editor editor = categorysaveFile.edit();

                                // key, value를 이용하여 저장하는 형태.
                                // categoryNAME이란 KEY 값으로 categoryNAME에 입력된 값을 저장한다.
                                editor.putString("categoryNAME",categoryNAME);
                                Log.v("categoryaddclick 알림", "내가 입력한 데이터 확인"+ categoryNAME +" 어레이리스트 확인 "+ categoryinfoList.size() + "  입력된 값이 저장 되었는지 확인 "+ editor.putString("categoryNAME", categoryNAME));
                                Log.v("categoryaddclick 알림", "지정된 키가 있는 항목"+ getSharedPreferences("CategorySaveFile",MODE_PRIVATE).contains(categoryNAME));
                                //다양한 형태의 변수값을 저장할 수 있다.
                                //editor.putString();
                                //editor.putStringSet();

                                //최종 커밋
                                editor.commit();

                                // 어댑터에 연결된 recyclerview를 갱신한다.
                                happinesscategoryAdapter.notifyDataSetChanged();

                                // 값이 변경되고 나서 다이어로그를 종료한다.
                                categorydialog.dismiss();

                               }

                           }

                        );

                        // 다이어로그를 띄어준다.
                        categorydialog.show();

                    }
                }

        );


        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences categorysaveFile = getSharedPreferences("CategorySaveFile",MODE_PRIVATE);
        Log.v("oncreat 저장값 불러오기 작업 categorysaveFile - 저장된 값을 가지고 있는 파일 불러와졌는지 확인 알림","" + getSharedPreferences("CategorySaveFile",MODE_PRIVATE));

        //categoryNAME이라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        String categoryNAME = categorysaveFile.getString("categoryNAME",""); // key에 해당하는 string 값 검색
        Log.v("oncreat 저장값 불러오기 작업 categoryNAME이라는 key에 저장된 값이 있는지 확인 알림","" + categoryNAME + categorysaveFile.getString("categoryNAME",""));
        Log.v("oncreat 저장값 불러오기 작업 알림", "categoryNAME 이 shared에 들어가져 있는지 확인"+ getSharedPreferences("CategorySaveFile",MODE_PRIVATE).contains(categoryNAME));

        if(categoryNAME != null){
            //Shared에서 불러온 걸 arraylist에 담아준다!
            categoryinfoList.add(new HAPPINESSCATEGORY_INFO(categoryNAME));
            Log.v("oncreat 저장값 불러오기 작업 알림 categoryinfoList라는 어레이리스트에 잘 담겨있는지","categoryinfoList : " + categoryinfoList + " categoryinfoList 크기 " + categoryinfoList.size());
            Log.v("oncreat 저장값 불러오기 작업 알림", "categoryNAME 이 shared에 들어가져 있는지 확인"+ getSharedPreferences("CategorySaveFile",MODE_PRIVATE).contains(categoryNAME) + categoryNAME);
            Log.v("oncreat 저장값 불러오기 작업 알림"," 저장된 모든 값 검색하기 "+ categorysaveFile.getAll());
        }

    }
}

// shared 안쓰고 arraylist 통해서 데이터만 추가되도록, 뒤로 갔다 오면 저장 x
//    // 위의 EditCategory에 입력한 값을 categoryNAME으로 가져와준다.
//    String categoryNAME = EditCategory.getText().toString();
//
//    // ArrayList에 추가해줌
//    HAPPINESSCATEGORY_INFO categorydata = new HAPPINESSCATEGORY_INFO(categoryNAME);
//
//// 리사이클러뷰에 데이터를 더해준다.
//                                   categoryinfoList.add(categorydata);
//                                           Log.v("categoryinfoList 알림 : 리사이클러뷰에 데이터를 더해주는 알림.","리사이클러뷰에 어떤 데이터가 추가되는지  " + "categorydata : " +  categorydata + "categoryinfoList : " + categoryinfoList);
//
//
//
//
//                                           // 어댑터에 연결된 recyclerview를 갱신한다.
//                                           happinesscategoryAdapter.notifyDataSetChanged();
//
//                                           // 값이 변경되고 나서 다이어로그를 종료한다.
//                                           categorydialog.dismiss();

//
// 다이얼로그 카테고리 추가버튼 누르면 추가되도록
//// SharedPreferences 클래스에서 categorysaveFile이라는 객체를 만들어줬다.
//// 이 객체가 가지고 있는 파일 이름은 CategorySavaeFile이다.
//SharedPreferences categorysaveFile = getSharedPreferences("CategorySaveFile",MODE_PRIVATE);
//
//    // 이 파일에 데이터를 기록하기 위해 이 editor 객체를 얻어야 한다.
//    SharedPreferences.Editor editor = categorysaveFile.edit();
//
//    // 위의 EditCategory에 입력한 값을 categoryNAME으로 가져와준다.
//    String categoryNAME = EditCategory.getText().toString(); // 사용자가 입력한 저장할 데이터
//
//    // ArrayList에 추가해줌
//    HAPPINESSCATEGORY_INFO categorydata = new HAPPINESSCATEGORY_INFO(categoryNAME);
//
//// 리사이클러뷰에 데이터를 더해준다.
//                                   categoryinfoList.add(categorydata);
//                                           Log.v("categoryinfoList 알림 : 리사이클러뷰에 데이터를 더해주는 알림.","리사이클러뷰에 어떤 데이터가 추가되는지  " + "categorydata : " +  categorydata + "categoryinfoList : " + categoryinfoList);
//
//                                           editor.putString("categoryNAME",categoryNAME); // key, value를 이용하여 저장하는 형태
//                                           //다양한 형태의 변수값을 저장할 수 있다.
//                                           //editor.putString();
//                                           //editor.putStringSet();
//
//                                           //최종 커밋
//                                           editor.commit();
//
//
//                                           // 어댑터에 연결된 recyclerview를 갱신한다.
//                                           happinesscategoryAdapter.notifyDataSetChanged();
//
//                                           // 값이 변경되고 나서 다이어로그를 종료한다.
//                                           categorydialog.dismiss();
