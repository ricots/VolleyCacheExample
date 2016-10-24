package com.example.sanakazi.volleycacheexample.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sanakazi.volleycacheexample.R;
import com.example.sanakazi.volleycacheexample.pojos.JsonResponse;

import java.util.List;

 public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<JsonResponse.Categories> categoryList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            image = (ImageView) view.findViewById(R.id.img);
        }
    }


    public MyAdapter(List<JsonResponse.Categories> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        JsonResponse.Categories category = categoryList.get(position);
        holder.name.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}