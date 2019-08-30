package id.oscar.code.miband3.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;

import id.oscar.code.miband3.Activities.ui.login.LoginActivity;
import id.oscar.code.miband3.Helpers.DatabaseHelper;
import id.oscar.code.miband3.Helpers.Usuario;
import id.oscar.code.miband3.R;

public class ActualizarUsuario extends AppCompatActivity {
    EditText usernameEditText;
    EditText passwordEditText;
    EditText confirmPassword;
    EditText nombreEditText;
    EditText apellidosEditText;
    EditText fechaEditText;
    EditText sexoEditText;
    EditText alturaEditext;
    EditText pesoEditText;
    EditText zancadaEditText;
    Button actualizarButton;
    Button cancelarButton;
    ProgressBar loadingProgressBar;
    TextView login;
    DatePickerDialog picker;
    Usuario userActual;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_usuario);

        db = new DatabaseHelper(this);
        usernameEditText = (EditText) findViewById(R.id.edittext_username);
        passwordEditText = (EditText) findViewById(R.id.edittext_password);
        confirmPassword = (EditText) findViewById(R.id.edittext_cnf_password);
        nombreEditText = (EditText) findViewById(R.id.edittext_nombre);
        apellidosEditText = (EditText) findViewById(R.id.edittext_apellidos);
        fechaEditText = (EditText) findViewById(R.id.edittext_fecha_nac);
        sexoEditText = (EditText) findViewById(R.id.edittext_sexo);
        pesoEditText = (EditText) findViewById(R.id.edittext_peso);
        alturaEditext = (EditText) findViewById(R.id.edittext_altura);
        zancadaEditText = (EditText) findViewById(R.id.edittext_zancada);
        usernameEditText = (EditText) findViewById(R.id.edittext_username);
        actualizarButton = (Button) findViewById(R.id.button_actualizar);
        cancelarButton = (Button) findViewById(R.id.button_cancelar);
        userActual = db.getUser(getIntent().getStringExtra("username"));

        nombreEditText.setText(userActual.getNombre());
        apellidosEditText.setText(userActual.getApellidos());
        usernameEditText.setText(userActual.getUsername());
        fechaEditText.setText(userActual.getFecha_nac());

        String aux = "";

        if(userActual.getSexo() == 0)
        {
            aux = "Hombre";
        }
        else
        {
            aux = "Mujer";
        }

        sexoEditText.setText(aux);
        aux = Float.toString(userActual.getPeso()) + " Kg";
        pesoEditText.setText(aux);
        aux = Float.toString(userActual.getAltura()) + " m";
        alturaEditext.setText(aux);
        aux = Float.toString(userActual.getZancada());
        zancadaEditText.setText(aux);

        loadingProgressBar = (ProgressBar) findViewById(R.id.loading);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
               /* loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());*/
                Toast.makeText(ActualizarUsuario.this,"Debe ser mayor de 5 caracteres", Toast.LENGTH_LONG).show();
            }
        };

        passwordEditText.addTextChangedListener(afterTextChangedListener);

        fechaEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                picker = new DatePickerDialog(ActualizarUsuario.this, R.style.Theme_AppCompat_Light_DialogWhenLarge,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                fechaEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        actualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String cnf_password = confirmPassword.getText().toString().trim();
                String nombre = nombreEditText.getText().toString();
                String apellidos = apellidosEditText.getText().toString();
                String fecha = fechaEditText.getText().toString();
                Float peso = -1f, altura = -1f, zancada = -1f;
                try {
                    peso = NumberFormat.getInstance().parse(pesoEditText.getText().toString()).floatValue();
                    peso = peso/10;
                    altura = NumberFormat.getInstance().parse(alturaEditext.getText().toString()).floatValue();
                    altura = altura/100;
                    zancada = NumberFormat.getInstance().parse(alturaEditext.getText().toString()).floatValue();
                    zancada = zancada / 100;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int  sexo = -1;

                if(user.isEmpty() || password.isEmpty() || password.length() < 5 || cnf_password.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || fecha.isEmpty() || peso < 0 || altura < 0 || zancada < 0)
                {
                    Toast.makeText(ActualizarUsuario.this,"Error en los datos introducidos", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(!sexoEditText.getText().toString().toLowerCase().equals("hombre") && !sexoEditText.getText().toString().toLowerCase().equals("mujer"))
                    {
                        Toast.makeText(ActualizarUsuario.this,"Escribe hombre o mujer", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(sexoEditText.getText().toString().toLowerCase().equals("hombre"))
                        {
                            sexo = 0;
                        }
                        else if(sexoEditText.getText().toString().toLowerCase().equals("mujer"))
                        {
                            sexo = 1;
                        }

                        if(!password.equals(cnf_password))
                        {
                            Toast.makeText(ActualizarUsuario.this,"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            userActual.setUsername(user);
                            userActual.setPassword(password);
                            userActual.setNombre(nombre);
                            userActual.setApellidos(apellidos);
                            userActual.setFecha_nac(fecha);
                            userActual.setSexo(sexo);
                            userActual.setPeso(peso);
                            userActual.setAltura(altura);
                            userActual.setZancada(zancada);
                            Long actualizado = db.actualizarUsuario(userActual);

                            if(actualizado != -1)
                            {
                                Toast.makeText(ActualizarUsuario.this,"Actualizado!", Toast.LENGTH_SHORT).show();
                                Intent perfil = new Intent(ActualizarUsuario.this, PerfilActivity.class);
                                perfil.putExtra("username",userActual.getUsername());
                                startActivity(perfil);
                            }
                            else
                            {
                                Toast.makeText(ActualizarUsuario.this,"ERROR EN LA ACTUALIZACIÓN!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }
            }
        });

        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent perfil = new Intent(ActualizarUsuario.this, PerfilActivity.class);
                perfil.putExtra("username",userActual.getUsername());
                startActivity(perfil);
            }
        });
    }
}
