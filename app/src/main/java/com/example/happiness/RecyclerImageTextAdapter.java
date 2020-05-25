package com.example.happiness;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerImageTextAdapter extends RecyclerView.Adapter<RecyclerImageTextAdapter.ViewHolder> { // implements Filterable

//        이제 리사이클러뷰 어댑터(RecyclerView.Adapter)를 상속받은 클래스를 추가하고, 필수 구현 메서드를 오버라이드합니다.

//        onCreateViewHolder() : 뷰홀더 객체 생성.
//        onBindViewHolder() : 데이터를 뷰홀더에 바인딩.
//        getItemCount() : 전체 아이템 갯수 리턴.

    private ArrayList<RecyclerItem> mData ;
    private ArrayList<RecyclerItem> mDatafull ;

    private Context mContext;

    private static final int REQ_EDIT_CONTENTS = 2;

    SharedPreferences userid;
    String useridString;

    SharedPreferences testUserInfo;
    String thisUserinfo;

    String id_check;
    String receive_write_info;

    SharedPreferences UserUploadContentsPreferences;
    SharedPreferences.Editor useruploadContentsEditor;

    // write 화면에서 받아온 값 split한 값들
    String receive_title;
    String receive_contents;
    String receive_imageuri;
    Uri selectedImageUri;

    ArrayList<RecyclerItem> filteredList;
    // context는 시스템이 관리하고 싶은 정보에 접근한다.

    // arraylist에서 recyleritem 클래스만 사용할 수 있다!!
    // 생성자에서 데이터 리스트 객체를 전달받음.
    // 생성자에서 class도 넣을 수 있고 list도 넣을 수 있다. activity도 넣을 수 있다.

    // 메인피드에 들어가는 recycler 1
    RecyclerImageTextAdapter(ArrayList<RecyclerItem> list, myhappinessfeedActivity myhappinessfeedActivity) {

        mData = list;
        mContext=myhappinessfeedActivity;
        Log.v("feed main adapter 알림", "adapater 생성자에서 데이터 리스트 객체를 전달받음" + " mData : " +  mData );

//        mDatafull = new ArrayList<RecyclerItem>();
//        mDatafull.addAll(mData);

    }

    // 메인피드 검색화면에 들어가는 recycler 2
    // https://www.youtube.com/watch?v=sJ-Z9G0SDhc
//    public RecyclerImageTextAdapter(ArrayList<RecyclerItem> list2, myfeed_searchActivity myfeed_searchActivity) {
////        this.mData = list2;
//        mData = list2;
//        mDatafull = new ArrayList<>(list2); // 검색창의 리사이클러뷰2를 위한 작업
//        mContext = myfeed_searchActivity;
//        Log.v("feed search adapter 알림", "adapater 생성자에서 데이터 리스트 객체를 전달받음 2" + " mData : " +  mData + " mDatafull " + mDatafull);
//    }


    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    // 리스트 항목 하나의 view를 만들고 보존하는 일을 한다.
    // 리사이클러뷰에 들어갈 뷰 홀더, 그리고 그 뷰홀더에 들어갈 아이템들을 지정

    public class ViewHolder extends RecyclerView.ViewHolder   {

//        ImageView photo ;
        TextView title ;
        TextView contents;
        TextView dateNow;
        ImageView imageView;
        ImageButton btnEditcomplete;
        ImageButton btnlocationicon;

        TextView locationName;

        TextView locationaddress;
        TextView locationlatitude;
        TextView locationlongitude;

        public View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
//            photo = itemView.findViewById(R.id.photo);
            this.title = itemView.findViewById(R.id.item_title);
            this.contents = itemView.findViewById(R.id.item_contents);
            this.dateNow = itemView.findViewById(R.id.item_date);
            this.btnEditcomplete = itemView.findViewById(R.id.item_button);
            this.imageView = itemView.findViewById(R.id.item_image);
            this.locationName = itemView.findViewById(R.id.item_location);
            this.locationaddress = itemView.findViewById(R.id.item_address);
            this.locationlatitude = itemView.findViewById(R.id.item_latitude);
            this.locationlongitude = itemView.findViewById(R.id.item_longitude);
            this.btnlocationicon = itemView.findViewById(R.id.location_icon);

            mView = itemView;
        }

    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    // 리사이클러뷰에 들어갈 뷰 홀더를 할당하는 함수, 뷰홀더는 실제 레이아웃 파일(recycler_item)과 매핑되어야 한다.
    // 리사이클러뷰가 아이템뷰레이아웃인 (recycler_item xml 재활용할 수 있도록 이 viewholder라는 클래스에 xml에 대한 정보를 넣어준다.
    // xml 파일을 객체로 만들어서 쓰는게 layout inflater다. 객체로 만들려면 context(안드로이드 권한이)가 필요하다.
    // 아이템마다 고유값을 만들어주기 위해 xml파일을 객체로 만들어줘야 한다.

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false) ;
        RecyclerImageTextAdapter.ViewHolder vh = new RecyclerImageTextAdapter.ViewHolder(view) ;

        //layout inflater 선언 - recyclerview_item.xml 파일을 객체로 만들어서 쓸 수 있게

        Log.v("feed adapter 알림", "recyclerview는 adapter에게 viewholder 객체를 받는다.");

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    // 실제 각 뷰 홀더에 들어갈 데이터를 연결해주는 함수


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        //Here it is simply write onItemClick listener here
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Toast.makeText(context, position +"", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mContext,MYHAPPINESSFEEDIMAGECLICK.class);

                // 이미지 클릭 화면에 position 값을 넘겨준다.
                intent.putExtra("contents_position", position);

                mContext.startActivity(intent);

            }
        });

        RecyclerItem item = mData.get(position);
        //  position에 해당하는 mData를 뷰홀더의 item뷰에 표시

        //  holder.photo.setImageDrawable(item.getPhoto());
        holder.title.setText(item.getTitleStr());
        Log.v("adapter title 알림","item.getTitleStr()" + item.getTitleStr() );
        Log.v("adapter title 알림","adapter" + mData.size() + mData.toString());

        holder.contents.setText(item.getContentsStr());
        Log.v("adapter contents 알림","item.getContentsStr()" + item.getContentsStr());
        Log.v("adapter contents 알림","adapter" + mData.size() + mData.toString());

        holder.dateNow.setText(item.getDateStr());
        Log.v("adapter dateNow 알림","item.getDateStr()" + item.getDateStr() );
        Log.v("adapter dateNow 알림","adapter" + mData.size() + mData.toString());

        holder.locationName.setText(item.getLocationStr());
        Log.v("adapter locationName 알림","item.getLocationStr() " + item.getLocationStr() );
        Log.v("adapter locationName 알림","adapter" + mData.size() + mData.toString());

        holder.locationaddress.setText(item.getAddressStr());
        Log.v("adapter locationName 알림","item.getAddressStr() " + item.getAddressStr() );

        holder.locationlatitude.setText(item.getLatitudeStr());
        Log.v("adapter locationName 알림","item.getLatitudeStr() " + item.getLatitudeStr() );

        holder.locationlongitude.setText(item.getLongitudeStr());
        Log.v("adapter locationName 알림","item.getLongitudeStr() " + item.getLongitudeStr() );

        //실제 각 뷰 홀더에 들어갈 데이터를 연결해줌.
        if(Uri.parse(item.getImageStr()) != null){
            holder.imageView.setImageURI(Uri.parse(item.getImageStr()));
        }


        // 위치 아이콘 클릭 시 화면 이동 버튼
        holder.btnlocationicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent intent = new Intent(mContext,locationclickpage.class);

                // 아이콘 클릭 화면에 position 값을 넘겨준다.
                intent.putExtra("location_position", position);

                mContext.startActivity(intent);

            }
        });


        holder.btnEditcomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // https://onepinetwopine.tistory.com/116 mcontext 참고함
                // 회원정보에 다가가기 위한 id키 값
                // 로그인할 때 저장해준 아이디를 가지고 있는 쉐어드 파일을 불러온다.
                // SharedPreferences userid
                userid = mContext.getSharedPreferences("useridFile", MODE_PRIVATE);
                // 로그인할 때 저장해준 벨류값을 가지고 있는 key를 useridString에 넣어줘서 이 key로 언제든지 회원정보에 접근할 수 있게 한다.
                useridString = userid.getString("userid","");
                Log.v("mainfeed 리사이클러뷰 adapter로 아이디 받아왔는지 확인 알림"," id_check 키 값 가져왔는지 확인 : " + useridString);

                // 위의 id키값으로 회원정보 불러와준다.
                // 저장된 회원정보를 불러오기 위해 같은 네임파일을 찾음.
                // SharedPreferences testUserInfo
                testUserInfo = mContext.getSharedPreferences("testuserinfo",0);

                // 해당 아이디(KEY)로 해당 유저에 대한 정보를 가져올 수 있다.
                // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key) - useridString와 value 값을 불러와준다.
                thisUserinfo =  testUserInfo.getString(useridString, "");
                Log.v("mainfeed 리사이클러뷰 adapter에서 회원정보 받아왔는지 확인 알림"," thisUserinfo 키 값 가져왔는지 확인 : " + thisUserinfo);

                // 이름, 닉네임, 아이디, 패스워드 순으로 이루어진 thisUserInfo의 0,1,2의 2에 해당하는 세번째 값을 가지고 오기 위해서
                // id_check에 아이디를 담아줌
                // String id_check
                id_check = thisUserinfo.split(",")[2];
                Log.v("mainfeed 리사이클러뷰 adapter에서 아이디 받아왔는지 확인 알림 2 "," id_check 키 값 가져왔는지 확인 : " + id_check);
                // write화면에서 글쓴 값들 불러와주기 위해 해당 값이 담겨있는 shared 파일 불러옴 >> 근데 그러려면 키가 있어야 하는데 키가 아이디다. 해당 키를 어떻게 불러와줄 수 있을까

                // SharedPreferences UserUploadContentsPreferences
                UserUploadContentsPreferences = mContext.getSharedPreferences("UserUploadContentsPreferences", 0);

                // editor라는게 필요해서 만들어줬다.
                // SharedPreferences.Editor useruploadContentsEditor
                useruploadContentsEditor = UserUploadContentsPreferences.edit();

                // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key)와 value 값을 불러와준다.
                // String receive_write_info
                receive_write_info = UserUploadContentsPreferences.getString(id_check, "");
                Log.v("mainfeed 리사이클러뷰 adapter에서 알림", " 메인피드 write 창에서 제목, 내용, 이미지 가져왔는지 확인 알림 : " + receive_write_info);

                String st[] = {"수정", "삭제"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//              어댑터에선 activity를 다룰 수 없다.
//              context를 활용해 어댑터에서 activity를 이용할 수 있게.

                builder.setTitle("행복피드");
                // 제목 설정
                builder.setItems(st, new DialogInterface.OnClickListener(){
                // setitems : 표시할 항목의 배열과 Onclicklistener를 매개 변수로 받는다.
                // onclicklistener는 사용자가 항목을 선택하는 경우에 실행되는 동작을 정의

                    @Override
                    public void onClick(DialogInterface dialog, int choose) {

                        if(choose == 0){

                            // 0번 수정을 클릭하면 글쓰기 화면으로 들어가서 입력했던 값을 수정할 수 있도록
                            // mainfeedactivity에서 edit activity로 넘어갈 수 있게 intent 생성자 지정
                            Intent intent = new Intent(mContext,myhappinessfeed_edit.class);

                            // position 잘 받았는지, 입력값 전달해줄 준비가 되었는지 확인하는 toast
//                            Toast.makeText(mContext, "수정할 포스팅 위치값 : " + position + " title에 들어간 값 " + mData.get(position).getTitleStr() + " contents에 들어간 값 "+ holder.contents.getText() , Toast.LENGTH_SHORT).show();

                            // "key-title"이라는 key와 title에 입력된 값을 edit 화면에 전달해준다.
                            intent.putExtra("key_title",mData.get(position).getTitleStr()); // holder.title.getText(); 대신에 mData.get(position).getTitleStr())
                            Log.v("myhappinesscommunityAdapter title 수정 알림","" + mData.get(position).getTitleStr() +"   " + position );


                            // "key-contents"라는 key와 contents에 입력된 값을 edit 화면에 전달해준다.
                            intent.putExtra("key_contents",mData.get(position).getContentsStr());
                            Log.v("myhappinesscommunityAdapter contents 수정 알림","" + mData.get(position).getContentsStr() +"   " + position );

                            // 이미지 넣어주기
                            intent.putExtra("mainfeed_key_image",mData.get(position).getImageStr());
                            Log.v("myhappinesscommunityAdapter image 수정 알림","" + mData.get(position).getImageStr() +"   " + position );

                            // "title - position"이라는 key와 위치값을 전달해준다.
                            intent.putExtra("title_position", position);
                            Log.v("title_position putextra 알림","title_position : " + intent.putExtra("title_position", position));

                            // location 넣어주기
                            intent.putExtra("mainfeed_key_location",mData.get(position).getLocationStr());
                            Log.v("myhappinesscommunityAdapter location 수정 알림","" + mData.get(position).getLocationStr() +"   " + position );

                            // address 넣어주기
                            intent.putExtra("mainfeed_key_address",mData.get(position).getAddressStr());

                            // 위도 넣어주기
                            intent.putExtra("mainfeed_key_latitude",mData.get(position).getLatitudeStr());

                            // 경도 넣어주기
                            intent.putExtra("mainfeed_key_longitude",mData.get(position).getLongitudeStr());


                            // adapter에서 startActivityforresult를 호출하는 방식이 조금 다르다.
                            Log.v("mainfeed adapter 알림","myfeed_search imagebuton");
                            ((Activity) mContext).startActivityForResult(intent, REQ_EDIT_CONTENTS);

                        }else{
// String receive_write_info 가 뭔지 참고용
//                            // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key)와 value 값을 불러와준다.
//                            String receive_write_info = UserUploadContentsPreferences.getString(id_check, "");
//                            Log.v("mainfeed 리사이클러뷰 adapter에서 알림", " 메인피드 write 창에서 제목, 내용, 이미지 가져왔는지 확인 알림 : " + `receive_write_info);

                            // https://onepinetwopine.tistory.com/116 참고
                            String[] splitReceiveInfo = receive_write_info.split("&");
                            Log.v("mainfeed 리사이클러뷰 adapter 알림", " receive_write_info로 받아온 제목, 내용, 이미지 등의 긴 데이터들을 split 하겠다 : " + splitReceiveInfo);
                            // 리사이클러뷰 맨위에 쌓인 게시물의 position이 0, 근데 이 position값에 해당하는 split index는 맨 아래에 있는 값임
                            // 그래서 이걸 splitReceiveInfo.length-1-position 통해 거꾸로 해줘야함.
                            Log.v("mainfeed 리사이클러뷰 adapter 알림", "" + splitReceiveInfo[splitReceiveInfo.length - 1 - position]);

                            // 회원정보가 해당 아이디를 가지고 있다면,
                            if(testUserInfo.contains(id_check)) {

                                // 삭제하려는 값을 삭제는 못하고 대신 & + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                                String deleteContentsInfo = receive_write_info.replace("&" + splitReceiveInfo[splitReceiveInfo.length - 1 - position],"");
                                Log.v("mainfeed 리사이클러뷰 adapter에서 게시물 삭제 확인 알림", "" + deleteContentsInfo);

                                // 기존 value인 first_contents에 추가되는 값들(sb_contentsinfo.toString())을 더해준다.
                                useruploadContentsEditor.putString(id_check, deleteContentsInfo);

                                // 파일에 최종 반영함
                                useruploadContentsEditor.commit();

                                // 나머지 삭제를 클릭하면 그냥 삭제되도록
                                // 해당 position값을 삭제하고
                                mData.remove(position);
                                Log.v("mainfeed 리사이클러뷰 adapter에서 알림", " 삭제하려는 값 positon 확인 알림 : " + position);

                                // 특정 영역의 데이터를 제거할 경우, 특정 Position에 데이터를 하나 제거할 경우
                                notifyItemRemoved(position);

                                // https://rojhw.tistory.com/16
                                Toast.makeText(mContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show();

                                // 삭제하고 나서 아이템 포지션이 제대로 안받는 이유가 notifydatasetchanged 갱신을 안해줘서였음 ;;
                                notifyDataSetChanged();

                                // https://m.blog.naver.com/PostView.nhn?blogId=jjjjokk&logNo=220690888123&proxyReferer=https%3A%2F%2Fwww.google.com%2F
                                // https://stackoverflow.com/questions/52207263/when-deleting-view-with-swipe-in-recyclerview-doesnt-deletin-in-sharedpreferenc
                                // https://stackoverflow.com/questions/24131732/remove-item-from-sharedpreference-list-android/24131775
                                // https://stackoverflow.com/questions/45743287/android-how-to-clear-sharepreference-by-position-recyclerview

                            }
                        }
                    }
                });

                builder.show(); // 알림창 띄우기

            }
        });

        Log.v("feed adapter 알림", "전에 Adapter에게 받았던 ViewHolder 객체와 리스트에서 해당 ViewHolder의 위치를 인자로 전달한다. Adapter는 인자로 받은 위치에 맞는 데이터를 찾은 후 그 것을 ViewHolder의 View에 결합");

    }


    // getItemCount() - 전체 데이터 갯수 리턴.

    @Override
    public int getItemCount() {

        Log.v("feed adapter 알림", "전체 데이터 갯수 리턴");
        //        return mData.size();
        return (null != mData ? mData.size() : 0);

    }

//    public void filter(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        mData.clear();
//        if (charText.length() == 0) {
//            mData.addAll(mDatafull);
//        } else {
//            for (RecyclerItem RecyclerItem : mDatafull) {
//                String name = RecyclerItem.getContentsStr();
//                if (name.toLowerCase().contains(charText)) {
//                    mData.add(RecyclerItem);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }

}


//    // 검색화면에 있는 리사이클러뷰2 리사이클러뷰 검색을 위한 작업들
//    // https://www.youtube.com/watch?v=sJ-Z9G0SDhc
//    @Override
//    public Filter getFilter() {
//        return recyclerfilter; // 리사이클러뷰2를 위한 필터
//    }
//
//    private Filter recyclerfilter = new Filter(){
//
//        //performFiltering() 함수는 이름 그대로, 필터링을 수행하는 함수입니다. 즉, 필터링을 수행하는 루프를 이 함수에 구현한 다음, 필터링된 결과 리스트를 FilterResults에 담아서 리턴
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//
//            // ArrayList<RecyclerItem> filteredList
//            filteredList = new ArrayList<>(); // filteredlist에 값이 없다! 그럼 어떻게? 아니 이건 당연히 값이 없지. mDatafull에 값이 있어야 한다.
//            Log.v("메인피드 어댑터 검색값 확인 알림 0","filteredList : " + filteredList + " mDatafull : " + mDatafull );
//
//            // 내가 입력한 값이 constraint로 받아져온다. 내가 입력한 값이 없을 때이다.
//            if (constraint == null || constraint.length() == 0) {
//
//                filteredList.addAll(mDatafull);
//                Log.v("메인피드 어댑터 검색값 확인 알림 1","filteredList : " + filteredList + " mDatafull : " + mDatafull);
//
////                if(testUserInfo.contains(id_check)) {
////
////                    // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key)와 value 값을 불러와준다.
////                    receive_write_info = UserUploadContentsPreferences.getString(id_check, "");
////                    Log.v("mainfeed search 알림", " write페이지에서 제목, 내용, 이미지 가져왔는지 확인 알림 : " + receive_write_info);
////
////                    // https://onepinetwopine.tistory.com/116 참고
////                    String[] splitReceiveInfo = receive_write_info.split("&");
////                    Log.v("mainfeed search 알림", " write페이지에서 제목, 내용, 이미지 split 확인 : " + splitReceiveInfo);
////
////                    for (int i = 0; i < splitReceiveInfo.length; i++) {
////
////                        Log.v("mainfeed search split 알림", "" + splitReceiveInfo[i]);
////                        // 제목, 내용, 이미지 순으로 이루어진 receive_write_info 각각의 값들을 가져와준다.
////                        // String receive_title, String receive_contents, String receive_imageuri
////
////                        receive_title = splitReceiveInfo[i].split(",")[0];
////                        Log.v("mainfeed search 알림", " write페이지에서 제목 split 확인 : " + receive_title);
////
////                        receive_contents = splitReceiveInfo[i].split(",")[1];
////                        Log.v("mainfeed search 알림", " write페이지에서 내용 split 확인 : " + receive_contents);
////
////                        receive_imageuri = splitReceiveInfo[i].split(",")[2];
////                        Log.v("mainfeed search 알림", " write페이지에서 이미지 split 확인 : " + receive_imageuri);
////
////                        Log.v("mainfeed search 알림 2", "receive_title : " + receive_title + " receive_contents : " + receive_contents + " receive_imageuri : " + receive_imageuri);
////
////                        // uri 받아왔다.
////                        if (selectedImageUri != null) {
////                            selectedImageUri = Uri.parse(receive_imageuri);
////                        }
////
////                        // Log.v("mainfeed oncreat 알림", " write페이지에서 제목, 내용, 이미지 각각의 내용들 확인 : receive_title : " + receive_title + " receive_contents : " + receive_contents + " receive_imageuri : " + receive_imageuri);
////                        // write 창에서 받아오는 값이 null이 아닐 경우, 그리고 split한 값들이 다 null이 아닐 경우에만 데이터 쌓이도록.
////                        if (receive_write_info != null && receive_title != null & receive_contents != null && receive_imageuri != null) {
////
////                            // recycleritem 생성자를 사용하여 arraylist에 들어갈 데이터를 만들어 준다.
////                            RecyclerItem ri = new RecyclerItem(receive_title, receive_contents, receive_imageuri);
////
////                            //데이터를 삽입한다.
////                            filteredList.add(0, ri); //첫 줄에 삽입
////
////                            // 어댑터에서 RecyclerView에 데이터를 반영하도록 합니다.
////                            notifyDataSetChanged(); //변경된 데이터를 화면에 반영
////
////                        }
////
////                    }
////
////                }
//
//            }else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                Log.v("메인피드 어댑터 검색값 확인 알림 2","constraint.toString().toLowerCase().trim() : " + constraint.toString().toLowerCase().trim());
//
//
//                for (RecyclerItem item : mDatafull) { // for (RecyclerItem item : mDatafull) { // 원래값 :
//                    if (item.getTitleStr().toLowerCase().contains(filterPattern) || item.getContentsStr().toLowerCase().contains(filterPattern)) {
//
//                        filteredList.add(item);
//                        Log.v("메인피드 어댑터 검색값 확인 알림 3","filteredList : " + filteredList + " item :  " + item + " filterPattern : " + filterPattern + " mDatafull : " + mDatafull);
//
//                    }
//                }
//            }
//
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//            Log.v("메인피드 어댑터 검색값 확인 알림","results : " + results);
//
//            return results;
//        }
//
//        // performFiltering() 함수에서 필터링된 결과를 UI에 갱신시키는 역할을 수행
//        // 내가 입력한 값이 constraint로 받아져온다.
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//
//            mData.clear(); // filteredList.clear();
//            mData.addAll((ArrayList) results.values); // 원래값
//            filteredList  = ((ArrayList) results.values); // mData.addAll((ArrayList) results.values);
//            notifyDataSetChanged();
//            Log.v("메인피드 어댑터 검색값 확인 알림","" + mData + " constraint " + constraint);
//
//        }
//    };



//      dialog 띄워서 수정, 삭제 해주려고 했는데 각 선택지에 알맞게 어떻게 if문을 줄 수 있을까 고민하다가 다른 예제 찾아서 버림.
//
//                final List<String> ListItems = new ArrayList<>();
//                ListItems.add("수정");
//                ListItems.add("삭제");
//
//                // 리스트에 데이터 넣어줌
//
//
//                final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                // 어댑터에선 activity를 다룰 수 없다.
//                // context를 활용해 어댑터에서 activity를 이용할 수 있게.
//
//                builder.setTitle("행복피드");
//                // 제목 설정
//
//                // setitems : 표시할 항목의 배열과 Onclicklistener를 매개 변수로 받는다.
//                // onclicklistener는 사용자가 항목을 선택하는 경우에 실행되는 동작을 정의
//
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int pos) {
//                        String selectedText = items[pos].toString();
//                        Toast.makeText(mContext, selectedText, Toast.LENGTH_SHORT).show();
//
//
////                        builder.setPositiveButton("예",
////                                new DialogInterface.OnClickListener() {
////                                    public void onClick(DialogInterface dialog, int which) {
////                                        Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
////                                    }
////                                });
////
////                        builder.setNegativeButton("아니오",
////                                new DialogInterface.OnClickListener() {
////                                    public void onClick(DialogInterface dialog, int which) {
////                                        Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
////                                    }
////                                });
//
//                    }
//                });
//
//                builder.show(); // 알림창 띄우기