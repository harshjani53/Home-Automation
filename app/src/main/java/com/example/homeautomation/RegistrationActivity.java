package com.example.homeautomation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private EditText eMail;
    private EditText name;
    private EditText pass;
    private FirebaseAuth firebaseAuth;
    String emailAddress;
    String password;
    String storedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = (EditText) findViewById(R.id.nameReg);
        eMail = (EditText) findViewById(R.id.eMailReg);
        pass = (EditText) findViewById(R.id.passReg);
        Button register = (Button) findViewById(R.id.btnReg);
        TextView alreadySignUp = (TextView) findViewById(R.id.alreadySignUp);
        firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(v -> {
            if(permission()){
                String userEmail = eMail.getText().toString().trim();
                String userPass = pass.getText().toString().trim();

                firebaseAuth.createUserWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(res -> {
                    if(res.isSuccessful()) {
                        emailVerify();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Registration Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        alreadySignUp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),MainActivity.class)));
    }

    private void emailVerify(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!= null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Data();
                    Toast.makeText(getApplicationContext(),"Email Verification mail has been sent",Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Verification Failed",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private Boolean permission(){
        boolean perm=false;
        String uName = name.getText().toString();
        String uPass = pass.getText().toString();
        String uEmail = eMail.getText().toString();

        if (uName.isEmpty() || uPass.isEmpty() || uEmail.isEmpty()){
            Toast.makeText(this,"Please Enter Credentials",Toast.LENGTH_SHORT).show();
        }
        else{
            perm=true;
        }
        return perm;
    }

    private void Data(){
        storedName = name.getText().toString();
        emailAddress = eMail.getText().toString();
        password = pass.getText().toString();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference(firebaseAuth.getUid());
        HomeAutomationDatabase database = new HomeAutomationDatabase(storedName, emailAddress, password);
        ref.setValue(database);
    }
}