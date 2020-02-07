package com.eslam.bit68task.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.eslam.bit68task.R;
import com.eslam.bit68task.adapters.CategoriesAdapter;
import com.eslam.bit68task.models.Category;
import com.eslam.bit68task.networking.RetrofitClient;
import com.eslam.bit68task.utils.Tools;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CategoriesAdapter.OnItemClickListener  {

    private ViewPager viewPager;
    private LinearLayout layout_dots;
    private AdapterImageSlider adapterImageSlider;
    private Runnable runnable = null;
    private Handler handler = new Handler();
    private RecyclerView categoriesRecyclerView;
    private CategoriesAdapter categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();
        initViews();
        initRecyclerView();
        getCategories();
    }


    /**
     * Init ToolBar and StatusBar
     */
    private void initToolBar() {
        Tools.setSystemBarColor(this, R.color.colorWhite);
        Tools.setSystemBarLight(this);
    }

    /**
     * Init layout Views
     */
    private void initViews() {
        layout_dots = findViewById(R.id.layout_dots);
        viewPager = findViewById(R.id.pager);
        categoriesRecyclerView = findViewById(R.id.categories_recycler_view);
    }

    /**
     * Init RecyclerView and its Adapter
     */
    private void initRecyclerView() {
        categoriesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        categoriesRecyclerView.setNestedScrollingEnabled(false);

        //set data and list adapter
        categoriesAdapter = new CategoriesAdapter(this);
        categoriesRecyclerView.setAdapter(categoriesAdapter);
    }

    /**
     * Making Request to get Categories data
     */
    private void getCategories() {
        RetrofitClient.getInstance().getApi().getCatefories()
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        if (response.code() == 200) {
                            initComponent(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {

                    }
                });
    }

    /**
     * Get Categories from response and Init Slider and Categories List
     *
     * @param categories Categories from Response
     */
    private void initComponent(List<Category> categories) {

        List<String> categoriesImages = new ArrayList<>();
        for (Category category : categories) categoriesImages.add(category.getCategoryImg());
        initSliderComponent(categoriesImages);

        categoriesAdapter.insertCategories( categories);
    }

    /**
     * Initial Image Slider and its components
     *
     * @param images List of Images For The Slider
     */
    private void initSliderComponent(List<String> images) {

        adapterImageSlider = new AdapterImageSlider(this);

        adapterImageSlider.setImages(images);
        viewPager.setAdapter(adapterImageSlider);

        // displaying selected image first
        viewPager.setCurrentItem(0);
        addBottomDots(layout_dots, adapterImageSlider.getCount(), 0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
                addBottomDots(layout_dots, adapterImageSlider.getCount(), pos);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        startAutoSlider(adapterImageSlider.getCount());
    }

    /**
     * Setup Dots of Slider
     *
     * @param layout_dots Container layout of dots
     * @param size        The size of each dote
     * @param current     current dote is shown
     */
    private void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(ContextCompat.getColor(this, R.color.overlay_dark_10), PorterDuff.Mode.SRC_ATOP);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        }
    }

    /**
     * To start auto slide for Image Slider
     *
     * @param count Number of Images
     */
    private void startAutoSlider(final int count) {
        runnable = () -> {
            int pos = viewPager.getCurrentItem();
            pos = pos + 1;
            if (pos >= count) pos = 0;
            viewPager.setCurrentItem(pos);
            handler.postDelayed(runnable, 3000);
        };
        handler.postDelayed(runnable, 3000);
    }


    /**
     * Categories adapter listener
     * @param obj
     */
    @Override
    public void onItemClick(Category obj) {
        Intent intent = new Intent(MainActivity.this, CategoryDetailsActivity.class);
        intent.putExtra("CATEGORY", obj);
        startActivity(intent);
    }

    /**
     * Adapter for Images of the Slider
     */
    private static class AdapterImageSlider extends PagerAdapter {

        private Activity act;
        private List<String> images;

        // constructor
        private AdapterImageSlider(Activity activity) {
            this.act = activity;
            this.images = new ArrayList<>();
        }


        @Override
        public int getCount() {
            return this.images.size();
        }

        public String getItem(int pos) {
            return images.get(pos);
        }

        public void setImages(List<String> images) {
            this.images = images;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.item_slider_image, container, false);

            SimpleDraweeView image = v.findViewById(R.id.image);
            image.setImageURI(images.get(position));

            container.addView(v);

            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);

        }

    }
}
