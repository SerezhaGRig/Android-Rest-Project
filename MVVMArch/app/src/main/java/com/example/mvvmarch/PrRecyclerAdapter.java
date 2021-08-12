package com.example.mvvmarch;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PrRecyclerAdapter extends RecyclerView.Adapter<PrRecyclerAdapter.ItemViewHolder> implements SetProduct<List<Product>> {

    protected ArrayList<Product> products=null;
    protected Context context=null;
    private RecyclerView recyclerView;
    public PrRecyclerAdapter(Context context){
        this.context=context;
        MainActivity main = (MainActivity) context;
        recyclerView = main.findViewById(R.id.recycler);
    }
    @NonNull
    @Override
    public PrRecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context!=null){
            View holder = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
            return new PrRecyclerAdapter.ItemViewHolder(holder);
        }
        throw new RuntimeException("Context is null");
    }

    @Override
    public void onBindViewHolder(@NonNull PrRecyclerAdapter.ItemViewHolder holder, int position) {
        if(products!=null) {
            holder.name.setText(products.get(position).name);
            holder.price.setText(products.get(position).price);
            holder.description.setText(products.get(position).description);
            holder.keywords.setText(TextUtils.join(", ", products.get(position).keywords));
        }
    }

    @Override
    public int getItemCount() {
        if(products!=null)
            return products.size();
        return 0;
    }

    @Override
    public void onSuccess(List<Product> products)
    {
        if(context!=null) {
            recyclerView.setVisibility(View.VISIBLE);
            this.products = new ArrayList<Product>(products);
            this.notifyDataSetChanged();
        }
    }
    @Override
    public void onError()
    {
        if(context!=null) {
            recyclerView.setVisibility(View.GONE);
        }
    }
    @Override
    public void onLoad()
    {
        if(context!=null) {
            recyclerView.setVisibility(View.GONE);
        }
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView price;
        TextView description;
        TextView keywords;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.description);
            keywords = itemView.findViewById(R.id.keywords);


        }

    }

}
