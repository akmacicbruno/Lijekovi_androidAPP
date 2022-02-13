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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private TextView input_fullname, input_oib, input_email, input_password, login;
    private Button btn_reg;
    private ProgressBar pb_register;

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuthREG;
    private static final String KORISNICI = "korisnici";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        input_fullname = (EditText) findViewById(R.id.reg_name);
        input_oib = (EditText) findViewById(R.id.reg_oib);
        input_email = (EditText) findViewById(R.id.reg_email);
        input_password = (EditText) findViewById(R.id.reg_pass);
        login = (TextView) findViewById(R.id.textView4);
        pb_register = (ProgressBar) findViewById(R.id.progressBar_reg);

        pb_register.setVisibility(View.INVISIBLE);
        btn_reg = findViewById(R.id.btn_reg);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(KORISNICI);
        mAuthREG = FirebaseAuth.getInstance();

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_register.setVisibility(View.VISIBLE);
                String txtFullName = input_fullname.getText().toString();
                String txtOIB = input_oib.getText().toString();
                String txtEmail = input_email.getText().toString();
                String txtPassword = input_password.getText().toString();


                if (TextUtils.isEmpty(txtFullName) || TextUtils.isEmpty(txtOIB)
                        || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(RegisterActivity.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                    pb_register.setVisibility(View.INVISIBLE);
                }
                else {
                    if (txtPassword.length() < 6){
                        Toast.makeText(RegisterActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
                        pb_register.setVisibility(View.INVISIBLE);
                    }
                    else {
                        user = new User(txtEmail, txtPassword, txtOIB, txtFullName);
                        registerUser(txtEmail, txtPassword);
                        pb_register.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

    }
    public void registerUser(String email, String password) {
        mAuthREG.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuthREG.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void updateUI(FirebaseUser currentUser){
        String txtOIB = input_oib.getText().toString();
        String keyId = txtOIB;
        mDatabase.child(keyId).setValue(user);
        Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
        startActivity(intent);
    }
}