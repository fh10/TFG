package id.oscar.code.miband3.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;

import id.oscar.code.miband3.Helpers.DatabaseHelper;
import id.oscar.code.miband3.Helpers.Usuario;
import id.oscar.code.miband3.R;

public class HistoricoActivity extends AppCompatActivity {

    TableLayout tabla;
    TextView fechaEdit;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        db = new DatabaseHelper(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent home = new Intent(HistoricoActivity.this, HomeActivity.class);
                        home.putExtra("username",getIntent().getExtras().getString("username"));
                        startActivity(home);
                        return true;
                    case R.id.camino_al_exito:
                        Intent ce = new Intent(HistoricoActivity.this, CaminoAlExito.class);
                        ce.putExtra("username",getIntent().getExtras().getString("username"));
                        startActivity(ce);
                        return true;
                    case R.id.historico:
                       /* Intent his = new Intent(HomeActivity.this, HistoricoActivity.class);
                        startActivity(his);*/
                        return true;
                case R.id.perfil:
                    Intent perfil = new Intent(HistoricoActivity.this, PerfilActivity.class);
                    perfil.putExtra("username",getIntent().getExtras().getString("username"));
                    startActivity(perfil);
                    return true;
                }
                return false;
            }
        });
        navigation.setSelectedItemId(R.id.historico);

        tabla = (TableLayout) findViewById(R.id.tabla);

        fechaEdit = (TextView) findViewById(R.id.fecha);
        fechaEdit.setBackgroundColor(0x76FB5E);

        Usuario user = db.getUser(getIntent().getExtras().getString("username"));

        Cursor cursor = db.historicoEjercicios(String.valueOf(user.getId()));

        TableRow row = new TableRow(this);
        TableRow row2 = new TableRow(this);

        row.setLayoutParams(new TableLayout.LayoutParams( TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));

        row.setBackgroundColor(0xD7D9DC);
        int value = 10;
        int dpValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, this.getResources().getDisplayMetrics());

        TextView fecha = new TextView(this);
        fecha.setText("14/07/2019");
        fecha.setGravity(Gravity.CENTER);
        fecha.setPadding(dpValue,dpValue,dpValue,dpValue);
        fecha.setWidth(95);

        row.addView(fecha);

        TextView fecha2 = new TextView(this);
        fecha2.setText("14/07/2019");
        fecha2.setGravity(Gravity.CENTER);
        fecha2.setPadding(dpValue,dpValue,dpValue,dpValue);
        row2.addView(fecha2);

        TextView pasos = new TextView(this);
        pasos.setText("14563");
        pasos.setGravity(Gravity.CENTER);
        row.addView(pasos);

        TextView pasos2 = new TextView(this);
        pasos2.setText("14563");
        pasos2.setGravity(Gravity.CENTER);
        row2.addView(pasos2);

        TextView distancia = new TextView(this);
        distancia.setText("12589");
        distancia.setGravity(Gravity.CENTER);
        row.addView(distancia);

        TextView distancia2 = new TextView(this);
        distancia2.setText("12589");
        distancia2.setGravity(Gravity.CENTER);
        row2.addView(distancia2);

        TextView calorias = new TextView(this);
        calorias.setText("2563");
        calorias.setGravity(Gravity.CENTER);
        row.addView(calorias);

        TextView calorias2 = new TextView(this);
        calorias2.setText("2563");
        calorias2.setGravity(Gravity.CENTER);
        row2.addView(calorias2);

        tabla.addView(row);
        tabla.addView(row2);
    }
}
