package id.oscar.code.miband3.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

import id.oscar.code.miband3.Activities.ui.login.LoginActivity;
import id.oscar.code.miband3.Helpers.DatabaseHelper;
import id.oscar.code.miband3.R;

public class RegistryActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    EditText confirmPassword;
    EditText nombreEditText;
    EditText apellidosEditText;
    EditText fechaEditText;
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
        registryButton = (Button) findViewById(R.id.button_register);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loading);
        login = (TextView) findViewById(R.id.textview_login);

        fechaEditText.setInputType(InputType.TYPE_NULL);

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
                picker = new DatePickerDialog(RegistryActivity.this,
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
                String apelldos = apellidosEditText.getText().toString();
                String fecha = fechaEditText.getText().toString();

                if(user.isEmpty() || password.isEmpty() || cnf_password.isEmpty() || nombre.isEmpty() || apelldos.isEmpty() || fecha.isEmpty())
                {
                    Toast.makeText(RegistryActivity.this,"Error en los datos introducidos", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(cnf_password))
                {
                    Toast.makeText(RegistryActivity.this,"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Long registrado = db.addUser(user,password,nombre,apelldos,fecha);

                    if(registrado != -1)
                    {
                        Toast.makeText(RegistryActivity.this,"Registrado!", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(RegistryActivity.this,LoginActivity.class);
                        SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("username" , user);
                        editor.commit();
                        startActivity(login);
                    }
                    else
                    {
                        Toast.makeText(RegistryActivity.this,"ERROR EN EL REGISTRO!", Toast.LENGTH_SHORT).show();
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
