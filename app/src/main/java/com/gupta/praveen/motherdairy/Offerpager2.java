package com.gupta.praveen.motherdairy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



public class Offerpager2 extends Fragment {
    public static final String POSITION_KEY1 = "FragmentPositionKeynews";
    private int position;

    public static Offerpager2 newInstance(Bundle args) {
        Offerpager2 fragment = new Offerpager2();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.offer_frag2,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView img= (ImageView) view.findViewById(R.id.news);
    }
}
