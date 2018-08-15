package com.bd.reibow;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView;
        private ImageView imageView;
        public MyViewHolder(View itemview)
        {
            super(itemview);
            textView= (TextView) itemview.findViewById(R.id.content);
            imageView= (ImageView) itemview.findViewById(R.id.image);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        MyViewHolder viewHolder =new MyViewHolder(view);//实例化viewholder
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        myViewHolder.textView.setText("Test"+position);
        myViewHolder.imageView.setImageResource(R.drawable.back);
    }
    @Override
    public int getItemCount() {
        return 15;          //返回个每页的数量
    }
}