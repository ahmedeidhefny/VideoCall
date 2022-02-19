package com.ahmedeid.videocall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ahmedeid.videocall.databinding.ActivityLoginBinding;
import com.ahmedeid.videocall.databinding.ActivityMainBinding;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUi();
        initClickAction();
        initData();
    }

    private void initUi() {
        URL serverURL ;

        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOption = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL)
                    .setAudioMuted(false)
                    .setVideoMuted(false)
                    .setAudioOnly(false)
                    .setWelcomePageEnabled(false)
                    .build();

            JitsiMeet.setDefaultConferenceOptions(defaultOption);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void initClickAction() {
        binding.btnJoin.setOnClickListener(view->{
            JitsiMeetConferenceOptions option = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(binding.etCode.getText().toString().trim())
                    .setAudioMuted(false)
                    .setVideoMuted(false)
                    .setAudioOnly(false)
                    .setWelcomePageEnabled(false)
                    .build();

            JitsiMeetActivity.launch(MainActivity.this, option);
        });
    }

    private void initData() {
    }
}