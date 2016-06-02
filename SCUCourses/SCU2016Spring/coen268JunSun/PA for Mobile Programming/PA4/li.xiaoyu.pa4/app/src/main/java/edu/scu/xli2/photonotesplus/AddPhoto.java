package edu.scu.xli2.photonotesplus;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Xiaoyu on 5/10/16.
 */
public class AddPhoto extends AppCompatActivity {
    private EditText caption_edit;
    private Button take_photoBtn;
    private Button save_photoBtn;
    private Uri imageUri;
    private static final int TAKE_PICTURE = 1;
    private int maxId;
    private static final String fileExt = ".jpg";
    private PhotoDbHelper dbHelper;
    Cursor cursor;
    String fileName;  //original fileName
    String thumbFile;

    //section for adding audio recording
    private static final String LOG_TAG = "AudioRecordTest";
    private static String voiceFile = null;

    private Button mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private Button mPlayButton = null;
    private MediaPlayer mPlayer = null;

    //section for adding geoLocation
    Location mLastLocation = new Location ("dummyprovider");
    String revisedPath = "dummyPath";

    //section for photo preview
    TouchDrawView preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_photo);
        caption_edit = (EditText) findViewById(R.id.captionEdit);
        take_photoBtn = (Button) findViewById(R.id.button_take);
        save_photoBtn = (Button) findViewById(R.id.button_save);
        dbHelper = new PhotoDbHelper(getApplicationContext());
        verifyStoragePermissions(this);
        preview = (TouchDrawView)findViewById(R.id.photo_preview);

        //audio part
        voiceFile = Environment.getExternalStorageDirectory().getAbsolutePath();
        voiceFile += "/audiorecordtest.3gp";
        mRecordButton = (Button)findViewById(R.id.button_record);
        mRecordButton.setText("Start recording");
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            boolean mStartRecording = true;
            @Override
            public void onClick(View v) {
                if (mStartRecording) {
                    mRecordButton.setText("Stop recording");
                    mRecordButton.setBackgroundColor(Color.RED);
                    startRecording();
                } else {
                    mRecordButton.setText("Start recording");
                    mRecordButton.setBackgroundColor(Color.WHITE);
                    stopRecording();
                }
                mStartRecording = !mStartRecording;

            }
        });

        mPlayButton = (Button)findViewById(R.id.button_play);
        mPlayButton.setText("Start playing");
        mPlayButton.setOnClickListener(new View.OnClickListener(){
            boolean mStartPlaying = true;
            public void onClick(View v){
                if (mStartPlaying) {
                    mPlayButton.setText("Stop playing");
                    mPlayButton.setBackgroundColor(Color.BLUE);
                    startPlaying();

                } else {
                    mPlayButton.setText("Start playing");
                    mPlayButton.setBackgroundColor(Color.WHITE);
                    stopPlaying();
                }
                mStartPlaying = !mStartPlaying;
            }

        });
    }
    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(voiceFile);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }
    private void stopRecording() {
        mRecorder.stop();
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
    }
    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(voiceFile);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }
    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void takePhotoFunc(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        maxId = dbHelper.getMaxRecID() + 1;
        File photo = new File(Environment.getExternalStorageDirectory(),  maxId + fileExt);
        String caption = caption_edit.getText().toString();
        MyPhoto myPhoto = new MyPhoto(caption, photo.getPath(),revisedPath, voiceFile, mLastLocation);
        dbHelper.add(myPhoto);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        fileName=photo.getPath();
        thumbFile=photo.getPath();
        startActivityForResult(intent, TAKE_PICTURE);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode != TAKE_PICTURE || resultCode != RESULT_OK) return;
//        //super.onActivityResult(requestCode, resultCode, data);
//            Uri selectedImage = imageUri;
//            getContentResolver().notifyChange(selectedImage, null);
//            ContentResolver cr = getContentResolver();
//            Bitmap bitmap;
//                try {
//                    bitmap = android.provider.MediaStore.Images.Media
//                            .getBitmap(cr, selectedImage);
//                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
//                        preview.setBackground(drawable);
//                    Toast.makeText(this, selectedImage.toString(), Toast.LENGTH_LONG).show();
//                    FileOutputStream out = null;
//                    try {
//                        out = new FileOutputStream(selectedImage.getPath());
//                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
//                        // PNG is a lossless format, the compression factor (100) is ignored
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                    }
//                } catch (Exception e) {
//                    Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
//                            .show();
//                    Log.e("Camera", e.toString());
//                }
//
//
//    }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode != TAKE_PICTURE || resultCode != RESULT_OK) return;
            try {
                Bitmap picture = BitmapFactory.decodeFile(fileName);
                Drawable drawable = new BitmapDrawable(getResources(), picture);
                preview.setBackground(drawable);
                Bitmap resized = ThumbnailUtils.extractThumbnail(picture, 120, 120);
                FileOutputStream fos = new FileOutputStream(thumbFile);
                resized.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.flush(); fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    public void savePhotoFunc(View view){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
