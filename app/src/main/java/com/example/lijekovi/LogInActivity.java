package com.example.lijekovi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity{

    TextView input_username, input_password, register;
    String email, password;
    Button btn_login;
    FirebaseAuth mAuth;
    private static final String TAG = "LogInActivity";

    ProgressBar progressbar;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        progressbar = (ProgressBar) findViewById(R.id.progressBar_login);
        input_username = (EditText) findViewById(R.id.enter_username);
        input_password = (EditText) findViewById(R.id.enetr_password);
        register = (TextView) findViewById(R.id.textView_register);
        btn_login = (Button) findViewById(R.id.btn_login);

        progressbar.setVisibility(View.INVISIBLE);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);

                email = input_username.getText().toString();
                password = input_password.getText().toString();
                mAuth = FirebaseAuth.getInstance();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(LogInActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.INVISIBLE);
                } else {
                    logInUser(email , password);
                    progressbar.setVisibility(View.INVISIBLE);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new intent
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void logInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LogInActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser currentUser) {
        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}