package id.oscar.code.miband3.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent home = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(home);
                        return true;
                /*case R.id.camino_al_exito:
                    Intent ce = new Intent(bottomNavbarActivity.this, CaminoAlExito.class);
                    startActivity(ce);
                    return true;*/
                    case R.id.historico:
                        Intent his = new Intent(HomeActivity.this, HistoricoActivity.class);
                        startActivity(his);
                        return true;
                /*case R.id.perfil:
                    Intent perfil = new Intent(bottomNavbarActivity.this, Perfil.class);
                    startActivity(perfil);
                    return true;*/
                }
                return false;
            }
        };

        db = new DatabaseHelper(this);
        pasos = (TextView) findViewById(R.id.pasos_realizados);
        distancia = (TextView) findViewById(R.id.distancia_recorrida);
        calorias = (TextView) findViewById(R.id.calorias_quemadas);
        imc = (TextView) findViewById(R.id.imc);
        peso = (TextView) findViewById(R.id.peso);
        nivel = (EditText) findViewById(R.id.colorNivel);

        Usuario user = db.getUser(getIntent().getExtras().getString("username"));
        Calendar fecha = Calendar.getInstance();

        String fechaEjercicio= Integer.toString(fecha.get(Calendar.DATE)) + "/" + Integer.toString(fecha.get(Calendar.MONTH)) +  "/" +Integer.toString(fecha.get(Calendar.YEAR));
        Cursor cursor = db.historicoEjercicios(String.valueOf(user.getId()));

        if(user.getNivel()> 7)
        {
            nivel.setBackgroundColor(0xB2B6BA);
        }
        else if(user.getNivel()>17)
        {
            nivel.setBackgroundColor(0xcc9900);
        }

        String aux = peso.getText().toString() + " " + user.getPeso();
        peso.setText(aux);

        float valorIMC = user.getPeso()/(user.getAltura()*user.getAltura());
        aux = imc.getText().toString() + " " + valorIMC;
        imc.setText(aux);

        if (cursor.moveToFirst()) {
            aux = pasos.getText().toString() + " " + cursor.getString(3);
            pasos.setText(aux);
            aux = distancia.getText().toString() + " " + cursor.getString(5);
            distancia.setText(aux);
            aux = calorias.getText().toString() + " " + cursor.getString(4);
            calorias.setText(aux);
        }
        else
        {
            aux = pasos.getText().toString() + "0";
            pasos.setText(aux);
            aux = pasos.getText().toString() + "0";
            distancia.setText(aux);
            aux = pasos.getText().toString() + "0";
            calorias.setText(aux);
        }
    }
}
