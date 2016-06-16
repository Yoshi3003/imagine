package com.jitrapon.imagine.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jitrapon.imagine.R;
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


    /**
     * View holder used to contain the image category card
     */
    private static class PhotoViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;
        TextView username;

        public PhotoViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.photo_title);
            image = (ImageView) itemView.findViewById(R.id.photo_image);
            username = (TextView) itemView.findViewById(R.id.photo_username);
        }

        public void attachClickListener(final Photo photo, final ItemClickedListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClicked(photo);
                }
            });
        }
    }

    public GalleryAdapter(Context context, List<Photo> photos, ItemClickedListener listener) {
        this.photos = photos;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.card_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Photo photo = photos.get(position);
        PhotoViewHolder viewHolder = (PhotoViewHolder) holder;

        viewHolder.attachClickListener(photo, listener);

        viewHolder.title.setText(photo.name);
        viewHolder.username.setText(photo.user.username);
        if (photo.images != null && photo.images.size() > 0)
            Glide.with(context)
                    .load(photo.images.get(0).url)
                    .error(R.drawable.error)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .crossFade(400)
                    .fitCenter()
                    .into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return photos == null ? 0 : photos.size();
    }
}
