package com.gupta.praveen.motherdairy;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.gupta.praveen.motherdairy.data.Transferdata;


public class MainActivity extends AppCompatActivity implements Transferdata,View.OnClickListener {
    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hiding the title bar has to happen before the view is created
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        MainActivity_frag mainActivity_frag=new MainActivity_frag();

        fm=getSupportFragmentManager();
        ft=fm.beginTransaction();
        ft.replace(R.id.main_activitypage,mainActivity_frag,"loginfrag");
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void TransferRespond(String data) {

    }


    @Override
    public void onClick(View v) {

    }
}

