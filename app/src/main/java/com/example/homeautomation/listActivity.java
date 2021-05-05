package com.example.homeautomation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.Set;

public class listActivity extends AppCompatActivity {

    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private FirebaseAuth firebaseAuth;
    SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logoutmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

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
        setContentView(R.layout.activity_list);

        sharedPreferences = getSharedPreferences("Mac", MODE_PRIVATE);
        firebaseAuth =FirebaseAuth.getInstance();
        ListView listView = findViewById(R.id.pairList);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Paired Devices");

        Set<BluetoothDevice> bluetoothDevices = bluetoothAdapter.getBondedDevices();
        String[] deviceName = new String[bluetoothDevices.size()];
        int n = 0;

        if (bluetoothDevices.size() > 0) {
            for (BluetoothDevice device : bluetoothDevices) {
                deviceName[n] = device.getName()+"-->" +device.getAddress();
                n++;
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceName);
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {
                String[] fetch = deviceName[position].split("-->");
                sharedPreferences.getString("MacAddress", fetch[1]);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("MacAddress",fetch[1]);
                editor.apply();
                startActivity(new Intent(getApplicationContext(), SecondActivity.class));
            });
        }
    }
}