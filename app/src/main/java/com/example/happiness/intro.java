package com.example.happiness;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

// https://bitsoul.tistory.com/164
// http://blog.naver.com/PostView.nhn?blogId=777lover&logNo=10132439565

public class intro extends AppCompatActivity {

    Handler handler = new Handler();

    Runnable r = new Runnable() {
        @Override
        public void run() {
            // 4초뒤에 다음화면(MainActivity)으로 넘어가기 Handler 사용
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent); // 다음화면으로 넘어가기
            finish(); // Activity 화면 제거
            Log.v("intro 화면 끝남 알림 ", " ");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro); // xml과 java소스를 연결

        // https://abc0123.tistory.com/121 bar 없애기
        ActionBar bar = getSupportActionBar();
        bar.hide();

    } // end of onCreate

    @Override
    protected void onResume() {
        super.onResume();
        // 다시 화면에 들어왔을 때 예약 걸어주기
        handler.postDelayed(r, 3000); // 2.5초 뒤에 Runnable 객체 수행
        Log.v("intro handler 확인 알림 ", " ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 화면을 벗어나면, handler 에 예약해놓은 작업을 취소하자
        // 실행 취소하는 법
        handler.removeCallbacks(r); // 예약 취소
    }
}

//출처: https://bitsoul.tistory.com/164 [Happy Programmer~]