package com.gupta.praveen.motherdairy.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gupta.praveen.motherdairy.R;
import com.gupta.praveen.motherdairy.data.Carddata;

import java.util.List;

/**
 * Created by prave on 25-09-2017.
 */

public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    Button btn1,btn2;
    ImageView image;
    TextView imgtxt;
    int Position;
    List<Carddata> carddataList;

    public viewHolder(View itemView,List<Carddata>carddatalist) {
        super(itemView);
        btn1= (Button) itemView.findViewById(R.id.btngetcoupon);
        btn2= (Button) itemView.findViewById(R.id.btnfavourite);
        image= (ImageView) itemView.findViewById(R.id.imagecv);
        imgtxt= (TextView) itemView.findViewById(R.id.textoffer);
        carddataList=carddatalist;
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        Carddata carddataobj=carddataList.get(position);
        if (v.getId()== btn1.getId()){
            Toast.makeText(btn1.getContext(), "Coupon Clicked", Toast.LENGTH_SHORT).show();
        }else if (v.getId()== btn2.getId()){
            Toast.makeText(btn2.getContext(), "Favourite Clicked ", Toast.LENGTH_SHORT).show();
        }
    }
}
