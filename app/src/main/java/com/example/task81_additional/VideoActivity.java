package com.example.task81_additional;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import android.os.Bundle;

import com.example.task81_additional.databinding.ActivityInternetAudioBinding;
import com.example.task81_additional.databinding.ActivityVideoBinding;

public class VideoActivity extends AppCompatActivity {
    ActivityVideoBinding binding;

    ExoPlayer exoPlayer;

    String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Creating binding for using items from view
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        exoPlayer = new ExoPlayer.Builder(this).build();
        binding.playerView.setPlayer(exoPlayer);

        //MediaItem mediaItem = MediaItem.fromUri("https://www.youtube.com/watch?v=" + videoUrl + "&el=info&ps=default&eurl=&gl=US&hl=en");
        //MediaItem mediaItem = MediaItem.fromUri(Uri.parse("https://www.youtube.com/embed/" + videoUrl));
        //MediaItem mediaItem = MediaItem.fromUri(Uri.parse("https://www.youtube.com/watch?v=" + videoUrl));
        MediaItem mediaItem = MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4");
        exoPlayer.setMediaItem(mediaItem);
        //exoPlayer.addMediaItem(mediaItem1);
        exoPlayer.prepare();
        exoPlayer.play();

    }
}