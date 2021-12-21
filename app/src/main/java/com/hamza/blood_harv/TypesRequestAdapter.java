package com.hamza.blood_harv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TypesRequestAdapter extends RecyclerView.Adapter<TypesRequestAdapter.ViewHolder> {
    List<BloodTypes> ls;

    public TypesRequestAdapter(List<BloodTypes> ls, Context c) {
        this.ls = ls;
        this.c = c;
    }

    Context c;
    @NonNull
    @Override
    public TypesRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(c).inflate(R.layout.request_row,parent,false);
        return new TypesRequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypesRequestAdapter.ViewHolder holder, int position) {
        holder.type.setText(ls.get(position).getType());
        holder.availability.setText(ls.get(position).getAvailability());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView type,availability;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type=itemView.findViewById(R.id.type);
            availability=itemView.findViewById(R.id.availability);
            button=itemView.findViewById(R.id.request_button);

        }
    }
}
