package com.gupta.praveen.motherdairy;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gupta.praveen.motherdairy.data.Transferdata;

/**
 * Created by prave on 06-08-2017.
 */

public class MainActivity_frag extends Fragment implements View.OnClickListener {
    private static final String TAG = "EmailPassword";
    EditText etmail,etpswd;
    CheckBox checkBox;
    Button loginbtn;
    ImageButton fbloginbtn,googleplusbtn,twitterbtn;
    TextView forgotpswd,signup;
    Transferdata transferdata;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog progressdialog;
    FragmentManager fm;
    FragmentTransaction ft;
    UsermainPage usermainPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.frag_mainactivity,container,false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transferdata= (Transferdata) getActivity();

        etmail= (EditText) view.findViewById(R.id.etemailid);
        etpswd= (EditText) view.findViewById(R.id.etpswd);
        checkBox= (CheckBox) view.findViewById(R.id.showpswd);
        loginbtn= (Button) view.findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(this);
        usermainPage=new UsermainPage();
        fbloginbtn= (ImageButton) view.findViewById(R.id.fblogin);
        fbloginbtn.setOnClickListener(this);
        googleplusbtn= (ImageButton) view.findViewById(R.id.googlepluslogin);
        googleplusbtn.setOnClickListener(this);
        twitterbtn= (ImageButton) view.findViewById(R.id.twitterlogin);
        twitterbtn.setOnClickListener(this);
        forgotpswd= (TextView) view.findViewById(R.id.forgotpswd);
        forgotpswd.setOnClickListener(this);
        signup= (TextView) view.findViewById(R.id.tvsignup);
        signup.setOnClickListener(this);
        fm=getFragmentManager();
        ft=fm.beginTransaction();

        progressdialog=new ProgressDialog(getActivity());
        progressdialog.setMessage(getString(R.string.loading));
        progressdialog.setIndeterminate(true);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // If it is checked then show password else hide password
                int textLength = etpswd.getText().length();
                if (isChecked) {

                    checkBox.setText(R.string.hide_pwd);// change checkbox text

                    etpswd.setInputType(InputType.TYPE_CLASS_TEXT);
                    etpswd.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password
                    etpswd.setSelection(textLength, textLength);
                } else {
                    checkBox.setText(R.string.show_pwd);// change checkbox text

                    etpswd.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etpswd.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());// hide password

                    etpswd.setSelection(textLength, textLength);

                }
            }
        });

        mAuth=FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    // Move to User Page...
                    movetouser();

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(getActivity(), "Sign In Please !", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
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
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.loginbtn :
                String getemail= etmail.getText().toString().trim();
                String getpassword=etpswd.getText().toString().trim();
                signIn(getemail,getpassword);
                break;
            case R.id.fblogin :
                // Move to User Page...
                movetouser();
                break;
            case R.id.googlepluslogin :
                // Move to User Page...
                movetouser();
                break;
            case R.id.twitterlogin :
                // Move to User Page...
                movetouser();
                break;
            case R.id.forgotpswd :
                Forgotpswd forgotpswd=new Forgotpswd();
                ft.replace(R.id.main_activitypage,forgotpswd,"forgotpswdfrag");
                ft.addToBackStack(null);
                ft.commit();
                break;
            case R.id.tvsignup :
                Signup signup=new Signup();
                ft.replace(R.id.main_activitypage,signup,"signupfrag");
                ft.addToBackStack(null);
                ft.commit();

                break;
        }


    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            Toast.makeText(getActivity(), "Fill Properly", Toast.LENGTH_SHORT).show();
            return;
        }
        progressdialog.show();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                            Toast.makeText(getActivity(), "Login Successfully !", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(getActivity(), R.string.auth_failed,Toast.LENGTH_SHORT).show();
                        }

                        progressdialog.dismiss();
                    }
                });

    }
    private void movetouser(){
        ft.replace(R.id.main_activitypage, usermainPage, "userpage");
        ft.addToBackStack(null);
        ft.commit();

    }
    private boolean validateForm() {
        boolean valid = true;

        String email = etmail.getText().toString();
        if (!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            valid = true;
        }
        else {
            etmail.setError("Enter Valid Email Id");
        }

        String password = etpswd.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etpswd.setError("Required.");
            valid = false;
        } else {
            etpswd.setError(null);
        }

        return valid;
    }

}
