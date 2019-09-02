package id.oscar.code.miband3.Activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

import id.oscar.code.miband3.Helpers.CustomBluetoothProfile;
import id.oscar.code.miband3.Helpers.DatabaseHelper;
import id.oscar.code.miband3.Helpers.Usuario;
import id.oscar.code.miband3.R;

public class DesafioActivity extends AppCompatActivity {

    ImageView imagen;
    TextView descripcionText;
    TextView tiempoText;
    TextView repeticionesText;
    TextView descripcionEjercicioText;
    TextView contadorText;
    TextView contadorMinText;
    TextView tiempoRestante;
    Button siguiente;
    Button anterior;
    Button comenzar;
    Random random;
    Usuario user;
    int cont;
    int tiempo; //30 s *1000 milisegundos 20 repeticiones cada una con un descanso de 60 segundos.
    int pasos;
    String desc;
    DatabaseHelper db;
    MainActivity pulsera;
    String nombre = "";
    String tiempoEjercicio = "";
    String repeticiones = "";
    int minutos;
    int pasosObtenidos;
    int caloriasObtenidas;
    int pasosObtenidosAntes;
    int caloriasObtenidasAntes;
    ArrayList <Integer> pulsacionesObtenidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio);
        db = new DatabaseHelper(this);
        cont = 0;
        pulsacionesObtenidas = new ArrayList<>();
        caloriasObtenidas = 0;
        imagen = (ImageView) findViewById(R.id.imageView5);
        descripcionText = (TextView) findViewById(R.id.descripcion);
        descripcionEjercicioText = (TextView) findViewById(R.id.descripcion_correr);
        contadorText = (TextView) findViewById(R.id.contador);
        tiempoText = (TextView) findViewById(R.id.tiempo);
        contadorMinText =  (TextView) findViewById(R.id.contador2);
        repeticionesText = (TextView) findViewById(R.id.repeticiones);
        tiempoRestante = (TextView) findViewById(R.id.textView15);
        siguiente = (Button) findViewById(R.id.siguiente);
        anterior = (Button) findViewById(R.id.anterior);
        comenzar = (Button) findViewById(R.id.comenzar_ejercicio);
        pasosObtenidos = 0;
        pasosObtenidosAntes = 0;
        caloriasObtenidas = 0;

        user = db.getUser(getIntent().getStringExtra("username"));

        random = new Random();
        int num = random.nextInt(3 - 1 + 1) + 1;

        if (num == 1) {
            imagen.setImageResource(R.drawable.puente1);
            nombre = "puente1";
        } else if (num == 2) {
            imagen.setImageResource(R.drawable.puente2);
            nombre = "puente1";
        } else {
            imagen.setImageResource(R.drawable.puente3);
            nombre = "puente1";
        }

        desc = db.getDescripcion(nombre);

        descripcionText.setText("Descripción= "+desc);

        if(user.getNivel() <= 7)
        {
            tiempoEjercicio = "20 s";
            repeticiones = "2 series con 2 repeticiones del ejercicio";
        }
        else if(user.getNivel() <= 17)
        {
            tiempoEjercicio = "30 s";
            repeticiones = "2 series con 4 repeticiones del ejercicio";
        }
        else
        {
            tiempoEjercicio = "40 s";
            repeticiones = "3 series con 3 repeticiones del ejercicio";
        }

        tiempoText.setText("Tiempo de cada ejercicio = " + tiempoEjercicio);
        repeticionesText.setText("Repeticiones = " + repeticiones);

        cont++;

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int aux = 0;

                if (cont == 1) {
                    aux = random.nextInt(3 - 1 + 1) + 1;

                    if (aux == 1) {
                        imagen.setImageResource(R.drawable.abdominales1);
                        nombre = "abdominales1";
                    } else if (aux == 2) {
                        imagen.setImageResource(R.drawable.abdominales2);
                        nombre = "abdominales2";
                    } else {
                        imagen.setImageResource(R.drawable.abdominales3);
                        nombre = "abdominales3";
                    }

                    desc = db.getDescripcion(nombre);

                    descripcionText.setText("Descripción= "+desc);

                } else if (cont == 2) {
                    aux = random.nextInt(3 - 1 + 1) + 1;

                    if (aux == 1) {
                        imagen.setImageResource(R.drawable.sentadilla1);
                        nombre = "sentadilla1";
                    } else if (aux == 2) {
                        imagen.setImageResource(R.drawable.sentadilla2);
                        nombre = "sentadilla2";
                    } else {
                        imagen.setImageResource(R.drawable.sentadilla3);
                        nombre = "sentadilla3";
                    }

                    desc = db.getDescripcion(nombre);

                    descripcionText.setText("Descripción= "+desc);

                } else if (cont == 3) {
                    aux = random.nextInt(3 - 1 + 1) + 1;

                    if (aux == 1) {
                        imagen.setImageResource(R.drawable.brazos1);
                        nombre = "brazos1";
                    } else if (aux == 2) {
                        imagen.setImageResource(R.drawable.brazos2);
                        nombre = "brazos2";
                    } else {
                        imagen.setImageResource(R.drawable.brazos3);
                        nombre = "brazos3";
                    }

                    desc = db.getDescripcion(nombre);

                    descripcionText.setText("Descripción= "+desc);
                } else if (cont == 4) {
                    if (user.getNivel() > 7) {
                        aux = random.nextInt(3 - 1 + 1) + 1;

                        if (aux == 1) {
                            imagen.setImageResource(R.drawable.brazos1);
                            nombre = "brazos1";
                        } else if (aux == 2) {
                            imagen.setImageResource(R.drawable.brazos2);
                            nombre = "brazos2";
                        } else {
                            imagen.setImageResource(R.drawable.brazos3);
                            nombre = "brazos3";
                        }

                        desc = db.getDescripcion(nombre);

                        descripcionText.setText("Descripción= "+desc);
                    } else {
                        imagen.setVisibility(View.INVISIBLE);
                        descripcionText.setVisibility(View.INVISIBLE);
                        tiempoText.setVisibility(View.INVISIBLE);
                        repeticionesText.setVisibility(View.INVISIBLE);
                        contadorText.setVisibility(View.VISIBLE);
                        descripcionEjercicioText.setVisibility(View.VISIBLE);
                        contadorMinText.setVisibility(View.VISIBLE);
                        pasos = 9000;
                        tiempo = 60000;
                        minutos = 10;
                        String mensaje = "En este último ejercicio tendrás que correr durante 10 minutos y conseguir realizar " + pasos + " pasos.";
                        descripcionEjercicioText.setText(mensaje);
                        comenzar.setVisibility(View.VISIBLE);
                        contadorText.setText((tiempo/1000)+"");
                        contadorMinText.setText(minutos+":");
                        siguiente.setVisibility(View.INVISIBLE);
                        tiempoRestante.setVisibility(View.VISIBLE);
                    }
                } else if (cont == 5) {
                    if (user.getNivel() > 17) {
                        aux = random.nextInt(3 - 1 + 1) + 1;

                        if (aux == 1) {
                            imagen.setImageResource(R.drawable.sentadilla2);
                            nombre = "sentadilla2";
                        } else if (aux == 2) {
                            imagen.setImageResource(R.drawable.sentadilla3);
                            nombre = "sentadilla3";
                        } else {
                            imagen.setImageResource(R.drawable.sentadilla1);
                            nombre = "sentadilla1";
                        }

                        desc = db.getDescripcion(nombre);

                        descripcionText.setText("Descripción= "+desc);
                    } else {
                        imagen.setVisibility(View.INVISIBLE);
                        descripcionText.setVisibility(View.INVISIBLE);
                        tiempoText.setVisibility(View.INVISIBLE);
                        repeticionesText.setVisibility(View.INVISIBLE);
                        contadorText.setVisibility(View.VISIBLE);
                        descripcionEjercicioText.setVisibility(View.VISIBLE);
                        pasos = 10000;
                        tiempo = 60000;
                        minutos = 15;
                        String mensaje = "En este último ejercicio tendrás que correr durante 10 minutos y conseguir realizar " + pasos + " pasos.";
                        descripcionEjercicioText.setText(mensaje);
                        comenzar.setVisibility(View.VISIBLE);
                        contadorMinText.setText(minutos+":");
                        siguiente.setVisibility(View.INVISIBLE);
                        tiempoRestante.setVisibility(View.VISIBLE);
                    }
                }
                else if(cont == 6)
                {
                    imagen.setVisibility(View.INVISIBLE);
                    descripcionText.setVisibility(View.INVISIBLE);
                    tiempoText.setVisibility(View.INVISIBLE);
                    repeticionesText.setVisibility(View.INVISIBLE);
                    contadorText.setVisibility(View.VISIBLE);
                    descripcionEjercicioText.setVisibility(View.VISIBLE);
                    tiempoRestante.setVisibility(View.VISIBLE);

                    if(user.getNivel() > 29)
                    {
                        pasos = 20000;
                        tiempo = 60000;
                        minutos = 25;
                    }
                    else
                    {
                        pasos = 30000;
                        tiempo = 60000;
                        minutos = 20;
                    }

                    String mensaje = "En este último ejercicio tendrás que correr durante 10 minutos y conseguir realizar " + pasos + " pasos.";
                    descripcionEjercicioText.setText(mensaje);
                    comenzar.setVisibility(View.VISIBLE);
                    contadorMinText.setText(minutos+":");
                    siguiente.setVisibility(View.INVISIBLE);
                }

                cont++;
            }
        });

        comenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pulsera pulsera = new Pulsera();

                pulsera.setmDeviceName(getIntent().getExtras().getString("nombreDispositivo"));
                pulsera.setmDeviceAddress(getIntent().getStringExtra("direccionDispositivo"));
                pulsera.startConnecting();
                pulsera.stateConnected();
                pulsera.getSteps();
                while (pasosObtenidos == 0)
                {
                    pulsera.getSteps();
                }
                pulsera.stateDisconnected();
                pasosObtenidosAntes = pasosObtenidos;
                caloriasObtenidasAntes = caloriasObtenidas;
                caloriasObtenidasAntes = pulsera.calorias;
                    MiContador contador = new MiContador(tiempo, 1000);
                    contador.start();
                    comenzar.setClickable(false);
            }
        });

        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ce = new Intent(DesafioActivity.this, CaminoAlExito.class);
                ce.putExtra("username", getIntent().getExtras().getString("username"));
                ce.putExtra("nombreDispositivo", getIntent().getStringExtra("nombreDispositivo"));
                ce.putExtra("direccionDispositivo", getIntent().getStringExtra("direccionDispositivo"));
                startActivity(ce);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent home = new Intent(DesafioActivity.this, HomeActivity.class);
                        home.putExtra("username", getIntent().getExtras().getString("username"));
                        home.putExtra("nombreDispositivo", getIntent().getStringExtra("nombreDispositivo"));
                        home.putExtra("direccionDispositivo", getIntent().getStringExtra("direccionDispositivo"));
                        startActivity(home);
                        return true;
                    case R.id.camino_al_exito:
                        Intent ce = new Intent(DesafioActivity.this, CaminoAlExito.class);
                        ce.putExtra("username", getIntent().getExtras().getString("username"));
                        ce.putExtra("nombreDispositivo", getIntent().getStringExtra("nombreDispositivo"));
                        ce.putExtra("direccionDispositivo", getIntent().getStringExtra("direccionDispositivo"));
                        startActivity(ce);
                        return true;
                    case R.id.historico:
                        Intent his = new Intent(DesafioActivity.this, HistoricoActivity.class);
                        his.putExtra("username", getIntent().getExtras().getString("username"));
                        his.putExtra("nombreDispositivo", getIntent().getStringExtra("nombreDispositivo"));
                        his.putExtra("direccionDispositivo", getIntent().getStringExtra("direccionDispositivo"));
                        startActivity(his);
                        return true;
                    case R.id.perfil:
                        Intent perfil = new Intent(DesafioActivity.this, PerfilActivity.class);
                        perfil.putExtra("username", getIntent().getExtras().getString("username"));
                        perfil.putExtra("nombreDispositivo", getIntent().getStringExtra("nombreDispositivo"));
                        perfil.putExtra("direccionDispositivo", getIntent().getStringExtra("direccionDispositivo"));
                        startActivity(perfil);
                        return true;
                }
                return false;
            }
        });
        //navigation.setSelectedItemId(R.id.camino_al_exito);
    }

    private class MiContador extends CountDownTimer {

        public MiContador(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {

            if(minutos == 0)
            {
                Pulsera pulsera = new Pulsera();

                pulsera.setmDeviceName(getIntent().getExtras().getString("nombreDispositivo"));
                pulsera.setmDeviceAddress(getIntent().getStringExtra("direccionDispositivo"));
                pulsera.startConnecting();
                pulsera.stateConnected();
                pulsera.getSteps();

                while (pasosObtenidos == 0)
                {
                    pulsera.getSteps();
                }

                Calendar cal = Calendar.getInstance();

                String fechaHoy = cal.get(Calendar.DAY_OF_MONTH) + "/" +cal.get(Calendar.MONTH)+ "/" + cal.get(Calendar.YEAR);

                int dis = (int)(pasosObtenidos*user.getZancada());
                int pasosRealizados = pasosObtenidos-pasosObtenidosAntes, disRealizada = dis-(int)(pasosObtenidosAntes*user.getZancada());
                int caloriasRealizadas = caloriasObtenidas-caloriasObtenidasAntes;

                db.addEjercicio(user.getId(),user.getUsername(),fechaHoy,pasosRealizados,disRealizada,caloriasRealizadas);

                if(pulsera.pasos < pasos)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(DesafioActivity.this).create();
                    alertDialog.setTitle("Alerta");
                    alertDialog.setMessage("No has superado el objetivo, ¡vuelve a intentarlo con más fuerza!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent ce = new Intent(DesafioActivity.this, CaminoAlExito.class);
                                    ce.putExtra("nombreDispositivo", getIntent().getStringExtra("nombreDispositivo"));
                                    ce.putExtra("direccionDispositivo", getIntent().getStringExtra("direccionDispositivo"));
                                    ce.putExtra("username",getIntent().getExtras().getString("username"));
                                    startActivity(ce);
                                }
                            });
                    alertDialog.show();
                }
                else
                {
                    user.setNivel(user.getNivel()+1);
                    db.actualizarUsuario(user);

                    AlertDialog alertDialog = new AlertDialog.Builder(DesafioActivity.this).create();
                    alertDialog.setTitle("Alerta");
                    alertDialog.setMessage("Has superado el desafio, ¡ENHORABUENA!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent ce = new Intent(DesafioActivity.this, CaminoAlExito.class);
                                    ce.putExtra("username",getIntent().getExtras().getString("username"));
                                    ce.putExtra("nombreDispositivo", getIntent().getStringExtra("nombreDispositivo"));
                                    ce.putExtra("direccionDispositivo", getIntent().getStringExtra("direccionDispositivo"));
                                    startActivity(ce);
                                }
                            });
                    alertDialog.show();
                }
            }
            else
            {
                MiContador contador = new MiContador(tiempo, 1000);
                minutos = minutos-1;
                contadorMinText.setText(minutos+":");
                contador.start();
                //pulsera2.stateDisconnected();

            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //texto a mostrar en cuenta regresiva en un textview
            contadorText.setText((millisUntilFinished/1000+""));

        }
    }

    private class Pulsera{
        Boolean isListeningHeartRate = false;

        public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
        public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
        BluetoothAdapter bluetoothAdapter;
        BluetoothGatt bluetoothGatt;
        BluetoothDevice bluetoothDevice;

        private String mDeviceName;
        private String mDeviceAddress;
        public short pasos;
        public short distancia;
        public short calorias;
        public boolean ok;


        public Pulsera()
        {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            ok = true;
        }

        public short getPasos() {
            return pasos;
        }

        public void setPasos(short pasos) {
            this.pasos = pasos;
        }

        public short getDistancia() {
            return distancia;
        }

        public void setDistancia(short distancia) {
            this.distancia = distancia;
        }

        public short getCalorias() {
            return calorias;
        }

        public void setCalorias(short calorias) {
            this.calorias = calorias;
        }

        public void setmDeviceName(String mDeviceName) {
            this.mDeviceName = mDeviceName;
        }

        public void setmDeviceAddress(String mDeviceAddress) {
            this.mDeviceAddress = mDeviceAddress;
        }

        void startConnecting() {

            String address = mDeviceAddress;
            bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);

            Log.v("test", "Connecting to " + address);
            Log.v("test", "Device name " + bluetoothDevice.getName());

            bluetoothGatt = bluetoothDevice.connectGatt(DesafioActivity.this, true, bluetoothGattCallback);
        }

        void stateConnected() {
            bluetoothGatt.discoverServices();
        }

        void stateDisconnected() {
            bluetoothGatt.disconnect();
        }

        void startScanHeartRate() {

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

        void getSteps() {
            while(ok) {
                try {
                        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.Basic.service)
                                .getCharacteristic(CustomBluetoothProfile.Basic.stepsCharacteristic);
                        bchar.setValue(new byte[]{21, 2, 1});
                        ok = false;
                        if (!bluetoothGatt.readCharacteristic(bchar)) {
                            Toast.makeText(DesafioActivity.this, "Failed get battery info", Toast.LENGTH_SHORT).show();
                        }
                } catch (Exception e) {

                }
            }
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

                    int steps = 0xff & data[1] | (0xff & data[2]) << 8;
                    pasos= (short) steps;
                    pasosObtenidos = pasos;
                    int distanza = ((((data[5] & 255) | ((data[6] & 255) << 8)) | (data[7] & 16711680)) | ((data[8] & 255) << 24));
                    distancia= (short) distanza;
                    int calorie = ((((data[9] & 255) | ((data[10] & 255) << 8)) | (data[11] & 16711680)) | ((data[12] & 255) << 24));
                    calorias = (short) calorie;
                    caloriasObtenidas = calorias;
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
                int heart = data[1] & 0xFF;
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
}
