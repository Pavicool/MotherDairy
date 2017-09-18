package com.gupta.praveen.motherdairy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gupta.praveen.motherdairy.model.User;

import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;

public class UsermainPage extends Fragment implements AppBarLayout.OnOffsetChangedListener{
    ImageView profilepic;
    private TextView username;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;
    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;
    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private  AppBarLayout appbarLayout;
    private Toolbar toolbar;
    ScrollerViewPager viewPager;

    FragmentManager fm;
    FragmentTransaction ft;
    MainActivity_frag mainActivityFrag;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
  //  private Context context;
    User userinfo;
    private String userID;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Calling menu options
       setHasOptionsMenu(true);

    }
    // Initiating Menu XML file (usermenu.xml)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        //super.onCreateOptionsMenu(menu, inflater);
       inflater.inflate(R.menu.usermenu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menulogout:
                //disconnect from Firebase Email Authentication (Log Out).
                FirebaseAuth.getInstance().signOut();
                ft.replace(R.id.main_activitypage,mainActivityFrag,"loginfrag");
                ft.addToBackStack(null);
                ft.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.usermainfrag,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profilepic= (ImageView) view.findViewById(R.id.userprofile);
        username= (TextView) view.findViewById(R.id.profilename);
        mTitle          = (TextView) view.findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) view.findViewById(R.id.main_linearlayout_title);
        viewPager  = (ScrollerViewPager) view.findViewById(R.id.pagerview);
        //Initializing Spring Indiactor
        SpringIndicator springIndicator = (SpringIndicator) view.findViewById(R.id.indicator);
        viewPager.fixScrollSpeed();
        viewPager.setOffscreenPageLimit(3);

        //Setting Tabs to Viewpager
        viewPager.setAdapter(new TabsAdapter(getChildFragmentManager()));
        // just set viewPager to SpringIndicator
        springIndicator.setViewPager(viewPager);
        appbarLayout = (AppBarLayout) view.findViewById(R.id.main_appbar);
        appbarLayout.addOnOffsetChangedListener(this);

        try{
            //Initializing Toolbar
            toolbar= (Toolbar) view.findViewById(R.id.usertoolbar);
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            //Hiding title of Toolbar before collapsing ..
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.inflateMenu(R.menu.usermenu);
            startAlphaAnimation(mTitle, 0, View.INVISIBLE);

        }catch (Exception e){
            e.printStackTrace();
        }

       // context = view.getContext();
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        mDatabase.addListenerForSingleValueEvent(userListener);
        mAuth=FirebaseAuth.getInstance();
        userinfo=new User();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

//        SharedPreferenceHelper prefHelper = SharedPreferenceHelper.getInstance(context);
//        userinfo = prefHelper.getUserInfo();
//        username.setText(userinfo.name);

        fm=getFragmentManager();
        ft=fm.beginTransaction();
        mainActivityFrag=new MainActivity_frag();

    }
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }
    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }
    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    private static class TabsAdapter extends FragmentPagerAdapter {
        private static final int TAB_COUNT = 3;

        TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args1 = new Bundle();

            switch (position) {
                case 0:
                    args1.putInt(OfferspagerFrag.POSITION_KEY, position);
                    return OfferspagerFrag.newInstance(args1);

                case 1:
                    args1.putInt(NewspagerFrag.POSITION_KEY, position);
                    return NewspagerFrag.newInstance(args1);

                case 2:
                    args1.putInt(ProfilepagerFrag.POSITION_KEY, position);
                    return ProfilepagerFrag.newInstance(args1);

            }
            return null;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return "Offers";

                case 1 :
                    return "News";

                case 2 :
                    return "Profile";

            }return null;
    }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //Setting Firebase Data to fields..
    private ValueEventListener userListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            // String value=dataSnapshot.getValue(String.class);
            try {
                // userinfo=dataSnapshot.child(userID).getValue(User.class);
                userinfo.setName(dataSnapshot.child(userID).getValue(User.class).getName());
                userinfo.setEmail(dataSnapshot.child(userID).getValue(User.class).getEmail());

                username.setText(userinfo.getName());
              //  emailtv.setText(userinfo.getEmail());

//                SharedPreferenceHelper preferenceHelper = SharedPreferenceHelper.getInstance(context);
//                preferenceHelper.saveUserInfo(userinfo);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(UsermainPage.class.getName(), "loadPost:onCancelled", databaseError.toException());
          //  Toast.makeText(context, "Database Error !", Toast.LENGTH_SHORT).show();
        }
    };

}


