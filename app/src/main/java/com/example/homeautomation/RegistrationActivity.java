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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Objects;

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

        name = findViewById(R.id.nameReg);
        eMail = findViewById(R.id.eMailReg);
        pass = findViewById(R.id.passReg);
        Button register = findViewById(R.id.btnReg);
        TextView alreadySignUp = findViewById(R.id.alreadySignUp);
        firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(v -> {
            if(permission()){
                String userEmail = eMail.getText().toString().trim();
                String userPass = pass.getText().toString().trim();
                userPass = sha256Hex(userPass);
                System.out.println("3333333333333333333333333333     " + userPass);

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
        password = sha256Hex(password);
        System.out.println(password);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference(Objects.requireNonNull(firebaseAuth.getUid()));
        HomeAutomationDatabase database = new HomeAutomationDatabase(storedName, emailAddress, password);
        ref.setValue(database);
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