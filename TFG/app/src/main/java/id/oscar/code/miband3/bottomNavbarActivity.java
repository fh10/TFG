package id.oscar.code.miband3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import id.oscar.code.miband3.Activities.HistoricoActivity;
import id.oscar.code.miband3.Activities.HomeActivity;
import id.oscar.code.miband3.Activities.ui.login.LoginActivity;

public class bottomNavbarActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent home = new Intent(bottomNavbarActivity.this, HomeActivity.class);
                    startActivity(home);
                    return true;
                /*case R.id.camino_al_exito:
                    Intent ce = new Intent(bottomNavbarActivity.this, CaminoAlExito.class);
                    startActivity(ce);
                    return true;*/
                case R.id.historico:
                    Intent his = new Intent(bottomNavbarActivity.this, HistoricoActivity.class);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navbar);
        BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.nav_view);
       // mTextMessage = (TextView) findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
