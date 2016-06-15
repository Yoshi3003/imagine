package com.jitrapon.imagine.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jitrapon.imagine.R;
import com.jitrapon.imagine.interfaces.ItemClickedListener;
import com.jitrapon.imagine.models.Category;

import java.util.List;

/**
 * Adapter for the recyclerview in the main activity.
 */
public class ImageCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Category> categories;

    private ItemClickedListener listener;

    private Context context;

    /**
     * View holder used to contain the image category card
     */
    private static class ImageCategoryViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView background;

        public ImageCategoryViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.category_title);
            background = (ImageView) itemView.findViewById(R.id.category_image);
        }

        public void attachClickListener(final Category category, final ItemClickedListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClicked(category);
                }
            });
        }
    }

    public ImageCategoryAdapter(Context context, List<Category> categories, ItemClickedListener listener) {
        this.categories = categories;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageCategoryViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.card_image_category, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Category category = categories.get(position);
        ImageCategoryViewHolder viewHolder = (ImageCategoryViewHolder) holder;

        viewHolder.attachClickListener(category, listener);

        viewHolder.title.setText(category.asQueryParameter());
        Glide.with(context)
                .load(category.asBackground())
                .crossFade(200)
                .fitCenter()
                .into(viewHolder.background);
    }

    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }
}