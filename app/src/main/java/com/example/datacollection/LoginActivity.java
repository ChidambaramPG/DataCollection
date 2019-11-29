package com.example.datacollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginEmailId);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.loginBtn);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailId = email.getText().toString();
                String pass = password.getText().toString();

                if(emailId.isEmpty()){
                    email.setError("Email is required");
                    return;
                }
                if(pass.isEmpty()){
                    password.setError("Password is required");
                    return;
                }

                if(!isEmail(emailId)){
                    email.setError("Email is not found.");
                    return;
                }

                mAuth.signInWithEmailAndPassword(emailId,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent loginIntent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(loginIntent);

                        }else{
                            Toast.makeText(LoginActivity.this,"Credentials are incorect",Toast.LENGTH_LONG);
                        }
                    }
                });


            }
        });


    }

    boolean isEmail(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent loginIntent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }
}
