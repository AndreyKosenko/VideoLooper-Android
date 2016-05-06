package com.example.mc.videolooper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.app.Activity;

import java.io.File;

public class Photo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        String photoPath=getIntent().getStringExtra("path");

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
        ImageView photo=(ImageView)findViewById(R.id.photo);
        photo.setImageBitmap(bitmap);
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent= new Intent(getApplicationContext(),Video.class);
                    int current_pos=getIntent().getIntExtra("current_pos",0);
                    intent.putExtra("current_pos",current_pos);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }
}
