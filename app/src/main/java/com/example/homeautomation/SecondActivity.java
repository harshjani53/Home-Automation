package com.example.homeautomation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private FirebaseAuth firebaseAuth;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logoutmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logoutMenu) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        firebaseAuth = FirebaseAuth.getInstance();
        TextView tvDev1 = findViewById(R.id.devOne);
        TextView tvDev2 = findViewById(R.id.devTwo);
        Button openList = findViewById(R.id.btnCon);

        tvDev1.setOnClickListener(v -> {
            if(!bluetoothAdapter.isEnabled())
            {
                Toast.makeText(getApplicationContext(),"Please Turn On Bluetooth",Toast.LENGTH_LONG).show();
            }
            else{
                try {
                    startActivity(new Intent(getApplicationContext(), ActDev1.class));
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Please Select One of Paired Devices First",Toast.LENGTH_LONG).show();
                }
            }});

        tvDev2.setOnClickListener(v -> {
            if(!bluetoothAdapter.isEnabled())
            {
                Toast.makeText(getApplicationContext(),"Please Turn On Bluetooth",Toast.LENGTH_LONG).show();
            }
            else{
                try {
                    startActivity(new Intent(getApplicationContext(), ActDev2.class));
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Please Select One of Paired Devices First",Toast.LENGTH_LONG).show();
                }
            }});
        openList.setOnClickListener(v -> {
            if(!bluetoothAdapter.isEnabled() || bluetoothAdapter==null){
                Toast.makeText(getApplicationContext(),"Please Turn On Bluetooth",Toast.LENGTH_LONG).show();
            }
            else{
                startActivity(new Intent(getApplicationContext(), listActivity.class));
            }
        });
    }
}