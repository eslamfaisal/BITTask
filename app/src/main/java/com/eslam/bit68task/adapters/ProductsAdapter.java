package com.eslam.bit68task.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eslam.bit68task.R;
import com.eslam.bit68task.models.Product;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.Holder> {

    private List<Product> productList;
    private Activity context;

    public ProductsAdapter(Activity context) {
        this.productList = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setProduct(productList.get(position));


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void insertProducts(List<Product> categories) {
        this.productList.addAll(categories);
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {

        SimpleDraweeView image;
        TextView name, weight,price;
        ImageView addToCartBtn;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            weight = itemView.findViewById(R.id.weight);
            image = itemView.findViewById(R.id.image);
            price = itemView.findViewById(R.id.price);
            addToCartBtn = itemView.findViewById(R.id.add_to_cart);
        }

        public void setProduct(Product product) {
            name.setText(product.getName());
            weight.setText(product.getWeight());
            price.setText(product.getPrice());
            image.setImageURI(product.getProductImg());
            addToCartBtn.setOnClickListener(view -> addToCartBtn.setImageResource(R.drawable.ic_chosed));
        }

    }


}
