package com.example.happiness;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myhappinesscommunityAdapter  extends RecyclerView.Adapter<myhappinesscommunityAdapter.ViewHolder> {

    //        이제 리사이클러뷰 어댑터(RecyclerView.Adapter)를 상속받은 클래스를 추가하고, 필수 구현 메서드를 오버라이드합니다.

    private Context mmContext;

    private static final int REQ_EDIT_CONTENTS_MYCOMMUNITY = 2;

    private ArrayList<MYHAPPINESSCOMMUNITY_DATA> Myhappinesscommunity_DataArrayList;

    myhappinesscommunityAdapter(ArrayList<MYHAPPINESSCOMMUNITY_DATA> myhappinesscommunity_dataArrayList,MYHAPPINESSCOMMUNITY MYHAPPINESSCOMMUNITY){
        Myhappinesscommunity_DataArrayList = myhappinesscommunity_dataArrayList;
        mmContext = MYHAPPINESSCOMMUNITY;
    }

    // context는 시스템이 관리하고 싶은 정보에 접근한다.
    // arraylist에서 recyleritem 클래스만 사용할 수 있다!!
    // 생성자에서 데이터 리스트 객체를 전달받음.
    // 생성자에서 class도 넣을 수 있고 list도 넣을 수 있다. activity도 넣을 수 있다.

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_contents;
        ImageButton btnEditcomplete;
        ImageView iv_imageview;

        public ViewHolder(View itemView) {
            super(itemView);

            this.tv_title = itemView.findViewById(R.id.MyhappinessCommunity_title);
            this.tv_contents = itemView.findViewById(R.id.MyhappinessCommunity_contents);
            this.btnEditcomplete = itemView.findViewById(R.id.item_button);
            this.iv_imageview = itemView.findViewById(R.id.recycler_image);

        }
    }


//        onCreateViewHolder() : 뷰홀더 객체 생성.

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_item_happinesscommunity, parent, false) ;
        myhappinesscommunityAdapter.ViewHolder vh = new myhappinesscommunityAdapter.ViewHolder(view) ;

        //layout inflater 선언 - recyclerview_item.xml 파일을 객체로 만들어서 쓸 수 있게

        Log.v("feed adapter 알림", "recyclerview는 adapter에게 viewholder 객체를 받는다.");

        return vh;

    }

//        onBindViewHolder() : 데이터를 뷰홀더에 바인딩.

    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        MYHAPPINESSCOMMUNITY_DATA item = Myhappinesscommunity_DataArrayList.get(position);
        //  position에 해당하는 mData를 뷰홀더의 item뷰에 표시

        //실제 각 뷰 홀더에 들어갈 데이터를 연결해줌.
        holder.tv_title.setText(item.getMyhappinesscommunityTitle());

        holder.tv_contents.setText(item.getMyhappinesscommunityContents());

//        holder.iv_imageview.byte;
//        holder.iv_imageview.setImageURI();

//        Uri selectedImageUri = Uri.parse(geturi);

        if(Uri.parse(item.getMyhappinesscommunityImageUrlStr()) != null){
            holder.iv_imageview.setImageURI(Uri.parse(item.getMyhappinesscommunityImageUrlStr()));
        }
        // byte를 bitmap으로 바꾸는 걸 여기서 해야하나? ㄴㄴ 여기선 iv_imageview로 참조만 해주고 setimagebitmap으로 보여주기
//        holder.iv_imageview.setImageBitmap(item.getMyhappinesscommunityImage());


//        Glide.with(holder.itemView.getContext()).load(item.getMyhappinesscommunityImageUrlStr()).into(holder.iv_imageview);
//        if(item.getPathFoto()!=null){
//            File f = new File(c.getPathPhoto());
//            Bitmap b = decodeFile(f);
//            holder.photo.setImageBitmap(b);
//        } else {
//            holder.photo.setImageBitmap(null);
//        }

        holder.btnEditcomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String st[] = {"수정", "삭제"};

                AlertDialog.Builder builder = new AlertDialog.Builder(mmContext);

//              어댑터에선 activity를 다룰 수 없다.
//              context를 활용해 어댑터에서 activity를 이용할 수 있게.

                builder.setTitle("행복커뮤니티");
                // 제목 설정

                builder.setItems(st, new DialogInterface.OnClickListener(){
                    // setitems : 표시할 항목의 배열과 Onclicklistener를 매개 변수로 받는다.
                    // onclicklistener는 사용자가 항목을 선택하는 경우에 실행되는 동작을 정의

                    @Override
                    public void onClick(DialogInterface dialog, int choose) {

                        if(choose == 0){

                            // 0번 수정을 클릭하면 글쓰기 화면으로 들어가서 입력했던 값을 수정할 수 있도록
                            // ｍｙｃｏｍｍｕｎｉｔｙｄ　ａ에서 edit activity로 넘어갈 수 있게 intent 생성자 지정
                            Intent intent = new Intent(mmContext,MYHAPPINESSCOMMUNITY_EDIT.class);

                            // "key_mycommunitytitle"이라는 key와 title에 입력된 값을 edit 화면에 전달해준다.
                            intent.putExtra("key_mycommunitytitle",Myhappinesscommunity_DataArrayList.get(position).getMyhappinesscommunityTitle()); // holder.title.getText(); 대신에 mData.get(position).getTitleStr())
                            Toast.makeText(mmContext, "수정할 포스팅 위치값 : " + position + " title에 들어간 값 " + Myhappinesscommunity_DataArrayList.get(position).getMyhappinesscommunityTitle() + " contents에 들어간 값 "+ Myhappinesscommunity_DataArrayList.get(position).getMyhappinesscommunityContents() , Toast.LENGTH_SHORT).show();
                            Log.v("myhappinesscommunityAdapter 수정 알림","" + Myhappinesscommunity_DataArrayList.get(position).getMyhappinesscommunityTitle() +"   " + position );

                            // "key_mycommunitycontents"라는 key와 contents에 입력된 값을 edit 화면에 전달해준다.
                            intent.putExtra("key_mycommunitycontents",Myhappinesscommunity_DataArrayList.get(position).getMyhappinesscommunityContents());
                            Log.v("myhappinesscommunityAdapter 수정 알림","" + Myhappinesscommunity_DataArrayList.get(position).getMyhappinesscommunityContents() +"   " + position );

                            // 이미지 넣어주기
                            intent.putExtra("key_image",Myhappinesscommunity_DataArrayList.get(position).getMyhappinesscommunityImageUrlStr());
                            Log.v("myhappinesscommunityAdapter 수정 알림","" + Myhappinesscommunity_DataArrayList.get(position).getMyhappinesscommunityImageUrlStr() +"   " + position );

                            // "mycommunity_title_position"이라는 key와 위치값을 전달해준다.
                            intent.putExtra("mycommunity_title_position", position);
                            Log.v("myhappinesscommunityAdapter 수정 알림"," " + position);

                            // adapter에서 startActivityforresult를 호출하는 방식이 조금 다르다.
                            Log.v("myhappinesscommunityAdapter 수정 알림","여기서 막힘1");
                            ((Activity) mmContext).startActivityForResult(intent, REQ_EDIT_CONTENTS_MYCOMMUNITY);
                            Log.v("myhappinesscommunityAdapter 수정 알림","여기서 막힘2");

                        }else{
                            // 나머지 삭제를 클릭하면 그냥 삭제되도록
                            // 해당 position값을 삭제하고
                            Myhappinesscommunity_DataArrayList.remove(position);

                            // 특정 영역의 데이터를 제거할 경우, 특정 Position에 데이터를 하나 제거할 경우
                            notifyItemRemoved(position);

                            // https://rojhw.tistory.com/16

                            Toast.makeText(mmContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show();

                            notifyDataSetChanged();


                        }
                    }
                });

                builder.show(); // 알림창 띄우기

            }
        });

        Log.v("feed adapter 알림", "전에 Adapter에게 받았던 ViewHolder 객체와 리스트에서 해당 ViewHolder의 위치를 인자로 전달한다. Adapter는 인자로 받은 위치에 맞는 데이터를 찾은 후 그 것을 ViewHolder의 View에 결합");

    }
//        getItemCount() : 전체 아이템 갯수 리턴.

    @Override
    public int getItemCount() {
        return (null != Myhappinesscommunity_DataArrayList ? Myhappinesscommunity_DataArrayList.size() : 0);
    }

}
