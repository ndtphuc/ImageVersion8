package com.example.imageversion8;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<Integer> imageResources;
    private Context context;
    private int selectedPosition = -1;
    private OnImageClickListener onImageClickListener;
    private RecyclerView recyclerView;

    public ImageAdapter(List<Integer> imageResources, OnImageClickListener listener, RecyclerView recyclerView) {
        this.imageResources = imageResources;
        this.onImageClickListener = listener;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        int imageResource = imageResources.get(position);
        Drawable drawable = context.getDrawable(imageResource);
        holder.imageView.setImageDrawable(drawable);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onImageClickListener != null) {
                    onImageClickListener.onImageClick(position);
                }
            }
        });

        // Apply zoom effect to the selected image
        if (position == selectedPosition) {
            holder.imageView.setScaleX(1.4f); // Adjust the scale factor as needed
            holder.imageView.setScaleY(1.4f);
        } else {
            holder.imageView.setScaleX(1.0f); // Reset to normal size
            holder.imageView.setScaleY(1.0f);
        }
    }

    @Override
    public int getItemCount() {
        return imageResources.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageListImageView);
        }
    }

    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();

        // Scroll the RecyclerView to the selected position
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(position);
        }
    }
}
