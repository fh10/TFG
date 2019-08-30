package id.oscar.code.miband3.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import id.oscar.code.miband3.Helpers.DatabaseHelper;
import id.oscar.code.miband3.Helpers.Usuario;
import id.oscar.code.miband3.R;
import id.oscar.code.miband3.bottomNavbarActivity;

public class HomeActivity extends AppCompatActivity {

    TextView pasos;
    TextView distancia;
    TextView calorias;
    TextView imc;
    TextView peso;
    EditText nivel;
    EditText nivelSiguiente;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DatabaseHelper(this);
        pasos = (TextView) findViewById(R.id.pasos_realizados);
        distancia = (TextView) findViewById(R.id.distancia_recorrida);
        calorias = (TextView) findViewById(R.id.calorias_quemadas);
        imc = (TextView) findViewById(R.id.imc);
        peso = (TextView) findViewById(R.id.peso);
        nivel = (EditText) findViewById(R.id.colorNivel);
        nivelSiguiente = (EditText) findViewById(R.id.colorNivelSiguiente);

        Usuario user = db.getUser(getIntent().getExtras().getString("username"));
        Calendar fecha = Calendar.getInstance();

        Cursor cursor = db.historicoEjercicios(String.valueOf(user.getId()));

        nivel.setGravity(Gravity.CENTER);
        String aux = "Desafio " + (user.getNivel()+1);
        nivel.setText(aux);

        nivelSiguiente.setGravity(Gravity.CENTER);
        aux = "Desafio " + (user.getNivel()+2);
        nivelSiguiente.setText(aux);

        if(user.getNivel()> 7)
        {
            nivel.setBackgroundColor(0xB2B6BA);
        }
        else if(user.getNivel()>17)
        {
            nivel.setBackgroundColor(0xcc9900);
        }

        if(user.getNivel() +1> 7)
        {
            nivelSiguiente.setBackgroundColor(0xB2B6BA);
        }
        else if(user.getNivel() +1>17)
        {
            nivelSiguiente.setBackgroundColor(0xcc9900);
        }

        aux = peso.getText().toString() + " " + user.getPeso() + "Kg";
        peso.setText(aux);

        float valorIMC = user.getPeso()/(user.getAltura()*user.getAltura());
        aux = imc.getText().toString() + " " + valorIMC + "Kg/m2";
        imc.setText(aux);

        if (cursor.moveToFirst()) {
            aux = pasos.getText().toString() + " " + cursor.getString(3);
            pasos.setText(aux);
            aux = distancia.getText().toString() + " " + cursor.getString(5) + " m";
            distancia.setText(aux);
            aux = calorias.getText().toString() + " " + cursor.getString(4) + " cal";
            calorias.setText(aux);
        }
        else
        {
            aux = pasos.getText().toString() + "0";
            pasos.setText(aux);
            aux = distancia.getText().toString() + "0 metros";
            distancia.setText(aux);
            aux = calorias.getText().toString() + "0 cal";
            calorias.setText(aux);
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        /*Intent home = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(home);*/
                        return true;
                    case R.id.camino_al_exito:
                        Intent ce = new Intent(HomeActivity.this, CaminoAlExito.class);
                        ce.putExtra("username",getIntent().getExtras().getString("username"));
                        startActivity(ce);
                        return true;
                    case R.id.historico:
                        Intent his = new Intent(HomeActivity.this, HistoricoActivity.class);
                        his.putExtra("username",getIntent().getExtras().getString("username"));
                        startActivity(his);
                        return true;
                case R.id.perfil:
                    Intent perfil = new Intent(HomeActivity.this, PerfilActivity.class);
                    perfil.putExtra("username",getIntent().getExtras().getString("username"));
                    startActivity(perfil);
                    return true;
                }
                return false;
            }
        });
        navigation.setSelectedItemId(R.id.navigation_home);
    }
}
