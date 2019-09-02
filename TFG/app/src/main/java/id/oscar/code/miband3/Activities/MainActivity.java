package id.oscar.code.miband3.Activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Set;
import java.lang.Object;

import id.oscar.code.miband3.Activities.ui.login.LoginActivity;
import id.oscar.code.miband3.Helpers.CustomBluetoothProfile;
import id.oscar.code.miband3.R;

public class MainActivity extends Activity {

    Boolean isListeningHeartRate = false;

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    BluetoothAdapter bluetoothAdapter;
    BluetoothGatt bluetoothGatt;
    BluetoothDevice bluetoothDevice;

    Button btnStartConnecting, btnGetBatteryInfo, btnGetHeartRate, btnStartVibrate, btnStopVibrate, btnProbarPasos, btnAceptar;
    EditText txtPhysicalAddress;
    TextView txtState, txtByte;
    private String mDeviceName;
    private String mDeviceAddress;
    public short pasos;
    public short distancia;
    public short calorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initializeObjects();
        initilaizeComponents();
        initializeEvents();

        getBoundedDevice();

    }

    void getBoundedDevice() {

        if(mDeviceAddress.isEmpty() && mDeviceName.isEmpty())
        {
            mDeviceName = getIntent().getStringExtra(EXTRAS_DEVICE_NAME);
            mDeviceAddress = getIntent().getStringExtra(EXTRAS_DEVICE_ADDRESS);
        }
        txtPhysicalAddress.setText(mDeviceAddress);

        Set<BluetoothDevice> boundedDevice = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice bd : boundedDevice) {
            if (bd.getName().contains("Mi Band 3")) {
                txtPhysicalAddress.setText(bd.getAddress());
            }
        }
    }

    public void setmDeviceName(String mDeviceName) {
        this.mDeviceName = mDeviceName;
    }

    public void setmDeviceAddress(String mDeviceAddress) {
        this.mDeviceAddress = mDeviceAddress;
    }

    void initializeObjects() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    void initilaizeComponents() {
        btnStartConnecting = (Button) findViewById(R.id.btnStartConnecting);
        btnStartVibrate = (Button) findViewById(R.id.btnStartVibrate);
        btnStopVibrate = (Button) findViewById(R.id.btnStopVibrate);
        txtPhysicalAddress = (EditText) findViewById(R.id.txtPhysicalAddress);
        txtState = (TextView) findViewById(R.id.txtState);
        txtByte = (TextView) findViewById(R.id.txtByte);
        btnProbarPasos = (Button) findViewById(R.id.btnSendNotification);
        btnAceptar = (Button) findViewById(R.id.button2);
        mDeviceName = "";
        mDeviceAddress = "";
    }

    void initializeEvents() {
        btnStartConnecting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConnecting();
            }
        });
        btnStartVibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVibrate();
            }
        });
        btnStopVibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopVibrate();
            }
        });
        btnProbarPasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSteps();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateDisconnected();
                Intent home  = new Intent(MainActivity.this,HomeActivity.class);
                home.putExtra("username",getIntent().getExtras().getString("username"));
                home.putExtra("nombreDispositivo",mDeviceName);
                home.putExtra("direccionDispositivo",mDeviceAddress);
                startActivity(home);
            }
        });
    }

    void startConnecting() {

        String address = mDeviceAddress;
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);

        Log.v("test", "Connecting to " + address);
        Log.v("test", "Device name " + bluetoothDevice.getName());

        bluetoothGatt = bluetoothDevice.connectGatt(this, true, bluetoothGattCallback);

    }

    void stateConnected() {
        bluetoothGatt.discoverServices();

        txtState.setText("Connected");
    }

    void stateDisconnected() {
        bluetoothGatt.disconnect();
        txtState.setText("Disconnected");
    }

    void startScanHeartRate() {
        txtByte.setText("...");
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.HeartRate.service)
                .getCharacteristic(CustomBluetoothProfile.HeartRate.controlCharacteristic);
        bchar.setValue(new byte[]{21, 2, 1});
        bluetoothGatt.writeCharacteristic(bchar);
    }

    void listenHeartRate() {
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.HeartRate.service)
                .getCharacteristic(CustomBluetoothProfile.HeartRate.measurementCharacteristic);
        bluetoothGatt.setCharacteristicNotification(bchar, true);
        BluetoothGattDescriptor descriptor = bchar.getDescriptor(CustomBluetoothProfile.HeartRate.descriptor);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        bluetoothGatt.writeDescriptor(descriptor);
        isListeningHeartRate = true;
    }

    void getBatteryStatus() {
        txtByte.setText("...");
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.Basic.service)
                .getCharacteristic(CustomBluetoothProfile.Basic.batteryCharacteristic);
        bchar.setValue(new byte[]{21, 2, 1});
        if (!bluetoothGatt.readCharacteristic(bchar)) {
            Toast.makeText(this, "Failed get battery info", Toast.LENGTH_SHORT).show();
        }
    }

    void getSteps() {
        txtByte.setText("...");
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.Basic.service)
                .getCharacteristic(CustomBluetoothProfile.Basic.stepsCharacteristic);
        bchar.setValue(new byte[]{21, 2, 1});
        if (!bluetoothGatt.readCharacteristic(bchar)) {
            Toast.makeText(this, "Failed get battery info", Toast.LENGTH_SHORT).show();
        }
    }

    void startVibrate() {
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.AlertNotification.service)
                .getCharacteristic(CustomBluetoothProfile.AlertNotification.alertCharacteristic);
        bchar.setValue(new byte[]{2});
        if (!bluetoothGatt.writeCharacteristic(bchar)) {
            Toast.makeText(this, "Failed start vibrate", Toast.LENGTH_SHORT).show();
        }
    }

    void stopVibrate() {
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.AlertNotification.service)
                .getCharacteristic(CustomBluetoothProfile.AlertNotification.alertCharacteristic);
        bchar.setValue(new byte[]{0});
        if (!bluetoothGatt.writeCharacteristic(bchar)) {
            Toast.makeText(this, "Failed stop vibrate", Toast.LENGTH_SHORT).show();
        }
    }

    void sendNotification ()
    {
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.AlertNotification.service)
                .getCharacteristic(CustomBluetoothProfile.AlertNotification.alertCharacteristic);

        byte notif = 5;
        byte alert = 1;
        byte[] mensaje;
        byte [] parametros;
        byte[] bytes;

        String value = "Ejemplo";

        parametros=new byte[]{notif,alert};
        bytes = value.getBytes(StandardCharsets.US_ASCII);
        mensaje= unitBytes(parametros,bytes);

        Log.v("TEST",new String(bytes));

        bchar.setValue(mensaje);
        bluetoothGatt.writeCharacteristic(bchar);
    }

    public byte[] unitBytes(byte[] a, byte[] b){

        byte[] mensaje= new byte[a.length+b.length];
        System.arraycopy(a,0,mensaje,0,a.length);
        System.arraycopy(b,0,mensaje,a.length,b.length);

        return mensaje;
    }

    final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            Log.v("test", "onConnectionStateChange");

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                stateConnected();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                stateDisconnected();
            }

        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.v("test", "onServicesDiscovered");
            listenHeartRate();
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.v("test", "onCharacteristicRead");
            byte[] data = characteristic.getValue();

            txtByte.setText(Arrays.toString(data));
            int steps = 0xff & data[1] | (0xff & data[2]) << 8;
            pasos= (short) steps;
            int distanza = ((((data[5] & 255) | ((data[6] & 255) << 8)) | (data[7] & 16711680)) | ((data[8] & 255) << 24));
            distancia= (short) distanza;
            int calorie = ((((data[9] & 255) | ((data[10] & 255) << 8)) | (data[11] & 16711680)) | ((data[12] & 255) << 24));
            calorias = (short) calorie;

            txtByte.setText("Pasos = " + Short.toString(pasos) + ", distancia = " + Short.toString(distancia) + ", calorias = " + Short.toString(calorias));
           /* Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);*/
            //txtByte.setText(Integer.toString(steps));
        }

        public short getPasos()
        {
            return pasos;
        }

        public short getDistancia()
        {
            return distancia;
        }

        public short getCalorias()
        {
            return calorias;
        }


        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.v("test", "onCharacteristicWrite");
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            Log.v("test", "onCharacteristicChanged");
            byte[] data = characteristic.getValue();
            txtByte.setText(Arrays.toString(data));
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            Log.v("test", "onDescriptorRead");
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            Log.v("test", "onDescriptorWrite");
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
            Log.v("test", "onReliableWriteCompleted");
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
            Log.v("test", "onReadRemoteRssi");
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
            Log.v("test", "onMtuChanged");
        }

    };

}
