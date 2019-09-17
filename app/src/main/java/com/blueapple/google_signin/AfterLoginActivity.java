package com.blueapple.google_signin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AfterLoginActivity extends AppCompatActivity {

    TextView tvname,tvemail;
    ImageView imageView;
    GoogleSignInClient signInClient;
    Button btn_signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        tvemail=findViewById(R.id.text_emailid);
        tvname=findViewById(R.id.text_nameid);
        imageView=findViewById(R.id.imageViewid);
        btn_signout=findViewById(R.id.btn_signoutid);


        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken("863009944691-q67uvp01fleptr47jeokh43b4klsjfaf.apps.googleusercontent.com")
                .requestEmail().build();

        signInClient= GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);

        if (account!=null)
        {
            String name=account.getDisplayName();
            String email=account.getEmail();
            Uri image_url=account.getPhotoUrl();

            tvname.setText(name);
            tvemail.setText(email);

            Glide.with(this).load(image_url).into(imageView);
        }

        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(AfterLoginActivity.this, "sign out", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(AfterLoginActivity.this,MainActivity.class));
                        finishAffinity();

                    }
                });
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }
}
