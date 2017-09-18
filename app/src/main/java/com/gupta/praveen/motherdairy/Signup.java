package com.gupta.praveen.motherdairy;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gupta.praveen.motherdairy.model.User;


public class Signup extends Fragment {

    EditText mEmailField, mPasswordField, name, mobileno, Address, confPassword;
    Button signuppbtn;
    TextView aclogin;
    private static final String TAG = "EmailPassword";
    ProgressDialog progressDialog;
    FragmentManager fm;
    FragmentTransaction ft;
    MainActivity_frag mainActivity_frag;
    UsermainPage usermainPage;


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signup_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmailField = (EditText) view.findViewById(R.id.mEmailField);
        mPasswordField = (EditText) view.findViewById(R.id.pswd);
        name = (EditText) view.findViewById(R.id.name);
        mobileno = (EditText) view.findViewById(R.id.mobno);
        Address = (EditText) view.findViewById(R.id.add);
        confPassword = (EditText) view.findViewById(R.id.confpswd);
        signuppbtn = (Button) view.findViewById(R.id.signuppagebtn);
        aclogin = (TextView) view.findViewById(R.id.logintv);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIndeterminate(true);
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        mainActivity_frag = new MainActivity_frag();
        usermainPage=new UsermainPage();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        aclogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to Login Page...
                ft.replace(R.id.main_activitypage,mainActivity_frag,"loginfrag");
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        onStart();
        mAuth = FirebaseAuth.getInstance();
        signuppbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.signuppagebtn) {
                    createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());

                    String getemail = mEmailField.toString().trim();
                    String getpswd = mPasswordField.toString().trim();
                    createAccount(getemail, getpswd);
                }
            }
        });
    }

    private void createAccount( final String email, final String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            Toast.makeText(getActivity(), "Fill Properly", Toast.LENGTH_SHORT).show();
            return;

        }

        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getActivity(), "Account Created", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "createUserWithEmail:success");

                            //Save data to FirebaseDatabase
                            final String getname = name.getText().toString().trim();
                            final String getAdd = Address.getText().toString().trim();
                            final String getmobno = mobileno.getText().toString().trim();

                            // Disable button so there are no multi-posts
                            setEditingEnabled(false);

                            final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            user=new User(userId, getname, getAdd, getmobno,email,password);
                            mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            // Write new post
                                            writeNewPost(userId, getname, getAdd, getmobno,email,password);
                                            // Finish this Activity, back to the stream
                                            setEditingEnabled(true);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                                            setEditingEnabled(true);
                                        }
                                    });
                            // Move to User Page...
                            movetouser();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                            }
                        progressDialog.dismiss();

                    }
                });

    }

    private boolean validateForm() {
        boolean valid = true;
        String email = mEmailField.getText().toString();

        if (!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            valid = true;
        } else {
            mEmailField.setError("Enter a Valid Email Id !");
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        String mname = name.getText().toString();
        if (TextUtils.isEmpty(mname)) {
            name.setError("Required");
            valid = false;
        } else {
            name.setError(null);
        }

        String address = Address.getText().toString();
        if (TextUtils.isEmpty(address)) {
            Address.setError("Required");
            valid = false;
        } else {
            Address.setError(null);
        }

        String mobile = mobileno.getText().toString();
        //matches 10-digit numbers only
        String regexStr = "^[0-9]{10}$";
        if (TextUtils.isEmpty(mobile)) {
            mobileno.setError("Reqiured");
            valid = false;
        } else if (!mobile.matches(regexStr)) {
            mobileno.setError("Enter a Valid Number !");
            valid = false;
        }

        String confpswd = confPassword.getText().toString();
        if (TextUtils.isEmpty(confpswd)) {
            confPassword.setError("Required");
            valid = false;
        } else if (!password.equals(confpswd)) {
            confPassword.setError("Password not Matched !");
            valid = false;
        }

        return valid;

    }
private void movetouser(){
    ft.replace(R.id.main_activitypage, usermainPage, "userpage");
    ft.addToBackStack(null);
    ft.commit();
    }
    private void setEditingEnabled(boolean enabled) {
        name.setEnabled(enabled);
        Address.setEnabled(enabled);
        mobileno.setEnabled(enabled);
        mEmailField.setEnabled(enabled);
        mPasswordField.setEnabled(enabled);

        if (enabled) {
            signuppbtn.setVisibility(View.VISIBLE);
        } else {
            signuppbtn.setVisibility(View.GONE);
        }
    }

    private void writeNewPost(String userId, String mname, String address, String mobile,String email,String password) {

        User user = new User(userId, mname, address, mobile,email,password);
         mDatabase.child("users").push().setValue(user);

       // Making filed Clear After Signup
        name.setText("");
        Address.setText("");
        mobileno.setText("");
        mEmailField.setText("");
        mPasswordField.setText("");
        confPassword.setText("");

    }

}
