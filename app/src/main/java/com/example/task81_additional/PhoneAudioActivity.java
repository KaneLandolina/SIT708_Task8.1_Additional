package com.example.task81_additional;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;

import com.example.task81_additional.databinding.ActivityPhoneAudioBinding;

import java.io.IOException;

public class PhoneAudioActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    ActivityPhoneAudioBinding binding;

    MediaPlayer mediaPlayer;

    Boolean paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Creating binding for using items from view
        binding = ActivityPhoneAudioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    play(view);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        binding.buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause(view);
            }
        });

        binding.buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop(view);
            }
        });

    }

    public void play(View view) throws IOException {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                Uri musicUri = Uri.parse("content://com.android.providers.downloads.documents/document/msf%3A1000000035");
                if(mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    //mediaPlayer = MediaPlayer.create(this, musicUri);
                    mediaPlayer.setDataSource("/storage/emulated/0/Download/cinematic-music-sketches-11-cinematic-percussion-sketch-116186.mp3");
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            stopPlayer();
                        }
                    });
                }
                //mediaPlayer.setOnPreparedListener((MediaPlayer.OnPreparedListener) this);
                if(paused != true) {
                    mediaPlayer.prepare();
                }
                mediaPlayer.start();
                paused = false;
            } else { //request for the permission
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        } else {
            //below android 11=======
            startActivity(new Intent(this, MainActivity.class));
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }


    public void pause(View view) {
        if(mediaPlayer != null) {
            mediaPlayer.pause();
            paused = true;
        }
    }

    public void stop(View view) {
        stopPlayer();
    }

    public void stopPlayer() {
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }

}