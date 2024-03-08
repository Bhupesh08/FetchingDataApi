package com.example.fetchingdataapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   private ArrayList<ModalClass> list;
    private Context context;
    private  ItemCLickListener clickListener;

    public MyAdapter(ArrayList<ModalClass> list, Context context, ItemCLickListener clickListener) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModalClass currentItem = list.get(position);

        String name = currentItem.getName();
        String url = currentItem.getImgUrl();
        int price = currentItem.getPrice();

        ((ViewHolder)holder).nameview.setText(name);
        ((ViewHolder)holder).priceview.setText(""+price);
        Picasso.get().load(url).into(((ViewHolder) holder).imgview);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(currentItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgview;
        TextView nameview, priceview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameview = itemView.findViewById(R.id.name_txt);
            imgview = itemView.findViewById(R.id.item_img);
            priceview = itemView.findViewById(R.id.price_txt);
        }
    }
    interface ItemCLickListener {
        void onItemClick(ModalClass modalClass);
    }

}
