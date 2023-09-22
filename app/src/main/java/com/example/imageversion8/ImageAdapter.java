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
    private OnImageClickListener onImageClickListener; // Listener to handle item clicks

    public ImageAdapter(List<Integer> imageResources, OnImageClickListener listener) {
        this.imageResources = imageResources;
        this.onImageClickListener = listener;
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

        // Highlight the selected image
        holder.imageView.setSelected(position == selectedPosition);

        // Set a click listener to handle item clicks
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onImageClickListener != null) {
                    onImageClickListener.onImageClick(position);
                }
            }
        });
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

    // Method to set the selected position
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged(); // Refresh the RecyclerView to update the UI
    }
}
