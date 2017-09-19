package com.gupta.praveen.motherdairy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class ProfilepagerFrag extends Fragment implements View.OnClickListener{
    public static final String POSITION_KEY = "FragmentPositionKey3";
    private int position;

    public static ProfilepagerFrag newInstance(Bundle args) {
        ProfilepagerFrag fragment = new ProfilepagerFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        position = getArguments().getInt(POSITION_KEY);

        View root = inflater.inflate(R.layout.profilechildfrag, container, false);
        ImageView img1= (ImageView) root.findViewById(R.id.offerimg2);
        img1.setOnClickListener(this);
        ImageView img= (ImageView) root.findViewById(R.id.offerimg3);
        img.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "Clicked Position: " + position, Toast.LENGTH_LONG).show();
    }
}
