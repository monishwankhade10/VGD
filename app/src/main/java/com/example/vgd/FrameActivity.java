package com.example.vgd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.agora.rtc.RtcEngine;

public class FrameActivity extends AppCompatActivity {
    private RtcEngine mRtcEngine;
    private Boolean mMuted;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        getSupportActionBar().hide();

    }

    public void onLocalAudioMuteClicked(View view)
    {
        mMuted=!mMuted;
        mRtcEngine.muteLocalAudioStream(mMuted);
    }
    public void onCallClicked(View view)
    {
        endCall();
        Intent intent = new Intent(getBaseContext(),MainActivity2.class);
        startActivity(intent);

    }
    public void onVolumeUpClicked(View view)
    {

    }
    private void endCall()
    {

        mRtcEngine.leaveChannel();
    }
}
