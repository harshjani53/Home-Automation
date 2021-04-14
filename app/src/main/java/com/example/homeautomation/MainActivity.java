package com.example.homeautomation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.userName);
        Password = (EditText) findViewById(R.id.userPassword);
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        TextView signUp = (TextView) findViewById(R.id.signUp);
        TextView forgetPass = (TextView) findViewById(R.id.forgotPass);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),SecondActivity.class));
        }

        forgetPass.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),PasswordActivity.class)));

        loginBtn.setOnClickListener(v -> entry(Name.getText().toString(),Password.getText().toString()));

        signUp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),RegistrationActivity.class)));
    }

    private void entry(String uName, String uPass){
        try {
            firebaseAuth.signInWithEmailAndPassword(uName, uPass).addOnCompleteListener(rest -> {
                if (rest.isSuccessful()) {
                    verify();
                } else {
                    Toast.makeText(getApplicationContext(), "Enter correct Credentials", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception e){
         Toast.makeText(getApplicationContext(),"Please Enter Your Credentials Before Login",Toast.LENGTH_LONG).show();
        }
    }

    private void verify(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        boolean eVerify = firebaseUser.isEmailVerified();

        if(eVerify){
            finish();
            startActivity(new Intent(getApplicationContext(),SecondActivity.class));
        }
        else{
            Toast.makeText(getApplicationContext(),"Verify E-Mail Please",Toast.LENGTH_LONG).show();
            firebaseAuth.signOut();
        }
    }
}