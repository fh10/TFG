package id.oscar.code.miband3.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import id.oscar.code.miband3.Helpers.DatabaseHelper;
import id.oscar.code.miband3.Helpers.Usuario;
import id.oscar.code.miband3.R;

public class DesafiosActivity extends AppCompatActivity {

    DatabaseHelper db;
    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafios);
        db = new DatabaseHelper(this);

        user = db.getUser(getIntent().getStringExtra("username"));

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent home = new Intent(DesafiosActivity.this, HomeActivity.class);
                        home.putExtra("username", getIntent().getExtras().getString("username"));
                        home.putExtra("nombreDispositivo", getIntent().getStringExtra("nombreDispositivo"));
                        home.putExtra("direccionDispositivo", getIntent().getStringExtra("direccionDispositivo"));
                        startActivity(home);
                        return true;
                    case R.id.camino_al_exito:
                        Intent ce = new Intent(DesafiosActivity.this, CaminoAlExito.class);
                        ce.putExtra("username", getIntent().getExtras().getString("username"));
                        ce.putExtra("nombreDispositivo", getIntent().getStringExtra("nombreDispositivo"));
                        ce.putExtra("direccionDispositivo", getIntent().getStringExtra("direccionDispositivo"));
                        startActivity(ce);
                        return true;
                    case R.id.historico:
                        Intent his = new Intent(DesafiosActivity.this, HistoricoActivity.class);
                        his.putExtra("username", getIntent().getExtras().getString("username"));
                        his.putExtra("nombreDispositivo", getIntent().getStringExtra("nombreDispositivo"));
                        his.putExtra("direccionDispositivo", getIntent().getStringExtra("direccionDispositivo"));
                        startActivity(his);
                        return true;
                    case R.id.perfil:
                        Intent perfil = new Intent(DesafiosActivity.this, PerfilActivity.class);
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
}
