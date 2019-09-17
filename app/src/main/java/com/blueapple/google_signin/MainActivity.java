package com.blueapple.google_signin;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN=0;
    GoogleSignInClient signInClient;
    SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton=findViewById(R.id.sign_in_button);

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken("863009944691-q67uvp01fleptr47jeokh43b4klsjfaf.apps.googleusercontent.com")
                .requestEmail().build();

        signInClient= GoogleSignIn.getClient(this,gso);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();
            }
        });
    }

     void signIn()
    {

        Intent signInIntent=signInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);

            handleSigninResult(task);

        }
    }

    void handleSigninResult(Task<GoogleSignInAccount> resultTask)
    {
        try {
            GoogleSignInAccount account=resultTask.getResult(ApiException.class);

            startActivity(new Intent(MainActivity.this,AfterLoginActivity.class));


        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onStart() {

        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);

        if (account!=null)
        {
            startActivity(new Intent(MainActivity.this,AfterLoginActivity.class));

            finishAffinity();


        }
        super.onStart();
    }
}
