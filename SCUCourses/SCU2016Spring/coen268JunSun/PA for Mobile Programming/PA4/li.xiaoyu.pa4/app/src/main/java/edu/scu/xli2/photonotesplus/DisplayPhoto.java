package edu.scu.xli2.photonotesplus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

/**
 * Created by Xiaoyu on 5/10/16.
 */
public class DisplayPhoto  extends AppCompatActivity {
    private TextView caption;
    private ImageView imageView;
    private Button button_back;
    private int id_photo;
    private Button voice_play;
    private Button map_view;
    private String mLocation;
    MediaPlayer mPlayer;
    String voiceFile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_photo);
        caption = (TextView)findViewById(R.id.caption_show);
        imageView = (ImageView)findViewById(R.id.image_display);
        button_back = (Button)findViewById(R.id.back_to_main);
        caption.setText(getIntent().getExtras().getString("Description"));
        String filePath = getIntent().getExtras().getString("photoPath");
        id_photo = Integer.parseInt(getIntent().getExtras().getString("id"));
        File imgFile = new  File(filePath);
        voiceFile = getIntent().getExtras().getString("voiceFile");
        mLocation = getIntent().getExtras().getString("location");

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }

        voice_play = (Button)findViewById(R.id.voice_play);
        voice_play.setOnClickListener(new View.OnClickListener(){
            boolean mStartPlaying = true;
            public void onClick(View v){
                if (mStartPlaying) {
                    voice_play.setText("Stop playing");
                    voice_play.setBackgroundColor(Color.BLUE);
                    startPlaying();

                } else {
                    voice_play.setText("Start playing");
                    voice_play.setBackgroundColor(Color.WHITE);
                    stopPlaying();
                }
                mStartPlaying = !mStartPlaying;
            }

        });


//        map_view = (Button)findViewById(R.id.map_view);
//        map_view.setOnClickListener( new View.OnClickListener(){
//            public void onClick(View v){
//                Intent intent = new Intent(v.getContext(), MapViewActivity.class);
//                intent.putExtra("photoLocation", mLocation);
//                startActivity(intent);
//            }
//        });


    }

    /**
     * go back to main activity
     *
     * @param v
     */
    public void btnBackOnClick(View v) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(voiceFile);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("hello", "prepare() failed");
        }
    }
    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

}
