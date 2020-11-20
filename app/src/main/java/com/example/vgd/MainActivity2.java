package com.example.vgd;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;


public class MainActivity2 extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    Button sign_out;
    TextView nameTV;
    TextView emailTV;
    TextView idTV;
    ImageView photoIV;

    private String server_url = "https://monwank.000webhostapp.com/sampledb.php";
   // private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        sign_out = findViewById(R.id.log_out);
        nameTV = findViewById(R.id.name);
        emailTV = findViewById(R.id.email);
        idTV = findViewById(R.id.id);
        photoIV = findViewById(R.id.photo);


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MainActivity2.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            nameTV.setText("Name: "+personName);
            emailTV.setText("Email: "+personEmail);
            idTV.setText("ID: "+personId);
            Glide.with(this).load(personPhoto).into(photoIV);
            sendDataToServer(personId,personName,personEmail);

        }

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }


    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity2.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity2.this, MainActivity.class));
                        finish();
                    }
                });
    }

    private void sendDataToServer(final String id,final String name, final String email)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }

                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity2.this,"server error",Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }){
            @Override
            protected HashMap<String, String> getParams() throws AuthFailureError {
                HashMap <String,String> Params = new HashMap<String, String>();
                Params.put("id",id);
                Params.put("name",name);
                Params.put("email",email);
                return Params;

            }
        };
        Mysingelton.getInstance(MainActivity2.this).addTorequestque(stringRequest);
    }
    public void joinRoom(View view)
    {
        startActivity(new Intent(MainActivity2.this, FrameActivity.class));
    }
}