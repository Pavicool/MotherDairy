package com.gupta.praveen.motherdairy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class NewspagerFrag extends Fragment implements View.OnClickListener{
    public static final String POSITION_KEY = "FragmentPositionKey2";
    private int position;

    public static NewspagerFrag newInstance(Bundle args) {
        NewspagerFrag fragment = new NewspagerFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        position = getArguments().getInt(POSITION_KEY);

        View root = inflater.inflate(R.layout.childfrag2, container, false);
        ImageView img= (ImageView) root.findViewById(R.id.offerimg2);
        img.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "Clicked Position: " + position, Toast.LENGTH_LONG).show();
    }
}
