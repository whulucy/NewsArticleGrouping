package edu.scu.xli2.photonotesplus;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Created by Xiaoyu on 5/18/16.
 */
public class TouchHelperCallback extends ItemTouchHelper.Callback {

    PhotoAdapter mAdapter;
    private PhotoDbHelper dbHelper;

    public TouchHelperCallback(PhotoAdapter adapter, PhotoDbHelper dbHelper) {
        mAdapter = adapter;
        this.dbHelper = dbHelper;
    }
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // Set movement flags based on the layout manager
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        Log.i("xli2", "item moved from " + source.getAdapterPosition() + " to " + target.getAdapterPosition());
        return mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition(), dbHelper);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.i("xli2", "item at " + viewHolder.getAdapterPosition() + " is swiped away");
        mAdapter.onItemDismissed(viewHolder.getAdapterPosition(), dbHelper);
    }
}
