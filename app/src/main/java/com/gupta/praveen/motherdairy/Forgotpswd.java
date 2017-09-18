package com.gupta.praveen.motherdairy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by prave on 13-07-2017.
 */

public class Forgotpswd extends Fragment {
    TextView tvback,tvsubbmit;
    String TAG="Email Sent";
    EditText email;
    FragmentManager fm;
    FragmentTransaction ft;
    MainActivity_frag mainActivity_frag;
    private Toolbar ftoolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.forgotpswd_frag,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvback= (TextView) view.findViewById(R.id.textView6);
        tvsubbmit= (TextView) view.findViewById(R.id.textView7);
        email= (EditText) view.findViewById(R.id.etfemail);

        //Toolbar Set
        ftoolbar= (Toolbar) view.findViewById(R.id.forgotpswdtoolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(ftoolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Mother Dairy");


        fm=getFragmentManager();
        ft=fm.beginTransaction();
        mainActivity_frag=new MainActivity_frag();
        tvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ft.replace(R.id.main_activitypage,mainActivity_frag,"loginfrag");
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        tvsubbmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    return;
                }
                //Send a password reset email
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = "user@example.com";
                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                    Toast.makeText(getActivity(), "Email Sent.", Toast.LENGTH_SHORT).show();

                                    ft.replace(R.id.main_activitypage,mainActivity_frag,"loginfrag");
                                    ft.addToBackStack(null);
                                    ft.commit();
                                }
                            }
                        });
            }
        });


    }

    private boolean validate() {
        boolean valid = false;
        String emailvalidate = email.getText().toString();

        if (!TextUtils.isEmpty(emailvalidate)&& android.util.Patterns.EMAIL_ADDRESS.matcher(emailvalidate).matches()) {
            valid = true;
        }else{
            email.setError("Required.");


        }
        return valid;
    }
}
