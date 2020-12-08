package com.example.serviceboundmusic;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    private MyPlayer myPlayer;
    private IBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ServiceDemo", "Đã gọi onCreate()");

        myPlayer = new MyPlayer(this);
        binder = new MyBinder(); // do MyBinder được extends Binder

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        myPlayer.play();
        // trả về đối tượng binder cho ActivityMain
        return binder;

    }
    // Kết thúc một Service
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        myPlayer.stop();
        return super.onUnbind(intent);
    }

    // Xây dựng các phương thức thực hiện nhiệm vụ,
    // ở đây mình demo phương thức tua bài hát
    public void fastForward(){

        myPlayer.fastForward(60000); // tua đến giây thứ 120
    }
    public void fastStart(){

        myPlayer.fastStart();
    }

    public class MyBinder extends Binder {

        // phương thức này trả về đối tượng MyService
        public MyService getService() {

            return MyService.this;
        }
    }

}
// Xây dựng một đối tượng riêng để chơi nhạc
class MyPlayer {
    // đối tượng này giúp phát một bài nhạc
    private MediaPlayer mediaPlayer;
    private List<MediaPlayer> mediaPlayerList=new ArrayList<>();
    public MyPlayer(Context context) {
        // Nạp bài nhạc vào mediaPlayer

       mediaPlayer = MediaPlayer.create(
                context, R.raw.buonvuongmauao_nguyenhung);
        // Đặt chế độ phát lặp lại liên tục
        mediaPlayer.setLooping(true);

    }
    public void addMediaPlayer(MediaPlayer m){
       mediaPlayerList.add(m);
    }


    public void fastForward(int pos){
        //mediaPlayer.seekTo(pos);
        mediaPlayer.pause();

    }
    public void fastStart(){
        mediaPlayer.start();
    }
    public void fastForwardsToPos(int pos){
        mediaPlayer.seekTo(pos);


    }
    // phát nhạc
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    // dừng phát nhạc
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
