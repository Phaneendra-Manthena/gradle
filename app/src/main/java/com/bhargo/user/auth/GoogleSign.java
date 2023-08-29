package com.bhargo.user.auth;

import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class GoogleSign {

    private static final int SIGN_IN = 10;
    private final FragmentActivity mActivity;
    private final InfoLoginGoogleCallback googleCallback;
    public GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient mGoogleApiClient;

    public GoogleSign(FragmentActivity mActivity, InfoLoginGoogleCallback googleCallback) {
        this.mActivity = mActivity;
        this.googleCallback = googleCallback;
        getConfigDefaultLogin();
    }


    private void getConfigDefaultLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        // .requestIdToken("695107775565-106oj3vhp6fee07epv1gnkmu6qs44eur.apps.googleusercontent.com")
        //Way 1
        mGoogleSignInClient = GoogleSignIn.getClient(mActivity, gso);
        //Way 2
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity.getApplication().getApplicationContext())
                .enableAutoManage(mActivity, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        googleCallback.connectionFailedApiClient(connectionResult);
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


    }

    public void signIn() {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(mActivity);
        if (acct != null) {
            //Already if your In Login Case
            googleCallback.getInfoLoginGoogle(acct);
        } else {
            //Way 1
            Intent signInIntent1 = mGoogleSignInClient.getSignInIntent();
            //Way 2
            Intent signInIntent2 = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

            mActivity.startActivityForResult(signInIntent2, SIGN_IN);
        }
    }

    public void signOut() {
        //Way 1
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
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


    public void resultGoogleLogin(int requestCode, int resultCode, Intent data) {
        if (requestCode == SIGN_IN) {
            if (data != null) {
                //Way 1:
                /*try {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    GoogleSignInAccount result=task.getResult();
                    task.getResult(ApiException.class);
                    if (result != null) {
                        googleCallback.getInfoLoginGoogle(task.getResult());
                    } else {
                        googleCallback.loginFailed(task.getException().toString());
                    }
                } catch (ApiException e) {
                    googleCallback.loginFailed(e.getMessage());
                }*/
                //Way 2
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result != null && result.isSuccess()) {
                    googleCallback.getInfoLoginGoogle(result.getSignInAccount());
                } else {
                    googleCallback.loginFailed("");
                }
            } else {
                googleCallback.loginFailed("");
            }
        }
    }


    public interface InfoLoginGoogleCallback {
        void getInfoLoginGoogle(GoogleSignInAccount account);

        void connectionFailedApiClient(ConnectionResult connectionResult);

        void loginFailed(String failedMsg);
    }

}
