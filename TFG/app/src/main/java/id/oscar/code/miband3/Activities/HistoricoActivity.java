package id.oscar.code.miband3.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import id.oscar.code.miband3.R;

public class HistoricoActivity extends AppCompatActivity {

    TableLayout tabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        tabla = (TableLayout) findViewById(R.id.tabla);

        TableRow row = new TableRow(this);

        row.setLayoutParams(new TableLayout.LayoutParams( TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));

        TextView fecha = new TextView(this);
        fecha.setText("14/07/2019");
        row.addView(fecha);

        TextView pasos = new TextView(this);
        fecha.setText("14563");
        row.addView(pasos);

        TextView distancia = new TextView(this);
        fecha.setText("12589");
        row.addView(distancia);

        TextView calorias = new TextView(this);
        fecha.setText("2563");
        row.addView(calorias);

        tabla.addView(row);
    }
}
