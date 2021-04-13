package com.example.homeautomation;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Set;

public class SecondActivity extends AppCompatActivity {
    private TextView bluetoothInfo;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bcd;
    private FirebaseAuth firebaseAuth;
    private TextView tvDev1;
    private TextView tvDev2;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logoutmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu:{
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothInfo = (TextView) findViewById(R.id.bluetoothConnection);
        firebaseAuth = FirebaseAuth.getInstance();
        tvDev1 = (TextView) findViewById(R.id.devOne);
        tvDev2 = (TextView) findViewById(R.id.devTwo);

        tvDev1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if(!bluetoothAdapter.isEnabled())
                   {
                       Toast.makeText(getApplicationContext(),"Please Turn On Bluetooth",Toast.LENGTH_LONG).show();
                   }
                   else{
                       startActivity(new Intent(getApplicationContext(), ActDev1.class));
            }}
        });

        tvDev2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bluetoothAdapter.isEnabled())
                {
                    Toast.makeText(getApplicationContext(),"Please Turn On Bluetooth",Toast.LENGTH_LONG).show();
                }
                else{
                    startActivity(new Intent(getApplicationContext(), ActDev2.class));
            }}
        });

        if (bluetoothAdapter == null) {
            bluetoothInfo.setText("Bluetooth Not available");
        } else if (!bluetoothAdapter.isEnabled()) {
            bluetoothInfo.setText("Please Turn On Bluetooth");
        } else {
            bluetoothInfo.setText("Bluetooth connected");
        }
    }
}