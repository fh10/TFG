package id.oscar.code.miband3.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import id.oscar.code.miband3.Activities.ui.login.LoginActivity;
import id.oscar.code.miband3.Helpers.DatabaseHelper;
import id.oscar.code.miband3.Helpers.Usuario;
import id.oscar.code.miband3.R;

public class PerfilActivity extends AppCompatActivity {

    TextView nombre;
    TextView username;
    TextView fecha;
    TextView sexo;
    TextView peso;
    TextView altura;
    TextView pasos;
    TextView distancia;
    TextView calorias;
    EditText nivel;
    Button modificarButton;
    Button pulsera;
    Usuario user;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        db = new DatabaseHelper(this);
        nombre = (TextView) findViewById(R.id.nombreYApellidos);
        username = (TextView) findViewById(R.id.nombreUsuario);
        fecha = (TextView) findViewById(R.id.fecha_nacimiento);
        sexo = (TextView) findViewById(R.id.Sexo);
        peso = (TextView) findViewById(R.id.Peso);
        altura = (TextView) findViewById(R.id.Altura);
        pasos = (TextView) findViewById(R.id.Pasos_totales_realizados);
        distancia = (TextView) findViewById(R.id.distancia_total_recorrida);
        calorias = (TextView) findViewById(R.id.Calorias_totales_quemadas);
        nivel = (EditText) findViewById(R.id.colorNivel);
        modificarButton = (Button) findViewById(R.id.buttonModificar);
        pulsera = (Button) findViewById(R.id.button_conectar);

        user = db.getUser(getIntent().getExtras().getString("username"));

        String aux = user.getNombre() + " " + user.getApellidos();
        nombre.setText(aux);
        username.setText(user.getUsername());
        fecha.setText(user.getFecha_nac());


        if(user.getSexo() == 0)
        {
            aux = "Hombre";
        }
        else
        {
            aux = "Mujer";
        }

        sexo.setText(aux);
        aux = Float.toString(user.getPeso()) + " Kg";
        peso.setText(aux);
        aux = Float.toString(user.getAltura()) + " m";
        altura.setText(aux);
        aux = Integer.toString(user.getPasos());
        pasos.setText(aux);
        aux = Integer.toString(user.getRecorrido()) + " m";
        distancia.setText(aux);
        aux = Integer.toString(user.getCalorias()) + " cal";
        calorias.setText(aux);

        if(user.getNivel()> 7)
        {
            nivel.setBackgroundColor(0xB2B6BA);
        }
        else if(user.getNivel()>17)
        {
            nivel.setBackgroundColor(0xcc9900);
        }

        if(getIntent().getExtras().getString("nombreDispositivo") != null || getIntent().getExtras().getString("direccionDispositivo") != null)
        {
            pulsera.setClickable(false);
            Toast.makeText(this, "Ya estás conectado", Toast.LENGTH_SHORT).show();
        }

        nivel.setGravity(Gravity.CENTER);
        aux = "Desafio " + (user.getNivel()+1);
        nivel.setText(aux);

        modificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actualizar = new Intent(PerfilActivity.this, ActualizarUsuario.class);
                actualizar.putExtra("username", getIntent().getExtras().getString("username"));
                actualizar.putExtra("nombreDispositivo", getIntent().getStringExtra("nombreDispositivo"));
                actualizar.putExtra("direccionDispositivo", getIntent().getStringExtra("direccionDispositivo"));
                startActivity(actualizar);
            }
        });

        pulsera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent pulsera = new Intent(PerfilActivity.this, LoginActivity.class);
               startActivity(pulsera);
               finish();
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent home = new Intent(PerfilActivity.this, HomeActivity.class);
                        home.putExtra("username",getIntent().getStringExtra("username"));
                        home.putExtra("nombreDispositivo",getIntent().getStringExtra("nombreDispositivo"));
                        home.putExtra("direccionDispositivo",getIntent().getStringExtra("direccionDispositivo"));
                        startActivity(home);
                        return true;
                    case R.id.camino_al_exito:
                        Intent ce = new Intent(PerfilActivity.this, CaminoAlExito.class);
                        ce.putExtra("username",getIntent().getExtras().getString("username"));
                        ce.putExtra("nombreDispositivo",getIntent().getStringExtra("nombreDispositivo"));
                        ce.putExtra("direccionDispositivo",getIntent().getStringExtra("direccionDispositivo"));
                        startActivity(ce);
                        return true;
                    case R.id.historico:
                        Intent his = new Intent(PerfilActivity.this, HistoricoActivity.class);
                        his.putExtra("username",getIntent().getExtras().getString("username"));
                        his.putExtra("nombreDispositivo",getIntent().getStringExtra("nombreDispositivo"));
                        his.putExtra("direccionDispositivo",getIntent().getStringExtra("direccionDispositivo"));
                        startActivity(his);
                        return true;
                    case R.id.perfil:
                       /* Intent perfil = new Intent(DesafioActivity.this, PerfilActivity.class);
                        perfil.putExtra("username",getIntent().getExtras().getString("username"));
                        startActivity(perfil);*/
                        return true;
                }
                return false;
            }
        });
        navigation.setSelectedItemId(R.id.perfil);
    }
}
