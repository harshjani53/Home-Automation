package com.example.homeautomation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private EditText resetEmail;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        resetEmail = (EditText) findViewById(R.id.onClickForgetPass);
        Button resetPassBtn = (Button) findViewById(R.id.resetPass);
        firebaseAuth = FirebaseAuth.getInstance();

        resetPassBtn.setOnClickListener(v -> {
            String getEmail = resetEmail.getText().toString().trim();

            if(getEmail.isEmpty()){
                Toast.makeText(getApplicationContext(),"Please Enter Credentials",Toast.LENGTH_LONG).show();
            }
            else{
                firebaseAuth.sendPasswordResetEmail(getEmail).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Password Reset link has been sent to email",Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Please Enter Correct Email",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}