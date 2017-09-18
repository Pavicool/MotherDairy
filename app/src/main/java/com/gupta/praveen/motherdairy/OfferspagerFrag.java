package com.gupta.praveen.motherdairy;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.Timer;
import java.util.TimerTask;


public class OfferspagerFrag extends Fragment implements View.OnClickListener{
    public static final String POSITION_KEY = "FragmentPositionKey";
    private int position;
    ViewPager mPager;
    InkPageIndicator mIndicator;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        // Hiding the title bar has to happen before the view is created
        try {
            getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }catch (Exception e){

        }

//        try {
//
//            if(this instanceof OfferspagerFrag) {
//                if (getActivity().getActionBar().isShowing()) getActivity().getActionBar().hide();
//            } else {
//                if (getActivity().getActionBar().isShowing()) getActivity().getActionBar().hide();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        super.onCreate(savedInstanceState);
    }

    public static OfferspagerFrag newInstance(Bundle args) {
        OfferspagerFrag fragment = new OfferspagerFrag();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        position = getArguments().getInt(POSITION_KEY);
        View root = inflater.inflate(R.layout.childfrag1, container, false);

//        ImageView img= (ImageView) root.findViewById(R.id.offerimg);
//        img.setOnClickListener(this);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPager = (ViewPager)view.findViewById(R.id.pager);
        mPager.setAdapter(new PagerViewAdapter(getChildFragmentManager()));
        mIndicator = (InkPageIndicator)view.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);


/*After setting the adapter use the timer */
        final Handler handler = new Handler();

        final Runnable Update = new Runnable() {
            public void run() {
                final int NUM_PAGES=4;
                if (currentPage == NUM_PAGES-1) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer .schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.postDelayed(Update,DELAY_MS);
                //If You want to stop on click
              // handler.removeCallbacks( Update );
            }
        }, DELAY_MS, PERIOD_MS);

    }
    private static class PagerViewAdapter extends FragmentPagerAdapter{
        private static final int TAB_COUNT = 3;
        public PagerViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args1 = new Bundle();
            switch (position) {
                case 0:
                    args1.putInt(Offerpager1.POSITION_KEY1, position);
                    return Offerpager1.newInstance(args1);

                case 1:
                    args1.putInt(Offerpager2.POSITION_KEY1, position);
                    return Offerpager2.newInstance(args1);

                case 2:
                    args1.putInt(Offerpager3.POSITION_KEY1, position);
                    return Offerpager3.newInstance(args1);

            }
            return null;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }
    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "Clicked Position: " + position, Toast.LENGTH_LONG).show();
    }
}
