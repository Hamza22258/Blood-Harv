package com.hamza.blood_harv;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    List<Profile> ls;
    Boolean SearchCheck;
    String SearchQuery;
    Context c;

    private CallbackInterface mCallback;

    public interface CallbackInterface{


        void onSelect(String uId, String accountType);
        void onSearch(String search);
    }

    public HomeAdapter(){

    }
    public HomeAdapter(List<Profile> ls, Context c) {
        this.SearchCheck=false;
        this.SearchQuery="";
        this.ls = ls;
        this.c = c;
        try{
            mCallback = (CallbackInterface) c;
        }catch(ClassCastException ex){
            //.. should log the error or throw and exception
            Log.e("MyAdapter","Must implement the CallbackInterface in the Activity", ex);
        }
    }
    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(c).inflate(R.layout.home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        if(SearchCheck== true){
            if(ls.get(position).getBloodType().equals(SearchQuery)){
                if (ls.get(position).getActive().equals("false")){
                    holder.type.setText("Not Active");
                }
                else{
                    holder.type.setText("Active");
                }
                holder.name.setText(ls.get(position).getName());
                Picasso.get().load(ls.get(position).getDp()).into(holder.image);
            }
            else{
                holder.linearLayout.setVisibility(View.GONE);
                holder.linearLayout.setLayoutParams(new RecyclerView.LayoutParams(0, 0));            }
        }
        else{
            if (ls.get(position).getActive().equals("false")){
                holder.type.setText("Not Active");
            }
            else{
                holder.type.setText("Active");
            }
            holder.name.setText(ls.get(position).getName());
            Picasso.get().load(ls.get(position).getDp()).into(holder.image);
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                Toast.makeText(v.getContext(), ls.get((Integer)holder.getAdapterPosition()).getAccountType()+"", Toast.LENGTH_SHORT).show();
//                mCallback.onSelect(ls.get((Integer)holder.getAdapterPosition()).getUid());


            }
        });

    }

    @Override
    public int getItemCount() {
        return ls.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView type,name;
        CircleImageView image;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type=itemView.findViewById(R.id.type);
            image=itemView.findViewById(R.id.profile_image);
            name=itemView.findViewById(R.id.name);
            linearLayout=itemView.findViewById(R.id.layout);
        }
    }
}
