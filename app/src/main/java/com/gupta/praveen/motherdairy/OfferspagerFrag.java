package com.gupta.praveen.motherdairy;

import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gupta.praveen.motherdairy.Recycler.Adapterrv;
import com.gupta.praveen.motherdairy.data.Carddata;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.facebook.FacebookSdk.getApplicationContext;


public class OfferspagerFrag extends Fragment implements View.OnClickListener{
    public static final String POSITION_KEY = "FragmentPositionKey";
    private int position;
    ViewPager mPager;
    InkPageIndicator mIndicator;

    private RecyclerView recyclerView;

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
        View root = inflater.inflate(R.layout.offerchildfrag, container, false);

//        ImageView img= (ImageView) root.findViewById(R.id.offerimg);
//        img.setOnClickListener(this);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Carddata> lista = new ArrayList<Carddata>();
        lista.add(new Carddata("Image Num 1", R.drawable.promo1,0));
        lista.add(new Carddata("Image Num 2", R.drawable.promo2,0));
        lista.add(new Carddata("Image Num 3", R.drawable.promo11,0));
        lista.add(new Carddata("Image Num 4", R.drawable.promo6,0));
        lista.add(new Carddata("Image Num 5", R.drawable.promo5,0));
        lista.add(new Carddata("Image Num 6", R.drawable.promo8,0));
        lista.add(new Carddata("Image Num 7", R.drawable.promo9,0));

        recyclerView= (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new Adapterrv(lista));

        mPager = (ViewPager)view.findViewById(R.id.pager);
        mPager.setAdapter(new PagerViewAdapter(getChildFragmentManager()));
        mIndicator = (InkPageIndicator)view.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        final TextView tv= (TextView) view.findViewById(R.id.headertext);
        tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tv.setSelected(true);

        // Showing Long Text Inside Textview not be cut by parent view or by screen
        Paint textPaint = tv.getPaint();
        String text = tv.getText().toString();//get text
        int width = Math.round(textPaint.measureText(text));//measure the text size
        ViewGroup.LayoutParams params =  tv.getLayoutParams();
        params.width = width;
        tv.setLayoutParams(params); //Refine

//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(displaymetrics);
//        }
//        int screenWidth = displaymetrics.widthPixels;
//
//        //this is optional. do not scroll if text is shorter than screen width
//        //remove this won't effect the scroll
//        if (width <= screenWidth) {
//            //All text can fit in screen.
//            return;
//        }

        //Animate Textview From Right To Left Smoothly
//        Animation animationToLeft = new TranslateAnimation(0, -width, 0, 0);
//        animationToLeft.setFillAfter(true);
//        animationToLeft.setDuration(8000);
//        animationToLeft.setRepeatMode(Animation.RESTART);
//        animationToLeft.setRepeatCount(Animation.INFINITE);
//        animationToLeft.setInterpolator(new LinearInterpolator());
//        tv.setAnimation(animationToLeft);

        //Animate TextView From Left To Right Smoothly
//        Animation animationToRight = new TranslateAnimation(-400,400, 0, 0);
//        animationToRight.setDuration(12000);
//        animationToRight.setRepeatMode(Animation.RESTART);
//        animationToRight.setRepeatCount(Animation.INFINITE);
//        tv.setAnimation(animationToRight);
//        String textRight = "Right marquue";
//        tv.setText(textRight);

        //Animate Textview From Right To Left Smoothly
        final ValueAnimator animator = ValueAnimator.ofFloat(1.0f, -0.50f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(17000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = tv.getWidth();
                final float translationX = width * progress;
                tv.setTranslationX(translationX);
               // tv2.setTranslationX(translationX - width);
            }
        });
        animator.start();


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
