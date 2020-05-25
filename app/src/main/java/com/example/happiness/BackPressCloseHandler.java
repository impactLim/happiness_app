package com.example.happiness;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class BackPressCloseHandler {

    // 뒤로 가기 누르면 "뒤로 버튼을 한 번 더 누르면 종료되도록."

    // 0.    시간을 저장하는 변수(t) = 0;
    private long backKeyPressedTime = 0;
    Toast toast;
    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;

        // 1.    뒤로가기 버튼 (처음)클릭시 시간을 저장하는 변수(t) + 2000(2초)가 현재 시간보다 작다.
        } if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            ActivityCompat.finishAffinity(activity); // 뒤로 버튼 두 번 누르면 어플 종료된다.
//            activity.finish();
//            System.exit(0);
//            android.os.Process.killProcess(android.os.Process.myPid());
//            https://blog.asamaru.net/2015/12/15/android-app-finish/
//            https://dsnight.tistory.com/14

            toast.cancel();
        }
    }

    public void showGuide() {

            toast = Toast.makeText(activity, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();

    }
}
//    출처: https://dsnight.tistory.com/14 [Development Assemble]

