package in.premad.maisha_task.SocialLogins;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import in.premad.maisha_task.R;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.database.core.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookLoginActivity extends AppCompatActivity {
    // Declare variables
    private Button mButtonFacebook;
    com.facebook.login.widget.LoginButton fbbutton;

    private CallbackManager callbackManager;
    private LoginManager loginManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        printHashKey();



        fbbutton
                = findViewById(R.id.login_button);
        mButtonFacebook
                = findViewById(R.id.button_facebook);
        FacebookSdk.sdkInitialize(FacebookLoginActivity.this);
        callbackManager = CallbackManager.Factory.create();
        facebookLogin();


        fbbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginManager.logInWithReadPermissions(
                        FacebookLoginActivity.this,
                        Arrays.asList(
                                "email",
                                "public_profile"
                                /*"user_birthday"*/));
            }
        });


        mButtonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginManager.logInWithReadPermissions(
                        FacebookLoginActivity.this,
                        Arrays.asList(
                                "email",
                                "public_profile"));
            }
        });
    }

    public void facebookLogin()
    {

        loginManager
                = LoginManager.getInstance();
        callbackManager
                = CallbackManager.Factory.create();

        loginManager
                .registerCallback(
                        callbackManager,
                        new FacebookCallback<LoginResult>() {

                            @Override
                            public void onSuccess(LoginResult loginResult)
                            {
                                GraphRequest request = GraphRequest.newMeRequest(

                                        loginResult.getAccessToken(),

                                        new GraphRequest.GraphJSONObjectCallback() {

                                            @Override
                                            public void onCompleted(JSONObject object,
                                                                    GraphResponse response)
                                            {

                                                if (object != null) {
                                                    try {
                                                        String name = object.getString("name");
                                                        String email = object.getString("email");
                                                        String fbUserID = object.getString("id");


                                                        Toast.makeText(FacebookLoginActivity.this, "Facebook Login Successfull with "+name, Toast.LENGTH_SHORT).show();
                                                       // disconnectFromFacebook();

                                                        // do action after Facebook login success
                                                        // or call your API
                                                    }
                                                    catch (JSONException | NullPointerException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        });

                                Bundle parameters = new Bundle();
                                parameters.putString(
                                        "fields",
                                        "id, name, email, gender, birthday");
                                request.setParameters(parameters);
                                request.executeAsync();
                            }

                            @Override
                            public void onCancel()
                            {
                                Log.v("LoginScreen", "---onCancel");
                            }

                            @Override
                            public void onError(FacebookException error)
                            {
                                // here write code when get error
                                Log.v("LoginScreen", "----onError: "
                                        + error.getMessage());
                            }
                        });
    }

    public void disconnectFromFacebook()
    {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse)
                    {
                        LoginManager.getInstance().logOut();
                    }
                })
                .executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        // add this line
        callbackManager.onActivityResult(
                requestCode,
                resultCode,
                data);

        super.onActivityResult(requestCode,
                resultCode,
                data);
    }



    public void printHashKey() {
        Log.d("TAG","fn called");
        // Add code to print out the key hash
        try {

            PackageInfo info
                    = getPackageManager().getPackageInfo(
                    "in.premad.maisha_task",
                    PackageManager.GET_SIGNATURES);

            Log.d("TAG","inside fn");
            for (Signature signature : info.signatures) {

                MessageDigest md
                        = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:",
                        Base64.encodeToString(
                                md.digest(),
                                Base64.DEFAULT));

                Log.d("TAG","inside hash");
            }
        } catch (PackageManager.NameNotFoundException e) {

            Log.d("TAG","inside exception"+e.getStackTrace());
        } catch (NoSuchAlgorithmException e) {
            Log.d("TAG","inside exception"+e.getStackTrace());

        }
    }
}