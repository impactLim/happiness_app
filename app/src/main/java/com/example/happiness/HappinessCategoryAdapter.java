package com.example.happiness;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
// https://humble.tistory.com/11?category=663015 리스트뷰 어댑터 사용법 참고
// 내 HappinessCategoryAdapter는 RecyclerView.Adapter<RecyclerView.ViewHolder>를 상속받아서 정의한다.
public class HappinessCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


// HappinessCategoryAdapter의 내부에 RecyclerView.ViewHolder를 상속받는 클래스를 정의. 이 클래스가 RecyclerView의 행(row)를 표시하는 클래스.
    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        TextView tvCategoryName;
        ImageButton ibEditDeleteButton;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.textView_title);
            ibEditDeleteButton = itemView.findViewById(R.id.EditDelete_button);
        }
    }

// HappinessCategoryAdapter가 ArrayList <HAPPINESSCATEGORY_INFO>를 인자로 갖는 생성자다.
// 인자값은 함수를 호출하는데 있어서 호출 시 전달하는 값을 의마한다.
// HappinessCategoryAdapter를 생성할 때 표시하고자 하는 데이터를 전달해준다.
    private ArrayList<HAPPINESSCATEGORY_INFO> categoryinfoList;

// 어댑터에선 액티비티를 호출할 수가 없다.
// 그래서 context에 액티비티에 대한 권한을 준다.
// 그러면 어댑터에서 액티비티를 호출할 수가 있다.

    private Context activityContext;


    HappinessCategoryAdapter(ArrayList<HAPPINESSCATEGORY_INFO> categoryinfoList, HAPPINESSCATEGORY HAPPINESSCATEGORY){

        this.categoryinfoList = categoryinfoList;
        this.activityContext = HAPPINESSCATEGORY;

    }

    // this.categoryinfoList = categoryinfoList; 이 문장은
    // HappinessCategoryAdapter 객체의 categoryinfoList 속성 = categoryinfoList 매개변수 형태가 되어 HppinessCategoryAdapter 객체의 속성에 값을 입력하게 되는 것이다.
    // this는 자기 자신이 되는 것이다.

    // HAPPINESSCATEGORY_INFO의 객체인 categoryinfoList() 속성은 categoryinfoList를 갖는다. 저 객체가 저 데이터값들을 갖는다는 말이다.
    // 어레이리스트 클래스로 HAPPINESSCATEGORY_INFO의 데이터들을 쌓을 수 있는 categoryinfoList라는 객체를 - 저장공간을 만들어줬다.


    // onCreateViewHolder()함수는 RecyclerView의 행을 표시하는데 사용되는 레이아웃 xml을 가져오는 역할
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_happinesscategory, parent, false);

        return new CategoryViewHolder(v);
    }


    // onBindViewHolder()함수에서 RecyclerView의 행에 보여질 TextView를 설정
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        CategoryViewHolder categoryviewHolder = (CategoryViewHolder) holder ;

        categoryviewHolder.tvCategoryName.setText(categoryinfoList.get(position).category_title );

        Log.v("onBindViewHolder","categoryinfoList" + categoryinfoList );

        categoryviewHolder.ibEditDeleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.v("onBindViewHolder","categoryinfoList" + categoryinfoList );

                // 다이어로그를 디자인하는 건데, activityContext(해피니스 카테고리) 위에 띄어준다.
                // 점 세 개 있는 버튼을 클릭하면 카테고리 수정과 삭제를 선택할 수 있는 다이어로그다.
                final AlertDialog.Builder editdeletebuilder = new AlertDialog.Builder(activityContext);

                // 다이어로그의 제목은 행복카테고리다.
                editdeletebuilder.setTitle("행복카테고리");

                // 다이어로그의 리스트에 수정, 삭제라는 옵션을 준다.
                String editdelete[] = {"수정", "삭제"};

                // setitems : 표시할 항목의 배열과 Dialoginterface.Onclicklistener를 매개 변수로 받는다.
                // onclicklistener는 사용자가 항목을 선택하는 경우에 실행되는 동작을 정의
                editdeletebuilder.setItems(editdelete, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int choose) {

                        if(choose == 0){
                            // 0번 수정을 클릭하면 글쓰기 화면으로 들어가서 입력했던 값을 수정할 수 있도록
                            // 수정할 수 있는 다이어로그가 나온다.
                            AlertDialog.Builder editdeletebuilder2 = new AlertDialog.Builder(activityContext);

                            // 다이어로그에 category_editbox_dialog.xml 파일로 디자인해준다.
                            View view = LayoutInflater.from(activityContext).inflate(R.layout.category_editbox_dialog, null, false);

                            // 행복카테고리 다이어로그에 레이아웃을 설정시켜줌
                            editdeletebuilder2.setView(view);

                            // category_editbox_dialog.xml 파일에 있는 write_categoryname EditText를 이 클래스로 가져온다.
                            final EditText editCategoryName = (EditText) view.findViewById(R.id.write_categoryname);
                            final Button edit_category_button = (Button) view.findViewById(R.id.add_category_button);

                            // 해당 줄에 입력되어 있던 데이터를 불러와서 다이얼로그에 보여준다.
//                            editCategoryName.setText(categoryinfoList.get(getAdapterPosition()).getCategoryName());
                            Log.v("arraylist 알림", "데이터 확인하기"+ categoryinfoList + categoryinfoList.size());


                            // editcategorydialog 수정할수 있는 다이어로그를 띄어준다.
                            final AlertDialog editcategorydialog = editdeletebuilder2.create();

                            // 수정하겠다는 버튼을 누른다.
                            edit_category_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                        String strCategoryName = editCategoryName.getText().toString();

//                                    HAPPINESSCATEGORY_INFO editcategoryDATA = new HAPPINESSCATEGORY_INFO(strCategoryName);

//                                    categoryinfoList.set(getAdapterPosition(), editcategoryDATA);
//                                    notifyItemChanged(getAdapterPosition());

                                    editcategorydialog.dismiss();
                                }
                            });

                            editcategorydialog.show();


                        }else{
                            // 나머지 삭제를 클릭하면 그냥 삭제되도록

//                            categoryinfoList.remove(getAdapterPosition());
//                            notifyItemRemoved(getAdapterPosition());
//                            notifyItemRangeChanged(getAdapterPosition(), mList.size());

                        }

                    }
                });

                editdeletebuilder.show(); // 알림창 띄우기

            }

        }) ;

    }

    // getItemCount()함수는 RecyclerView의 행 갯수를 리턴
    @Override
    public int getItemCount() {

        return (null != categoryinfoList ? categoryinfoList.size() : 0);

    }


}
