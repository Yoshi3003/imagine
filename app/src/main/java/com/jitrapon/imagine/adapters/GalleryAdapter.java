package com.jitrapon.imagine.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jitrapon.imagine.interfaces.ItemClickedListener;
import com.jitrapon.imagine.models.Photo;

import java.util.List;

/**
 * Adapter for the recyclerview in the Gallery activity to display list of photos.
 */
public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Photo> photos;

    private ItemClickedListener listener;

    private Context context;

    public GalleryAdapter(Context context, List<Photo> photos, ItemClickedListener listener) {
        this.photos = photos;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return photos == null ? 0 : photos.size();
    }
}
