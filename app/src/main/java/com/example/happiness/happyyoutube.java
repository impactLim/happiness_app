package com.example.happiness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

// youtube android plyaer api https://developers.google.com/youtube/android/player/?hl=ko
// http://mystudyroom.net/2017/10/13/안드로이드-6-유튜브-재생하기/
// http://minpaprograming.blogspot.com/2018/01/android-studio-api.html
// https://underground2.tistory.com/8
// https://docko.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%8A%A4%ED%8A%9C%EB%94%94%EC%98%A4-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%ACjar-%ED%8C%8C%EC%9D%BC-%EC%B6%94%EA%B0%80%ED%95%98%EA%B8%B0
// https://soo84.tistory.com/entry/android-youtube-player-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0

// https://debuglog.tistory.com/59
// https://hs36.tistory.com/37
// https://ondestroy.tistory.com/49

public class happyyoutube extends YouTubeBaseActivity {

    YouTubePlayerView youTubeView;
    Button button;
    YouTubePlayer.OnInitializedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happyyoutube);

        button = (Button) findViewById(R.id.button);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);

        //리스너 등록부분
        listener = new YouTubePlayer.OnInitializedListener() {

            //초기화 성공시
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("TXxxZHGYWqc");
                //url의 맨 뒷부분 ID값만 넣으면 됨
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }

        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //첫번째 인자는 API키값 두번째는 실행할 리스너객체를 넘겨줌
                youTubeView.initialize("AIzaSyCv5YMhnIXMUHyG08pxNVEP6TQm2ffTifg", listener);
            }
        });


    }
}