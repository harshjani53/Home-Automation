package com.example.homeautomation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private Button LoginBtn;
    private TextView signUp;
    private FirebaseAuth firebaseAuth;
    private TextView forgetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.userName);
        Password = (EditText) findViewById(R.id.userPassword);
        LoginBtn = (Button) findViewById(R.id.loginBtn);
        signUp = (TextView) findViewById(R.id.signUp);
        forgetPass = (TextView) findViewById(R.id.forgotPass);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),SecondActivity.class));
        }

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PasswordActivity.class));
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry(Name.getText().toString(),Password.getText().toString());
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });
    }

    private void entry(String uName, String uPass){
        firebaseAuth.signInWithEmailAndPassword(uName,uPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> rest) {
                if(rest.isSuccessful()){
                    verify();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter correct Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verify(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
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