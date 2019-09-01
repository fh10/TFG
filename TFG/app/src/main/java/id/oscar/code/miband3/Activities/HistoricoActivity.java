package id.oscar.code.miband3.Activities;

import android.content.Intent;
import android.database.Cursor;
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
                        home.putExtra("nombreDispositivo",getIntent().getStringExtra("nombreDispositivo"));
                        home.putExtra("direccionDispositivo",getIntent().getStringExtra("direccionDispositivo"));
                        startActivity(home);
                        return true;
                    case R.id.camino_al_exito:
                        Intent ce = new Intent(HistoricoActivity.this, CaminoAlExito.class);
                        ce.putExtra("username",getIntent().getExtras().getString("username"));
                        ce.putExtra("nombreDispositivo",getIntent().getStringExtra("nombreDispositivo"));
                        ce.putExtra("direccionDispositivo",getIntent().getStringExtra("direccionDispositivo"));
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

        /*Calendar cal = Calendar.getInstance();

        String fechaHoy = cal.get(Calendar.DAY_OF_MONTH) + "/" +cal.get(Calendar.MONTH)+ "/" + cal.get(Calendar.YEAR);*/

        int dpValue = 0;

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams( TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
            String fechaDB = cursor.getString(2);
            TextView fecha = new TextView(this);
            fecha.setText(fechaDB);
            fecha.setGravity(Gravity.CENTER);
            row.setBackgroundColor(0xD7D9DC);
            dpValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, this.getResources().getDisplayMetrics());
            fecha.setPadding(dpValue,dpValue,dpValue,dpValue);
            fecha.setWidth(95);
            row.addView(fecha);

            String pasosDB = cursor.getString(3);
            TextView pasos = new TextView(this);
            pasos.setText(pasosDB);
            pasos.setGravity(Gravity.CENTER);
            pasos.setBackgroundColor(0xD7D9DC);
            dpValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, this.getResources().getDisplayMetrics());
            pasos.setPadding(dpValue,dpValue,dpValue,dpValue);
            pasos.setWidth(95);
            row.addView(pasos);

            String distanciaDB = cursor.getString(5);
            TextView distancia = new TextView(this);
            distancia.setText(distanciaDB);
            distancia.setGravity(Gravity.CENTER);
            distancia.setBackgroundColor(0xD7D9DC);
            dpValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, this.getResources().getDisplayMetrics());
            distancia.setPadding(dpValue,dpValue,dpValue,dpValue);
            distancia.setWidth(95);
            row.addView(distancia);

            String caloriasDB = cursor.getString(4);
            TextView calorias = new TextView(this);
            calorias.setText(caloriasDB);
            calorias.setGravity(Gravity.CENTER);
            calorias.setBackgroundColor(0xD7D9DC);
            dpValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, this.getResources().getDisplayMetrics());
            calorias.setPadding(dpValue,dpValue,dpValue,dpValue);
            calorias.setWidth(95);
            row.addView(calorias);

            tabla.addView(row);
        }
    }
}
