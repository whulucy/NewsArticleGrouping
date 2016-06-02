package edu.scu.xli2.photonotesplus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Xiaoyu on 5/10/16.
 */
public class DisplayPhoto  extends AppCompatActivity {
    private TextView caption;
    private ImageView imageView;
    private Button button_back;
    private int id_photo;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_photo);
        caption = (TextView)findViewById(R.id.caption_show);
        imageView = (ImageView)findViewById(R.id.image_display);
        button_back = (Button)findViewById(R.id.back_to_main);

//        caption.setSingleLine(false);
//        caption.setEllipsize(TextUtils.TruncateAt.END);
//        int n = 1; // the exact number of lines you want to display
//        caption.setLines(n);
        caption.setText(getIntent().getExtras().getString("Description"));
        String filePath = getIntent().getExtras().getString("photoPath");
        id_photo = Integer.parseInt(getIntent().getExtras().getString("id"));
        File imgFile = new  File(filePath);

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }

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

}
