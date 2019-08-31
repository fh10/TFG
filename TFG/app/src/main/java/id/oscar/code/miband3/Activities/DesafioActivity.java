package id.oscar.code.miband3.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

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
    Button siguiente;
    Button anterior;
    Button comenzar;
    Random random;
    Usuario user;
    int cont;
    int tiempo; //30 s *1000 milisegundos 20 repeticiones cada una con un descanso de 60 segundos.
    int pasos;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio);
        db = new DatabaseHelper(this);
        cont = 0;
        imagen = (ImageView) findViewById(R.id.imageView5);
        descripcionText = (TextView) findViewById(R.id.descripcion);
        descripcionEjercicioText = (TextView) findViewById(R.id.descripcion_correr);
        contadorText = (TextView) findViewById(R.id.contador);
        tiempoText = (TextView) findViewById(R.id.tiempo);
        repeticionesText = (TextView) findViewById(R.id.repeticiones);
        siguiente = (Button) findViewById(R.id.siguiente);
        anterior = (Button) findViewById(R.id.anterior);
        comenzar = (Button) findViewById(R.id.comenzar_ejercicio);
        user = db.getUser(getIntent().getStringExtra("username"));

        random = new Random();
        int num =  random.nextInt(3 - 1 + 1) + 1;

        if(num == 1)
        {
            imagen.setImageResource(R.drawable.puente1);
        }
        else if(num == 2)
        {
            imagen.setImageResource(R.drawable.puente2);
        }
        else
        {
            imagen.setImageResource(R.drawable.puente3);
        }

        cont++;

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int aux =  0;

                if(cont == 1)
                {
                    aux = random.nextInt(3 - 1 + 1) + 1;

                    if(aux == 1)
                    {
                        imagen.setImageResource(R.drawable.abdominales1);
                    }
                    else if(aux == 2)
                    {
                        imagen.setImageResource(R.drawable.abdominales2);
                    }
                    else
                    {
                        imagen.setImageResource(R.drawable.abdominales3);
                    }
                }
                else if(cont == 2)
                {
                    aux = random.nextInt(3 - 1 + 1) + 1;

                    if(aux == 1)
                    {
                        imagen.setImageResource(R.drawable.sentadilla1);
                    }
                    else if(aux == 2)
                    {
                        imagen.setImageResource(R.drawable.sentadilla2);
                    }
                    else
                    {
                        imagen.setImageResource(R.drawable.sentadilla3);
                    }
                }
                else if(cont == 3)
                {
                    aux = random.nextInt(3 - 1 + 1) + 1;

                    if(aux == 1)
                    {
                        imagen.setImageResource(R.drawable.brazos1);
                    }
                    else if(aux == 2)
                    {
                        imagen.setImageResource(R.drawable.brazos2);
                    }
                    else
                    {
                        imagen.setImageResource(R.drawable.brazos3);
                    }
                }
                else if(cont == 4)
                {
                    if(user.getNivel() > 7)
                    {
                        aux = random.nextInt(3 - 1 + 1) + 1;

                        if(aux == 1)
                        {
                            imagen.setImageResource(R.drawable.brazos1);
                        }
                        else if(aux == 2)
                        {
                            imagen.setImageResource(R.drawable.brazos2);
                        }
                        else
                        {
                            imagen.setImageResource(R.drawable.brazos3);
                        }
                    }
                    else
                    {
                        imagen.setVisibility(View.INVISIBLE);
                        descripcionText.setVisibility(View.INVISIBLE);
                        tiempoText.setVisibility(View.INVISIBLE);
                        repeticionesText.setVisibility(View.INVISIBLE);
                        contadorText.setVisibility(View.VISIBLE);
                        descripcionEjercicioText.setVisibility(View.VISIBLE);
                        pasos = 9000;
                        tiempo = 15000;
                        String mensaje = "En este último ejercicio tendrás que correr durante 10 minutos y conseguir realizar " + pasos + " pasos.";
                        descripcionEjercicioText.setText(mensaje);
                        comenzar.setVisibility(View.VISIBLE);
                    }
                }
                else if(cont == 5)
                {
                    if(user.getNivel() > 17)
                    {
                        aux = random.nextInt(3 - 1 + 1) + 1;

                        if(aux == 1)
                        {
                            imagen.setImageResource(R.drawable.sentadilla2);
                        }
                        else if(aux == 2)
                        {
                            imagen.setImageResource(R.drawable.sentadilla3);
                        }
                        else
                        {
                            imagen.setImageResource(R.drawable.sentadilla1);
                        }
                    }
                    else
                    {
                        imagen.setVisibility(View.INVISIBLE);
                        descripcionText.setVisibility(View.INVISIBLE);
                        tiempoText.setVisibility(View.INVISIBLE);
                        repeticionesText.setVisibility(View.INVISIBLE);
                        contadorText.setVisibility(View.VISIBLE);
                        descripcionEjercicioText.setVisibility(View.VISIBLE);
                        pasos = 10000;
                        tiempo = 20000;
                        String mensaje = "En este último ejercicio tendrás que correr durante 10 minutos y conseguir realizar " + pasos + " pasos.";
                        descripcionEjercicioText.setText(mensaje);
                        comenzar.setVisibility(View.VISIBLE);
                    }
                }

                cont++;
            }
        });

        comenzar.setOnClickListener(new View.OnClickListener()
        {
             @Override
             public void onClick(View v) {
                 MiContador contador = new MiContador(tiempo,1000);
                 contador.start();
             }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent home = new Intent(DesafioActivity.this, HomeActivity.class);
                        startActivity(home);
                        return true;
                    case R.id.camino_al_exito:
                        Intent ce = new Intent(DesafioActivity.this, CaminoAlExito.class);
                        ce.putExtra("username",getIntent().getExtras().getString("username"));
                        startActivity(ce);
                        return true;
                    case R.id.historico:
                        Intent his = new Intent(DesafioActivity.this, HistoricoActivity.class);
                        his.putExtra("username",getIntent().getExtras().getString("username"));
                        startActivity(his);
                        return true;
                    case R.id.perfil:
                        Intent perfil = new Intent(DesafioActivity.this, PerfilActivity.class);
                        perfil.putExtra("username",getIntent().getExtras().getString("username"));
                        startActivity(perfil);
                        return true;
                }
                return false;
            }
        });
        navigation.setSelectedItemId(R.id.camino_al_exito);
    }

    private class MiContador extends CountDownTimer {

        public MiContador(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            int pasosConseguidos = 0;

            if(pasosConseguidos < pasos)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(DesafioActivity.this);
                builder.setMessage("no has superado el objetivo, ¡vuelve a intentarlo con más fuerza!")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id) {
                                Intent ce = new Intent(DesafioActivity.this, CaminoAlExito.class);
                                ce.putExtra("username",getIntent().getExtras().getString("username"));
                                startActivity(ce);
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
            }
            else
            {
                user.setNivel(user.getNivel()+1);
                db.actualizarUsuario(user);

                AlertDialog.Builder builder = new AlertDialog.Builder(DesafioActivity.this);
                builder.setMessage("Has superado el desafio, ¡ENHORABUENA!")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id) {
                                Intent ce = new Intent(DesafioActivity.this, CaminoAlExito.class);
                                ce.putExtra("username",getIntent().getExtras().getString("username"));
                                startActivity(ce);
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
            }

        }

        @Override
        public void onTick(long millisUntilFinished) {
            //texto a mostrar en cuenta regresiva en un textview
            //countdownText.setText((millisUntilFinished/1000+""));

        }
    }
}
