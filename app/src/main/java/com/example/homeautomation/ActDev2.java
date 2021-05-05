package com.example.homeautomation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import java.io.OutputStream;
import java.util.Objects;
import java.util.UUID;

public class ActDev2 extends AppCompatActivity {

    final private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public Button onButton2;
    public Button offButton2;
    public BluetoothAdapter bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
    public BluetoothSocket bluetoothSocket=null;
    public BluetoothDevice bluetoothDevice= null;
    SharedPreferences sharedPreferences;

    private void sendMessage(BluetoothSocket socket, char msg) {
        OutputStream outStream;
        try {
            outStream = socket.getOutputStream();
            byte[] byteString = (msg + " ").getBytes();
            byteString[byteString.length - 1] = 0;
            outStream.write(byteString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logoutmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logoutMenu){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dev2);
        onButton2 =  findViewById(R.id.onBtn2);
        offButton2 =  findViewById(R.id.offBtn2);
        sharedPreferences = getSharedPreferences("Mac",MODE_PRIVATE);
        String gotAddress = sharedPreferences.getString("MacAddress", "");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Device 2");

        try{
            bluetoothDevice = bluetoothAdapter.getRemoteDevice(gotAddress);
            UUID uuid = bluetoothDevice.getUuids()[0].getUuid();
            bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Please Connect to one of your Paired Devices First",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),SecondActivity.class));
        }

        onButton2.setOnClickListener(v -> {
            if(bluetoothSocket.isConnected()){
                sendMessage(bluetoothSocket, 'B');
                Toast.makeText(getApplicationContext(), "ON",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Something Wrong Happened",Toast.LENGTH_SHORT).show();
            }
        });

        offButton2.setOnClickListener(v -> {
            if(bluetoothSocket.isConnected()){
                sendMessage(bluetoothSocket, 'b');
                Toast.makeText(getApplicationContext(), "OFF",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Something Wrong Happened",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        try {
            bluetoothSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}