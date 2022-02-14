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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private TextView input_fullname, input_oib, input_email, input_password, login;
    private String txtFullName, txtOIB, txtEmail, txtPassword;
    private Button btn_reg;
    private ProgressBar pb_register;

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuthREG;
    private User user;
    private Integer found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        input_fullname = (EditText) findViewById(R.id.reg_name);
        input_oib = (EditText) findViewById(R.id.reg_oib);
        input_email = (EditText) findViewById(R.id.reg_email);
        input_password = (EditText) findViewById(R.id.reg_pass);
        login = (TextView) findViewById(R.id.textView4);
        btn_reg = findViewById(R.id.btn_reg);
        pb_register = (ProgressBar) findViewById(R.id.progressBar_reg);

        pb_register.setVisibility(View.INVISIBLE);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("korisnici");
        mAuthREG = FirebaseAuth.getInstance();

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_register.setVisibility(View.VISIBLE);
                txtFullName = input_fullname.getText().toString();
                txtOIB = input_oib.getText().toString();
                txtEmail = input_email.getText().toString();
                txtPassword = input_password.getText().toString();


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
                        register(txtEmail, txtPassword);
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
    public void register(String email, String password){
        String txtOIB = input_oib.getText().toString();
        String keyId = txtOIB;
        found = 0;

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (txtOIB.equals(user.getOib())) {
                        Toast.makeText(RegisterActivity.this, "OIB already exists!", Toast.LENGTH_SHORT).show();
                        found = 1;
                    }
                }
                if (found == 0) {
                    mDatabase.child(keyId).setValue(user);
                    mAuthREG.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuthREG.getCurrentUser();
                                        LoginAfterRegistration(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, "Registration failed!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegisterActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void LoginAfterRegistration(FirebaseUser user) {
        Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
        startActivity(intent);
    }
}