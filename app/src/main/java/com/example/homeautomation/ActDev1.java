package com.example.homeautomation;
//someone was here
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.UUID;
import java.util.logging.Handler;

public class ActDev1 extends AppCompatActivity {

    //static final UUID uuid = UUID.fromString("00000000-0000-1000-8000-00805F9B34FB");
   // static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    static final UUID uuid = UUID.fromString("0000110a-0000-1000-8000-00805f9b34fb");
    private FirebaseAuth firebaseAuth;
    private Button onButton1;
    private Button offButton1;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logoutmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }


     //   private BluetoothSocket fallbackSocket;

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
        setContentView(R.layout.activity_act_dev1);
        onButton1 = (Button) findViewById(R.id.onBtn1);
        offButton1 = (Button) findViewById(R.id.offBtn1);

        getSupportActionBar().setTitle("Device 1");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        System.out.println(bluetoothAdapter.getBondedDevices());

        BluetoothDevice bluetoothDevice= bluetoothAdapter.getRemoteDevice("80:AD:16:A6:02:41");
      //  UUID myuu = bluetoothDevice.getUuids()[0].getUuid();
      //  System.out.println(myuu);
        try {

        }
        catch (Exception e){}
        onButton1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
            try {

            //    bluetoothDevice.getUuids();
            //    ParcelUuid[] uuuu=(ParcelUuid[])method.invoke(bluetoothDevice,args);
              //  bluetoothSocket = (BluetoothSocket) bluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(bluetoothDevice, 1);
                bluetoothSocket=bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
                bluetoothSocket.connect();
                System.out.println("11111111111111111111");
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        });
        offButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }



}
