package com.example.json.gamevoice;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btnMainActivityStatVoice)
    Button btnMainActivityStatVoice;
    @BindView(R.id.btnMainActivityStopVoice)
    Button btnMainActivityStopVoice;

    private SoundPool soundPool;
    private HashMap<Integer,Integer> hashMap;
    private int currStreamId;// 当前正播放的streamId


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initSoundPool();
        btnMainActivityStatVoice.setOnClickListener(this);
        btnMainActivityStopVoice.setOnClickListener(this);

    }

    public void initSoundPool(){
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC,0);
        hashMap = new HashMap<>();
        hashMap.put(1,soundPool.load(this,R.raw.musictest,1));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMainActivityStatVoice:

                //获取audioManager
               AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

                //获取当前音量
                float streamVolumeCurrent = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

                //获取系统最大音量
                float streamVolumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

                //计算得到播放音量
                float volume = streamVolumeCurrent/streamVolumeMax;

                // 调用SoundPool的play方法来播放声音文件

                currStreamId = soundPool.play(hashMap.get(1),volume,volume,1,0,1.0f);


               // 提示播放即时音效
                Toast.makeText(getBaseContext(),getResources().getString(R.string.start_voice), Toast.LENGTH_SHORT).show();

                break;
            case R.id.btnMainActivityStopVoice:

            soundPool.stop(currStreamId);
                // 提示停止播放
                Toast.makeText(getBaseContext(), getResources().getString(R.string.stop_voice), Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }
    }
}
