package com.example.happiness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//        ImageAdpater.class를 선언하고, RecyclerView.Adapter를 상속 받음.
//        출처: https://mommoo.tistory.com/2 [개발자로 홀로 서기]
//        ImageAdpater안에 직접 구현한 ViewHolder 클래스를 제네릭으로 받는 구조이다.
//        물론 직접 만드는 ViewHolder도 ReclerView.ViewHolder를 상속받아야 한다.

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private ArrayList<ImageData> adapterImageDataset;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    // 리스트 항목 하나의 view를 만들고 보존하는 일을 한다.
    // 리사이클러뷰에 들어갈 뷰 홀더, 그리고 그 뷰홀더에 들어갈 아이템들을 지정

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextView;


        // ViewHolder 클래스는 RecyclerView의 item에 들어갈 itemView를 받은후 그 itemView 안에 있는 ImageView와 TextView를 초기화 한다.
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = (ImageView)itemView.findViewById(R.id.xml_image);
            mTextView = (TextView)itemView.findViewById(R.id.xml_text);
        }
    }

    // 매개변수로 ArrayList<ImageData> 객체를 받는다. ImageData 클래스는 data-set을 위한 클래스
    public ImageAdapter(ArrayList<ImageData> arrayImageDataset) {
        adapterImageDataset = arrayImageDataset;
    }

//        onCreateViewHolder() : 뷰홀더 객체 생성.
//        onBindViewHolder() : 데이터를 뷰홀더에 바인딩.
//        getItemCount() : 전체 아이템 갯수 리턴.

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        // 앞서 말한 ViewHolder에 넣어줄 view를 찾는 과정이다. recyclerview_item_happinessimagefeed라는 xml 파일에는 리사이클러뷰 안에 들어있는 imageview와 textview가 들어간다.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_happinessimagefeed, parent, false);

        // imageview와 textview를 넣기 위한 viewholder를 선언해준다.
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    // RecyclerView의 item의 셋팅과 사용자가 스크롤링 할때, 호출되는 메서드
    // 우리가 원하는 데이터가 포지션별로 ArrayList<ImageData>에 저장되어 있다.
    // 이러한 데이터를 포지션별로 보여주는 것을 보장해준다.

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mTextView.setText(adapterImageDataset.get(position).text);
        holder.mImageView.setImageResource(adapterImageDataset.get(position).image);

    }

    @Override
    public int getItemCount() {
        return adapterImageDataset.size();
    }

    // 우리가 원하는 image와 text 데이터를 보관하기 위한 데이터 저장소 개념

    static class ImageData {
        public String text;
        public int image;

        public ImageData(String Text, int Image) {
            this.text = Text;
            this.image = Image;
        }
    }
}
