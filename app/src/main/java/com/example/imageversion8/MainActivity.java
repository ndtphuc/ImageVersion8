package com.example.imageversion8;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ImageAdapter.OnImageClickListener {

    private ViewPager2 viewPager;
    private Button btnPrevious, btnNext;
    private RecyclerView imageList;
    private ImageAdapter imageAdapter;
    private List<Integer> imageResources = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        imageList = findViewById(R.id.imageList);

        // Retrieve all image resources dynamically from the drawable folder
        Field[] drawables = R.drawable.class.getDeclaredFields();
        for (Field field : drawables) {
            try {
                if (field.getName().startsWith("image")) { // Assuming your image resource names start with "image"
                    int drawableId = field.getInt(null);
                    imageResources.add(drawableId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        imageAdapter = new ImageAdapter(imageResources, this); // Pass this activity as the listener
        imageList.setAdapter(imageAdapter);
        imageList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ImagePagerAdapter pagerAdapter = new ImagePagerAdapter(imageResources);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(new ZoomPageTransformer());

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() > 0) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() < imageResources.size() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Update the selected image in the RecyclerView
                imageAdapter.setSelectedPosition(position);
            }
        });
    }

    @Override
    public void onImageClick(int position) {
        // Handle item click: Change the selected image in the ViewPager2
        viewPager.setCurrentItem(position);
    }
}
