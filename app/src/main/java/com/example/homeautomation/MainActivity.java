package com.example.homeautomation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = findViewById(R.id.userName);
        Password = findViewById(R.id.userPassword);
        Button loginBtn = findViewById(R.id.loginBtn);
        TextView signUp = findViewById(R.id.signUp);
        TextView forgetPass = findViewById(R.id.forgotPass);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),SecondActivity.class));
        }

        forgetPass.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),PasswordActivity.class)));

        loginBtn.setOnClickListener(v -> {
            String Hpass = Password.getText().toString();
            Hpass= sha256Hex(Hpass);
            entry(Name.getText().toString(),Hpass);
        });
        
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
    public static String sha256Hex(String hexBase){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte [] hexId = messageDigest.digest(hexBase.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hexId) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}