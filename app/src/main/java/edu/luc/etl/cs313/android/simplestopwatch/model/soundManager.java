package edu.luc.etl.cs313.android.simplestopwatch.model;

import android.content.Context; // this is a library that adds media such as images/video/sound
import android.content.res.AssetFileDescriptor; //decrypts binary mp3 file into java
import android.media.MediaPlayer; //plays the mp3
public class soundManager {
    private final Context context;
    private MediaPlayer mediaPlayer;
    private boolean alarmOn = false;

    public soundManager(Context context) {
        this.context = context;
    }

    public boolean isAlarmOn() {
        return alarmOn;
    }

    public void play() {

        if (context == null) {//added this because tests error occur. "java.lang.NullPointerException: Cannot invoke "android.content.Context.getAssets()" because "this.context" is null"

            System.out.println("SoundManager: No context, skipping sound.");
            return;
        }
        try {
            AssetFileDescriptor afd = context.getAssets().openFd("beep-06.mp3");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            /*mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    //.setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build());*/
            //trying out some sound styles above.
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
            mediaPlayer.start();
            alarmOn = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        alarmOn = false;
    }
}
