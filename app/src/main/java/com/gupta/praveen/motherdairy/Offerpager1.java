package com.gupta.praveen.motherdairy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by prave on 08-09-2017.
 */

public class Offerpager1 extends Fragment {
    public static final String POSITION_KEY1 = "FragmentPositionKeyoffers";
    private int position;

    public static Offerpager1 newInstance(Bundle args) {
        Offerpager1 fragment = new Offerpager1();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.offers_frag,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView img= (ImageView) view.findViewById(R.id.offerimg);
    }
}
