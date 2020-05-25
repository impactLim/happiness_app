package com.example.happiness;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SoundPool sound;
    int soundBtn;
    int streamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        sound = new SoundPool(1, AudioManager.STREAM_ALARM, 0);
//        soundBtn = sound.load(this, R.raw.buttonbgmogg, 1);


//        Log.v("알림","main 생명주기 oncreat!");
//        Toast.makeText(getApplicationContext(), "main 생명주기 oncreat!", Toast.LENGTH_SHORT).show();

        Button btn_login = (Button) findViewById(R.id.login_button);
        btn_login.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
//                        streamId = sound.play(soundBtn, 1.0F, 1.0F,  0,  0,  1.0F);
//                        sound.stop(streamId);

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                        startActivity(intent);

                    }
                }
        );

        Button btn_join = (Button) findViewById(R.id.join_button);
        btn_join.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
//                        streamId = sound.play(soundBtn, 1.0F, 1.0F,  0,  0,  1.0F);
//                        sound.stop(streamId);

                        Intent intent = new Intent(getApplicationContext(), joinActivity.class);

                        startActivity(intent);

                    }
                }

        );

        Button btn_checkweather = (Button) findViewById(R.id.go_weather);
        btn_checkweather.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), checkweather.class);

                        startActivity(intent);

                    }
                }

        );


    }



//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.v("알림","main 생명주기 onstart!");
//        Toast.makeText(getApplicationContext(), "main 생명주기 onstart!", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.v("알림","main 생명주기 onresume!");
//        Toast.makeText(getApplicationContext(), "main 생명주기 onresume!", Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.v("알림","main 생명주기 onpause!");
//        Toast.makeText(getApplicationContext(), "main 생명주기 onpause!", Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.v("알림","main 생명주기 onstop!");
//        Toast.makeText(getApplicationContext(), "main 생명주기 onstop!", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.v("알림","main 생명주기 ondestroy!");
//        Toast.makeText(getApplicationContext(), "main 생명주기 ondestroy!", Toast.LENGTH_SHORT).show();
//
//    }
//
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.v("알림","main 생명주기 onrestart!");
//        Toast.makeText(getApplicationContext(), "main 생명주기 onrestart!", Toast.LENGTH_SHORT).show();
//    }
}
