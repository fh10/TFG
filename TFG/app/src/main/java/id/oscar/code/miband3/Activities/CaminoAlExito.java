package id.oscar.code.miband3.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import id.oscar.code.miband3.Helpers.DatabaseHelper;
import id.oscar.code.miband3.Helpers.Usuario;
import id.oscar.code.miband3.R;

public class CaminoAlExito extends AppCompatActivity {

    ListView camino;
    DatabaseHelper db;
    int nivelUsuario;
    ItemAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camino_al_exito);
        camino = (ListView) findViewById(R.id.listaCamino);

        db = new DatabaseHelper(this);
        Usuario user = db.getUser(getIntent().getExtras().getString("username"));
        nivelUsuario = user.getNivel();
        ArrayList<String> desafios = new ArrayList<>();

        for(int i = 0; i < 33;i++)
        {
            desafios.add(Integer.toString(i));
        }

        ItemAdapter adapter = new ItemAdapter(this,desafios);

        camino.setAdapter(adapter);

        camino.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent des = new Intent(CaminoAlExito.this, DesafioActivity.class);
                des.putExtra("nombreDispositivo",getIntent().getStringExtra("nombreDispositivo"));
                des.putExtra("direccionDispositivo",getIntent().getStringExtra("direccionDispositivo"));
                des.putExtra("username",getIntent().getStringExtra("username"));
                startActivity(des);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent home = new Intent(CaminoAlExito.this, HomeActivity.class);
                        home.putExtra("username",getIntent().getExtras().getString("username"));
                        home.putExtra("nombreDispositivo",getIntent().getStringExtra("nombreDispositivo"));
                        home.putExtra("direccionDispositivo",getIntent().getStringExtra("direccionDispositivo"));
                        startActivity(home);
                        return true;
                    case R.id.camino_al_exito:
                        /*Intent ce = new Intent(CaminoAlExito.this, CaminoAlExito.class);
                        startActivity(ce);*/
                        return true;
                    case R.id.historico:
                        Intent his = new Intent(CaminoAlExito.this, HistoricoActivity.class);
                        his.putExtra("username",getIntent().getExtras().getString("username"));
                        his.putExtra("nombreDispositivo",getIntent().getStringExtra("nombreDispositivo"));
                        his.putExtra("direccionDispositivo",getIntent().getStringExtra("direccionDispositivo"));
                        startActivity(his);
                        return true;
                case R.id.perfil:
                    Intent perfil = new Intent(CaminoAlExito.this, PerfilActivity.class);
                    perfil.putExtra("username",getIntent().getExtras().getString("username"));
                    perfil.putExtra("nombreDispositivo",getIntent().getStringExtra("nombreDispositivo"));
                    perfil.putExtra("direccionDispositivo",getIntent().getStringExtra("direccionDispositivo"));
                    startActivity(perfil);
                    return true;
                }
                return false;
            }
        });

        navigation.setSelectedItemId(R.id.camino_al_exito);
    }

    private class ItemAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<String> desafios;

        public ItemAdapter(Context context, ArrayList<String> desafios) {
            super();
            this.context = context;
            this.desafios= desafios;
        }

        @Override
        public int getCount() {
            return this.desafios.size();
        }

        @Override
        public Object getItem(int position) {
            return this.desafios.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            if (convertView == null) {
                // Create a new view into the list.
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.lista_desafios, parent, false);
            }

            // Set data into the view.
            TextView nombre= (TextView) rowView.findViewById(R.id.nombre);
            TextView estado= (TextView) rowView.findViewById(R.id.estado);


            int posicion = Integer.parseInt(this.desafios.get(position));
            int aux = posicion +1;

            String nombreText = "Desafio " + aux;

            nombre.setText(nombreText);

            if(posicion <= 7)
            {
                nombre.setBackgroundColor(Color.parseColor("#CD7F32"));
                estado.setBackgroundColor(Color.parseColor("#CD7F32"));
            }
            else if(posicion > 7 && posicion <= 17)
            {
                nombre.setBackgroundColor(Color.parseColor("#B2B6BA"));
                estado.setBackgroundColor(Color.parseColor("#B2B6BA"));
            }
            else if(posicion > 17)
            {
                nombre.setBackgroundColor(Color.parseColor("#cc9900"));
                estado.setBackgroundColor(Color.parseColor("#cc9900"));
            }

            if(nivelUsuario <= 7)
            {
                if(posicion <= 7)
                {
                    if(posicion < nivelUsuario)
                    {
                        estado.setText(R.string.completado_exclamacion);
                    }
                    else
                    {
                        estado.setText(R.string.a_por_el);
                    }
                }
                else
                {
                    estado.setText(R.string.a_por_el);
                }
            }
            else if(nivelUsuario > 7 && nivelUsuario <=17)
            {
                if(posicion > 7 && posicion <17)
                {
                    if(posicion <= nivelUsuario)
                    {
                        estado.setText(R.string.completado_exclamacion);
                    }
                    else
                    {
                        estado.setText(R.string.a_por_el);
                    }
                }
                else
                {
                    estado.setText(R.string.a_por_el);
                }
            }
            else if(nivelUsuario > 17)
            {
                if(posicion > 17)
                {
                    if(posicion <= nivelUsuario)
                    {
                        estado.setText(R.string.completado_exclamacion);
                    }
                    else
                    {
                        estado.setText(R.string.a_por_el);
                    }
                }
                else
                {
                    estado.setText(R.string.a_por_el);
                }
            }

            return rowView;
        }

    }
}