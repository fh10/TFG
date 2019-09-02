package id.oscar.code.miband3.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;

import id.oscar.code.miband3.Activities.ui.login.LoginActivity;
import id.oscar.code.miband3.Activities.ui.login.LoginViewModel;
import id.oscar.code.miband3.Helpers.DatabaseHelper;
import id.oscar.code.miband3.Helpers.Usuario;
import id.oscar.code.miband3.R;

public class RegistryActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    EditText confirmPassword;
    EditText nombreEditText;
    EditText apellidosEditText;
    EditText fechaEditText;
    EditText sexoEditText;
    EditText alturaEditext;
    EditText pesoEditText;
    Button registryButton;
    ProgressBar loadingProgressBar;
    TextView login;
    DatePickerDialog picker;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
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
        usernameEditText = (EditText) findViewById(R.id.edittext_username);
        registryButton = (Button) findViewById(R.id.button_register);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loading);
        login = (TextView) findViewById(R.id.textview_login);

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
                Toast.makeText(RegistryActivity.this,"Debe ser mayor de 5 caracteres", Toast.LENGTH_LONG).show();
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

                picker = new DatePickerDialog(RegistryActivity.this, R.style.Theme_AppCompat_Light_DialogWhenLarge,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                fechaEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                }
        });

        registryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String cnf_password = confirmPassword.getText().toString().trim();
                String nombre = nombreEditText.getText().toString();
                String apellidos = apellidosEditText.getText().toString();
                String fecha = fechaEditText.getText().toString();
                Float peso = -1f, altura = -1f;
                try {
                    peso = NumberFormat.getInstance().parse(pesoEditText.getText().toString()).floatValue();
                    peso = peso/10;
                    altura = NumberFormat.getInstance().parse(alturaEditext.getText().toString()).floatValue();
                    altura = altura/100;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int  sexo = -1;

                if(user.isEmpty() || password.isEmpty() || password.length() < 5 || cnf_password.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || fecha.isEmpty() || peso < 0 || altura < 0)
                {
                    Toast.makeText(RegistryActivity.this,"Error en los datos introducidos", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(!sexoEditText.getText().toString().toLowerCase().equals("hombre") && !sexoEditText.getText().toString().toLowerCase().equals("mujer"))
                    {
                        Toast.makeText(RegistryActivity.this,"Escribe hombre o mujer", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(RegistryActivity.this,"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }


                        if(!db.checkUser(user,password))
                        {
                            Long registrado = db.addUser(user,password,nombre,apellidos,fecha, sexo, peso,altura);

                            if(registrado != -1)
                            {
                                Toast.makeText(RegistryActivity.this,"Registrado!", Toast.LENGTH_SHORT).show();
                                Intent login = new Intent(RegistryActivity.this,LoginActivity.class);
                                startActivity(login);
                            }
                            else
                            {
                                Toast.makeText(RegistryActivity.this,"ERROR EN EL REGISTRO!", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(RegistryActivity.this,"Nombre de usuario no disponible", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registryIntent = new Intent(RegistryActivity.this, LoginActivity.class);
                startActivity(registryIntent);
            }
        });
    }
}
