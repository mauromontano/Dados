package com.example.satiro.dados;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 *    Esta actividad se usa para representar el resultado luego de terminar la partida
 * @author Mauro Montano
 * @version Febrero-2019
 */


public class Resultado extends AppCompatActivity {

    /**
     *  Variables locales
     */

    private Button bt_resufinal,bt_tabla,bt_guardar;
    private EditText et_nombre;
    static ArrayList<String> l = new ArrayList<String>();
    ImageView boton_atras2;

    /**
     *  Es llamado cuando la actividad esta iniciando
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        getSupportActionBar().hide();

        bt_resufinal= (Button) findViewById(R.id.bt_resufinal);
        et_nombre= (EditText) findViewById(R.id.et_nombre);
        boton_atras2= (ImageView) findViewById(R.id.boton_atras2);
        bt_tabla= (Button) findViewById(R.id.bt_tabla);
        bt_guardar= (Button) findViewById(R.id.bt_guardar);

        bt_resufinal.setText("Resultado: "+Partida.aux);

        bt_tabla.setEnabled(false);

        bt_tabla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Resultado.this,Listado.class));
            }
        });

        bt_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // guardo el resultado de partida en bdd
                String nom = et_nombre.getText().toString();
                if(nombrePermitido(nom)) {
                    guardar(nom, Partida.aux);
                    bt_tabla.setEnabled(true);
                }
            }
        });

        // boton atras
        boton_atras2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    /**
     *  Inserta una nueva entrada en la base de datos
     */
    private void guardar(String n, Integer i){
        DBAdapter db= new DBAdapter(this);
        db.open();
        db.insertar(n,i);
        db.close();
    }

    /**
     *  Se fija que el nombre ingresado sea valido y no se encuentre ya en la base de datos
     */

    private boolean nombrePermitido(String nombre){
        DBAdapter db = new DBAdapter(this);
        ArrayList<MiEntidad> lista = db.verTodos();
        Iterator<MiEntidad> it = lista.iterator();
        boolean resu= true;
        if(!lista.isEmpty()) {
            while (it.hasNext()) {
                MiEntidad m = it.next();
                if(m.getNombre().equals(nombre)){
                    resu = false;
                    Toast.makeText(Resultado.this, "Nombre ya ingresado en la tabla ", Toast.LENGTH_SHORT).show();
                  }
            }
        }
        return resu;
    }

}
