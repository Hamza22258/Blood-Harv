package com.hamza.blood_harv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TypesAdapter extends RecyclerView.Adapter<TypesAdapter.ViewHolder> {
    List<BloodTypes> ls;
    Context c;
    public TypesAdapter(){

    }
    public TypesAdapter(List<BloodTypes> ls, Context c) {
        this.ls = ls;
        this.c = c;
    }
    @NonNull
    @Override
    public TypesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(c).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypesAdapter.ViewHolder holder, int position) {
        holder.type.setText(ls.get(position).getType());
        holder.availability.setText(ls.get(position).getAvailability());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView type,availability;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type=itemView.findViewById(R.id.type);
            availability=itemView.findViewById(R.id.availability);

        }
    }
}
