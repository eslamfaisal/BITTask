package com.eslam.bit68task.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eslam.bit68task.R;
import com.eslam.bit68task.models.Category;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.Holder> {

    private int cout = 1;
    private List<Category> categoryList;
    private Activity context;
    private OnItemClickListener mOnItemClickListener;

    public CategoriesAdapter(Activity context) {
        this.categoryList = new ArrayList<>();
        this.context = context;
        mOnItemClickListener = (OnItemClickListener) context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setCategory(categoryList.get(position));

        if (cout == 1 || cout == 2) {
            cout += 1;
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(false);
        } else {
            cout = 1;
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void insertCategories(List<Category> categories) {
        this.categoryList.addAll(categories);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Category obj);
    }

    class Holder extends RecyclerView.ViewHolder {

        SimpleDraweeView image;
        TextView name;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
        }

        public void setCategory(Category category) {
            name.setText(category.getName());
            image.setImageURI(category.getCategoryImg());
            itemView.setOnClickListener(view -> {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(category);
            });
        }

    }


}
