package com.bhargo.user.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bhargo.user.R;
import com.bhargo.user.screens.LoginActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GoogleSignOut extends AppCompatActivity {

    Button bt_logout;
    ImageView iv_image;
    TextView tv_name, tv_email;
    GoogleSign googleSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_out);
        googleSign=new GoogleSign(this, new GoogleSign.InfoLoginGoogleCallback() {
            @Override
            public void getInfoLoginGoogle(GoogleSignInAccount account) {

            }

            @Override
            public void connectionFailedApiClient(ConnectionResult connectionResult) {

            }

            @Override
            public void loginFailed(String failedMsg) {

            }
        });
        findViews();
    }

    private void findViews() {
        bt_logout = findViewById(R.id.bt_logout);
        iv_image = findViewById(R.id.iv_image);
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            tv_name.setText(personName);
            tv_email.setText(personEmail);
            Glide.with(this).load(acct.getPhotoUrl())
                    .placeholder(R.drawable.icon_user_profile_image)
                    .dontAnimate().into(iv_image);

        }

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Way 1
                googleSign.mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Sign Out", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(GoogleSignOut.this, LoginActivity.class));
                        finish();
                    }
                });
                //Way 2
                /*Auth.GoogleSignInApi.signOut(googleSign.mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                if (status.isSuccess()){
                                 Toast.makeText(getApplicationContext(),"Sign Out", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(GoogleSignOut.this, LoginActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Session not close", Toast.LENGTH_LONG).show();
                                }
                            }
                        });*/

            }
        });
    }
}