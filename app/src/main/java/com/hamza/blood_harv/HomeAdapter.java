package com.hamza.blood_harv;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    List<Profile> ls;
    Context c;
    public HomeAdapter(){

    }
    public HomeAdapter(List<Profile> ls, Context c) {
        this.ls = ls;
        this.c = c;
    }
    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(c).inflate(R.layout.home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        if (ls.get(position).getActive().equals("false")){
            holder.type.setText("Not Active");
        }
        else{
            holder.type.setText("Active");
        }
        holder.name.setText(ls.get(position).getName());
        Picasso.get().load(ls.get(position).getDp()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView type,name;
        CircleImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type=itemView.findViewById(R.id.type);
            image=itemView.findViewById(R.id.profile_image);
            name=itemView.findViewById(R.id.name);
        }
    }
}
