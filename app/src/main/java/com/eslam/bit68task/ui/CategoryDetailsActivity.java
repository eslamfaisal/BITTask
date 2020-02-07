package com.eslam.bit68task.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.eslam.bit68task.R;
import com.eslam.bit68task.adapters.ProductsAdapter;
import com.eslam.bit68task.models.Category;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class CategoryDetailsActivity extends AppCompatActivity {

    private SimpleDraweeView image;
    private Category category;
    private RecyclerView productsRecyclerView;
    private ProductsAdapter productsAdapter;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detals);

        initToolbar();
        initViews();
        initRecyclerView();
        initIntent();

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initViews() {
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        productsRecyclerView = findViewById(R.id.products_recycler_view);

    }

    /**
     * Get Product data from Intent
     */
    private void initIntent() {
        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra("CATEGORY");

        fillData();
    }


    private void fillData() {
        image.setImageURI(category.getCategoryImg());
        name.setText(category.getName());
        productsAdapter.insertProducts(category.getProducts());
    }

    /**
     * Init RecyclerView and its Adapter
     */
    private void initRecyclerView() {
        productsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        productsRecyclerView.setNestedScrollingEnabled(false);

        //set data and list adapter
        productsAdapter = new ProductsAdapter(this);
        productsRecyclerView.setAdapter(productsAdapter);
    }
}
