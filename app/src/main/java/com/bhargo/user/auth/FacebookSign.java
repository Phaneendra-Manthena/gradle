package com.bhargo.user.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class FacebookSign {

    private FragmentActivity mActivity;
    private CallbackManager mCallbackManager;
    private InfoLoginFaceCallback mFaceCallback;

    public FacebookSign(FragmentActivity mActivity, InfoLoginFaceCallback mFaceCallback) {
        FacebookSdk.sdkInitialize(mActivity.getApplicationContext());
        this.mActivity = mActivity;
        this.mFaceCallback = mFaceCallback;
        mCallbackManager = CallbackManager.Factory.create();
    }

    public void signInWithFaceButton(LoginButton loginButton) {
        List<String> permissionNeeds = Arrays.asList("email", "user_about_me");
        loginButton.setReadPermissions(permissionNeeds);
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.v("TAG", "JSON: " + object);
                                try {
                                    String id = object.getString("id");
                                    String foto = "https://graph.facebook.com/" + id + "/picture?height=120&width=120";
                                    String nome = object.getString("name");
                                    String email = object.getString("email");
                                    if (mFaceCallback != null) {
                                        mFaceCallback.getInfoFace(id, nome, email, foto,loginResult);
                                    } else {
                                        throw new IllegalArgumentException("interface InfoLoginFaceCallback is null");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,picture.width(120).height(120)");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                if (mFaceCallback != null) {
                    mFaceCallback.cancelLoginFace();
                } else {
                    throw new IllegalArgumentException("interface InfoLoginFaceCallback is null");
                }
            }

            @Override
            public void onError(FacebookException error) {
                if (mFaceCallback != null) {
                    mFaceCallback.erroLoginFace(error);
                } else {
                    throw new IllegalArgumentException("interface InfoLoginFaceCallback is null");
                }
            }
        });
    }

    public void signIn() {

        List<String> permissionNeeds = Arrays.asList("email", "user_about_me");

        LoginManager.getInstance().logInWithReadPermissions(
                mActivity,
                permissionNeeds);
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResults) {

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResults.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        Log.v("TAG", "JSON: " + object);
                                        try {
                                            String id = object.getString("id");
                                            String foto = "https://graph.facebook.com/" + id + "/picture?height=120&width=120";
                                            String url=object.getJSONObject("picture").getJSONObject("data").getString("url");
                                            String nome = object.getString("name");
                                            String email = object.getString("email");
                                            if (mFaceCallback != null) {
                                                mFaceCallback.getInfoFace(id, nome, email, foto, loginResults);
                                            } else {
                                                throw new IllegalArgumentException("interface InfoLoginFaceCallback is null");
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            mFaceCallback.cancelLoginFace();
                                        }
                                    }

                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,picture.width(120).height(120)");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        if (mFaceCallback != null) {
                            mFaceCallback.cancelLoginFace();
                        } else {
                            throw new IllegalArgumentException("interface InfoLoginFaceCallback is null");
                        }
                    }

                    @Override
                    public void onError(FacebookException e) {
                        if (mFaceCallback != null) {
                            mFaceCallback.erroLoginFace(e);
                        } else {
                            throw new IllegalArgumentException("interface InfoLoginFaceCallback is null");
                        }
                    }
                });
    }

    public void resultFaceLogin(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public interface InfoLoginFaceCallback {
        void getInfoFace(String id, String nome, String email, String foto, LoginResult loginResults);

        void cancelLoginFace();

        void erroLoginFace(FacebookException e);
    }

    /*
AccessToken accessToken = AccessToken.getCurrentAccessToken();
boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
  */
    /*Then you can later perform the actual login, such as in a custom button's OnClickListener:


LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));*/

    /*App ID: 1365186324296964*/

}
