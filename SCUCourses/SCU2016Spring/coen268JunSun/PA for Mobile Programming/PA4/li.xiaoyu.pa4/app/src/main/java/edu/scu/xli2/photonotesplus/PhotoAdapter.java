package edu.scu.xli2.photonotesplus;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyMainViewHolder>{
    private final int THUMBSIZE = 150;
    private List<MyPhoto> myPhotoList;
    private PhotoDbHelper photoDbHelper;

    public PhotoAdapter(List<MyPhoto> myPhotoList, PhotoDbHelper dbHelper){
        this.myPhotoList = myPhotoList;
        this.photoDbHelper = dbHelper;

    }

    public class MyMainViewHolder extends RecyclerView.ViewHolder{
        TextView photoDescription;
        ImageView photoShow;
        Button button_voice;
        Button button_map;

        public MyMainViewHolder(View itemView){
            super(itemView);
            photoDescription = (TextView) itemView.findViewById(R.id.item_img_infor);
            photoDescription.setSingleLine(false);
            photoDescription.setEllipsize(TextUtils.TruncateAt.END);
            int n = 1; // the exact number of lines you want to display
            photoDescription.setLines(n);
            photoShow = (ImageView) itemView.findViewById(R.id.item_img_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("mymainviewholder", "click on ");
                    Cursor cursor = photoDbHelper.fetchAll();
                    cursor.moveToPosition(getAdapterPosition());
                    Intent intent = new Intent(v.getContext(), DisplayPhoto.class);
                    intent.putExtra("Description", cursor.getString(cursor.getColumnIndex("description")));
                    intent.putExtra("photoPath", cursor.getString(cursor.getColumnIndex("photoPath")));
                    intent.putExtra("revisedPath", cursor.getString(cursor.getColumnIndex("revisedPath")));
                    intent.putExtra("voiceFile", cursor.getString(cursor.getColumnIndex("voiceFile")));
                    intent.putExtra("location", cursor.getString(cursor.getColumnIndex("location")));
                    intent.putExtra("id", cursor.getString(cursor.getColumnIndex("_id")));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public MyMainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_row, parent, false);
        MyMainViewHolder holder = new MyMainViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyMainViewHolder holder, int position) {
        String photoDescript = myPhotoList.get(position).getDescription();
        String photoPath = myPhotoList.get(position).getPath();
        holder.photoDescription.setText(photoDescript);
        holder.photoShow.setImageBitmap(ThumbnailUtils
                .extractThumbnail(BitmapFactory.decodeFile(photoPath),
                        THUMBSIZE, THUMBSIZE));
    }


    @Override
    public int getItemCount() {
        return myPhotoList.size();
    }

    // called by touch helper callback
    public void onItemDismissed(int position, PhotoDbHelper dbHelper) {
        Cursor cursor = dbHelper.fetchAll();
        cursor.moveToPosition(position);
        int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));
        myPhotoList.remove(position);
        dbHelper.delete(id);
        dbHelper.close();
        notifyItemRemoved(position);
    }

    // called by touch helper callback
    public boolean onItemMove(int fromPosition, int toPosition, PhotoDbHelper dbHelper) {
        MyPhoto temp = myPhotoList.get(fromPosition);
        myPhotoList.set(fromPosition, myPhotoList.get(toPosition));
        myPhotoList.set(toPosition, temp);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

}