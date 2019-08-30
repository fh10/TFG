package id.oscar.code.miband3.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

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

        nivel.setGravity(Gravity.CENTER);
        aux = "Desafio " + (user.getNivel()+1);
        nivel.setText(aux);

        modificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actualizar = new Intent(PerfilActivity.this, ActualizarUsuario.class);
                actualizar.putExtra("username",user.getUsername());
                startActivity(actualizar);
            }
        });
    }
}
