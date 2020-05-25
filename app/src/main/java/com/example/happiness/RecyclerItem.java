package com.example.happiness;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

//      RecyclerView의 한 줄에 보여줄 데이터를 클래스로 선언합니다. item_list.xml에서 정의한 TextView 개수에 맞추어야 합니다.

//      아이템 뷰 레이아웃을 구성하고 아이템뷰에 표시될 데이터를 저장할 클래스를 정의.
//      아이템 뷰는 하나의 이미지뷰와 두 개의 텍스트뷰로 구성.
//      아이템 데이터 클래스에는 이미지뷰를 위한 하나의 Drawable 변수와 텍스트뷰에 표시될 문자열을 저장하는 두 개의 String 변수를 정의.

//Adapter : 아이템에 대한 View 생성
//ViewHolder : 재활용 View에 대한 모든 서브 View를 관리
//LayoutManager : 아이템 항목 배치

public class RecyclerItem {

    private String titleStr;
    private String contentsStr;
    public String imageStr;

    // 현재시간을 msec 으로 구한다.
    private long now = System.currentTimeMillis();

    // 현재시간을 date 변수에 저장한다.
    private Date date = new Date(now);

    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
//    private SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    // 시연을 위해 ss까지 표현

    // dateNow 변수에 값을 저장한다.
    private String dateStr = sdfNow.format(date);

    public String locationStr;



    //    private int position;
    public String addressStr;
    public String latitudeStr;
    public String longitudeStr;

    // 값을 받고(get) set(설정)해주는 메소드

    //int position ?
    public String getTitleStr() {
        Log.v("recycleritem 알림","getTitleStr" + titleStr);

        return this.titleStr;
    }

    //int position?
    public String getContentsStr() {
        Log.v("recycleritem 알림","getContentsStr" + contentsStr );

        return this.contentsStr;
    }

    public String getImageStr() {
        return imageStr;
    }

//    public int getPosition(int position) {
//        return this.position; //position번째의 아이템을 얻을거야.
//    }


    public String getDateStr() {
        Log.v("recycleritem 알림","getDateStr" + dateStr );

        return this.dateStr;
    }

    public String getLocationStr() {
        return locationStr;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public String getLatitudeStr() {
        return latitudeStr;
    }

    public String getLongitudeStr() {
        return longitudeStr;
    }

    public RecyclerItem(String title, String contents, String imageurl, String location, String address, String latitude, String longitude) {

        titleStr = title;
        contentsStr = contents;
        imageStr = imageurl;
        locationStr = location;
        addressStr = address;
        latitudeStr = latitude;
        longitudeStr = longitude;

        Log.v("recycleritem 알림","title, contents, image, location name 생성자" + title + titleStr +contents +contentsStr +imageStr + imageurl + locationStr+ location);
        Log.v("recycleritem 알림 2 ","address + latitude + longitude 생성자 : " + "address : "+ address + " latitude : " + latitude + " longitude : " + longitude);

    }

}

//    이미지 관련
//    private Drawable photoDrawble;
//    public void setPhoto(Drawable photo){
//        photoDrawble = photo;
//    }

//    이미지 관련
//    public Drawable getPhoto(){
//        return this.photoDrawble;
//    }

//    settitle, setcontents 언제 씀?
//    public void setTitle(String title){
//        Log.v("recycleritem 알림","setTitle");
//
//        titleStr = title ;
//    }
//    public  void setContents(String contents){
//        Log.v("recycleritem 알림","setcontents");
//
//        contentsStr = contents;
//    }
