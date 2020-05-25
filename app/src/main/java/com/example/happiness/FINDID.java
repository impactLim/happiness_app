package com.example.happiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FINDID extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findid);

        Button check_id = (Button) findViewById(R.id.check_ID);
        check_id.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                        startActivity(intent);

                    }
                }

        );
    }
}
