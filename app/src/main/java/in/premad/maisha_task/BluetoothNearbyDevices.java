package in.premad.maisha_task;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.core.Context;

import java.util.ArrayList;

public class BluetoothNearbyDevices extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> mDeviceList = new ArrayList<String>();
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_nearby_devices);

        listView = (ListView) findViewById(R.id.listView);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startDiscovery();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        Toast.makeText(BluetoothNearbyDevices.this, "OnCreate", Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(android.content.Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                Toast.makeText(context, "ActionFound", Toast.LENGTH_SHORT).show();
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mDeviceList.add(device.getName() + "\n" + device.getAddress());
                Log.i("BT", device.getName() + "\n" + device.getAddress());
                listView.setAdapter(new ArrayAdapter<String>(BluetoothNearbyDevices.this,
                        android.R.layout.simple_list_item_1, mDeviceList));


                Toast.makeText(context, "ActionFound"+mDeviceList.toString(), Toast.LENGTH_SHORT).show();

            }
        }


    };

}