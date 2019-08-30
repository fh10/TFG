package id.oscar.code.miband3.Activities;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import id.oscar.code.miband3.R;

public class DesafioActivity extends AppCompatActivity {

    ImageView imagen;
    TextView descripcionText;
    TextView tiempoText;
    TextView repeticionesText;
    Button siguiente;
    Button anterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio);
        imagen = (ImageView) findViewById(R.id.imageView5);
        descripcionText = (TextView) findViewById(R.id.descripcion);
        tiempoText = (TextView) findViewById(R.id.tiempo);
        repeticionesText = (TextView) findViewById(R.id.repeticiones);
        siguiente = (Button) findViewById(R.id.siguiente);
        anterior = (Button) findViewById(R.id.anterior);

        Random random = new Random();
        int num =  random.nextInt(3 - 1 + 1) + 1;

        if(num == 1)
        {
            imagen.setImageResource(R.drawable.abdominales1);
        }
        else if(num == 2)
        {
            imagen.setImageResource(R.drawable.abdominales2);
        }
        else
        {
            imagen.setImageResource(R.drawable.abdominales3);
        }


        int tiempo = 0; //30 s *1000 milisegundos 20 repeticiones cada una con un descanso de 60 segundos.
        MiContador contador = new MiContador(30000,1000);
        contador.start();
    }

    private class MiContador extends CountDownTimer {

        public MiContador(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            //Lo que quieras hacer al finalizar

        }

        @Override
        public void onTick(long millisUntilFinished) {
            //texto a mostrar en cuenta regresiva en un textview
            //countdownText.setText((millisUntilFinished/1000+""));

        }
    }
}
