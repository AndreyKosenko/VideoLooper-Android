package com.example.mc.videolooper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Video extends Activity {

    String path;
    ArrayList<String>videoPath = new ArrayList<String>();
    ArrayList<String>photoPath = new ArrayList<String>();
    String ext = ".3gp";
    String ext1=".mp4";
    String ext2=".jpg";
    final Context context=this;
    VideoView videoView;

    int position=0;
    int vdnum; int phnum; int totalnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        path = Environment.getExternalStorageDirectory().getPath()+ File.separator+Environment.DIRECTORY_MOVIES;
        File files = new File(path);
        GenericExtFilter filter=new GenericExtFilter(ext);
        String[] list= files.list(filter);
        GenericExtFilter filter1=new GenericExtFilter(ext1);
        String[] list1=files.list(filter1);
        GenericExtFilter filter2=new GenericExtFilter(ext2);
        String[] list2=files.list(filter2);

        vdnum=list.length+list1.length;
        phnum=list2.length;
        totalnum=vdnum+phnum;
        position=getIntent().getIntExtra("current_pos",0);
        setContentView(R.layout.activity_video);
        if(totalnum<1)
        {
            new AlertDialog.Builder(context)
                    .setTitle("No Video/Photo")
                    .setMessage("Please copy video and photo files into /sdcard/Movies/.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else {
            int i=0;
            for ( i=0;i<list.length;i++)
                videoPath.add(i,list[i].toString());
            for (i=i;i<vdnum;i++)
                videoPath.add(i,list1[i].toString());
            for (int j=0;j<list2.length;j++)
                photoPath.add(j,list2[j].toString());
            videoView = (VideoView)findViewById(R.id.video);
            Random random = new Random();
            while (true)
            {
                int pos=random.nextInt(totalnum);
                if(position!=pos)
                {
                    position=pos; break;
                }
            }
            if(position<vdnum)
                playVideo(position);
            else
                playPhoto(position-vdnum);

        }
    }
    private void playVideo(int pos){
        String p=path+File.separator+videoPath.get(pos);
        videoView.setVideoPath(p);
        videoView.setMediaController(new MediaController(context));
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Random random = new Random();
                while (true)
                {
                    int pos=random.nextInt(totalnum);
                    if(position!=pos)
                    {
                        position=pos; break;
                    }
                }
                if(position<vdnum){
                    mp.reset();
                    String p=path+videoPath.get(position);
                    videoView.setVideoPath(p);
                    videoView.start();
                }else {
                    playPhoto(position-vdnum);
                }

            }
        });
    }
    private void playPhoto(int pos){
        String p=path+File.separator+photoPath.get(pos);
        Intent intent = new Intent(context,Photo.class);
        intent.putExtra("current_pos",position);
        intent.putExtra("path",p);
        startActivity(intent);
    }
}

