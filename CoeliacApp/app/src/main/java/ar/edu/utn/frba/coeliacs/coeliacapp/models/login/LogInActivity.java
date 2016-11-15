package ar.edu.utn.frba.coeliacs.coeliacapp.models.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.MainActivity;

public class LogInActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private TextView info;
    private ImageView profileImgView;
    private LoginButton loginButton;
    private Button boton_acceso;
    private PrefUtil prefUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.setApplicationId(getResources().getString(R.string.facebook_app_id));
        setContentView(R.layout.activity_fb);

        //Facebook login
        callbackManager = CallbackManager.Factory.create();
        prefUtil = new PrefUtil(this);

        info = (TextView) findViewById(R.id.info);
        profileImgView = (ImageView) findViewById(R.id.profile_img);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        boton_acceso = (Button) findViewById(R.id.boton_acceso);
        boton_acceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        updateVisibility(prefUtil.getToken()!= null);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                showProfile();

                String userId = loginResult.getAccessToken().getUserId();
                String accessToken = loginResult.getAccessToken().getToken();

                // save accessToken and userId to SharedPreference
                prefUtil.saveAccessToken(accessToken);
                prefUtil.saveUserId(userId);

                updateVisibility(true);
            }

            @Override
            public void onCancel() {
                updateVisibility(false);
                Toast.makeText(LogInActivity.this, "Log in cancelado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                updateVisibility(false);
                Toast.makeText(LogInActivity.this, "Log in fallido", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    private void showProfile() {
        Profile profile = Profile.getCurrentProfile();
        info.setText(message(profile));
        String profileImgUrl = "https://graph.facebook.com/" + prefUtil.getUserId() + "/picture?type=large";
        Glide.with(LogInActivity.this)
                .load(profileImgUrl)
                .into(profileImgView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_bypass_login:
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        deleteAccessToken();
        showProfile();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private String message(Profile profile) {
        StringBuilder stringBuffer = new StringBuilder();
        if (profile != null) {
            stringBuffer.append("Hola ").append(profile.getName());
        }
        return stringBuffer.toString();
    }

    private void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    //User logged out
                    prefUtil.clearToken();
                    clearUserArea();
                    updateVisibility(false);
                }
            }
        };
    }

    private void clearUserArea() {
        info.setText("");
        profileImgView.setImageDrawable(null);
    }

    private void updateVisibility(boolean logged) {
        info.setVisibility(logged ? View.VISIBLE : View.GONE);
        profileImgView.setVisibility(logged ? View.VISIBLE : View.GONE);
        boton_acceso.setVisibility(logged ? View.VISIBLE : View.GONE);
        loginButton.setVisibility(View.VISIBLE);
    }

}
