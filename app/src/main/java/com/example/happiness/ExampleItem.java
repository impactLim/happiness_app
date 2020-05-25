package com.example.happiness;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExampleItem {
    private String titleStr;
    private String contentsStr;
    public String imageStr;

    // 현재시간을 msec 으로 구한다.
    private long now = System.currentTimeMillis();

    // 현재시간을 date 변수에 저장한다.
    private Date date = new Date(now);

    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    private SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
//    private SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    // 시연을 위해 ss까지 표현

    // dateNow 변수에 값을 저장한다.
    private String dateStr = sdfNow.format(date);

//    private int position;


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


    public ExampleItem(String title, String contents, String imageurl) {

        titleStr = title;
        contentsStr = contents;
        imageStr = imageurl;

        Log.v("recycleritem 알림","title, contents 생성자" + title + titleStr +contents +contentsStr);

    }

}
