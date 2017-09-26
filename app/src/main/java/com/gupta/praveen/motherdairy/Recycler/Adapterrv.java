package com.gupta.praveen.motherdairy.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gupta.praveen.motherdairy.R;
import com.gupta.praveen.motherdairy.data.Carddata;

import java.util.List;

/**
 * Created by prave on 25-09-2017.
 */

public class Adapterrv extends RecyclerView.Adapter<viewHolder>{
    List<Carddata> carddataList;
    public Adapterrv(List<Carddata> carddatalist){
        carddataList=carddatalist;
    }
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.carddataitem,parent,false);
        return new viewHolder(view,carddataList);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.imgtxt.setText(carddataList.get(position).getImgtxt());
        holder.image.setImageResource(carddataList.get(position).getImages());
    }

    @Override
    public int getItemCount() {
        return carddataList.size();
    }
}
