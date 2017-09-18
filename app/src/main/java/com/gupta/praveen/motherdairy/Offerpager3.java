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

public class Offerpager3 extends Fragment{
    public static final String POSITION_KEY1 = "FragmentPositionKeyprofile";
    private int position;

    public static Offerpager3 newInstance(Bundle args) {
        Offerpager3 fragment = new Offerpager3();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_frag,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView img= (ImageView) view.findViewById(R.id.profileoffer);
    }
}
