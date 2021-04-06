package com.example.homeautomation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private EditText eMail;
    private EditText name;
    private EditText pass;
    private Button register;
    private TextView alreadySignUp;
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
        register = (Button) findViewById(R.id.btnReg);
        alreadySignUp = (TextView) findViewById(R.id.alreadySignUp);

        firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(permission()){
                    String userEmail = eMail.getText().toString().trim();
                    String userPass = pass.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> res) {
                            if(res.isSuccessful()) {
                                emailVerify();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Registration Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        alreadySignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }

    private void emailVerify(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!= null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
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
                }
            });
        }
    }
    private Boolean permission(){
        Boolean perm=false;
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