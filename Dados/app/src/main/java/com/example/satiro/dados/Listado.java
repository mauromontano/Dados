package com.example.satiro.dados;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 *    Esta actividad se usa para representar la lista de puntajes
 * @author Mauro Montano
 * @version Febrero-2019
 */


public class Listado extends AppCompatActivity {

    /**
     *  Variables locales
      */

    private ListView list;
    private ArrayList<MiEntidad> listado;
    private Button bt_score;
    private ImageView boton_atras3;

    private AlertDialog dialog;
    private EditText nuevo;
    private String[] arregloIds;
    private int posItem = -1;



    /**
     *  Es llamado cuando la actividad esta iniciando
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        getSupportActionBar().hide();
        list = (ListView) findViewById(R.id.lista);
        boton_atras3= (ImageView) findViewById(R.id.boton_atras3);
        bt_score= (Button) findViewById(R.id.bt_score);

        dialog= new AlertDialog.Builder(this).create();
        nuevo= new EditText(this);

        dialog.setTitle(" Editar nombre ");
        dialog.setView(nuevo);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "ACTUALIZAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editar(posItem);
            }
        });

        CargarListado();
        bt_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });

        // boton atras
        boton_atras3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Listado.this, "POSICION: "+position, Toast.LENGTH_SHORT).show();
                // Toast.makeText(Listado.this, listado.get(position).getNombre(), Toast.LENGTH_SHORT).show();
                posItem= position;
                dialog.show();
            }
        });
    }

    /**
     *  Carga la lista con los nombres y puntajes en el listview
     */

    private void CargarListado() {
        DBAdapter db = new DBAdapter(this);
        listado= db.verTodos();
        Adaptador adapter = new Adaptador(this,R.layout.activity_item,listado);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     *  Elimina los datos de la base
     */

    private void eliminar(){
        DBAdapter db= new DBAdapter(this);
        db.open();
        db.eliminarTodos();
        db.close();
    }

    /**
     *  Editar un nombre especifico de una entrada de la base de datos
     */

    private void editar(int pos){

        DBAdapter db = new DBAdapter(this);
        db.open();
        Adaptador adapter = new Adaptador(this,R.layout.activity_item,listado);
        MiEntidad item= adapter.getItem(pos);
        String name= item.getNombre();
        String nuevoNombre= nuevo.getText().toString();
        db.updateEntrada(posItem,nuevoNombre,item.getPuntaje(),name);
        item.setNombre(nuevoNombre);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        db.close();
    }




}