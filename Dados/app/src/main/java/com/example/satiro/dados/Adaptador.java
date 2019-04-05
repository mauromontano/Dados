package com.example.satiro.dados;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Adaptador extends ArrayAdapter<MiEntidad> {

    /**
     * Variables locales
     */

    private AppCompatActivity activity;
    private ArrayList<MiEntidad> lista ;
    private MiEntidad entidad;
    int resource;

    public Adaptador(AppCompatActivity context, int resource, ArrayList<MiEntidad> entidades) {
        super(context,resource,entidades);
        this.activity= context;
        this.lista= entidades;
        this.resource= resource;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public MiEntidad getItem(int position) {
        entidad= lista.get(position);
        return entidad;
    }

    public long getItemPuntaje(int position){
        entidad= lista.get(position);
        return entidad.getPuntaje();
    }

    /**
     * Uso del patron de diseño ViewHolder que optimiza la ListView ahorrando memoria
     */
    @Override
    public View getView(int position, View view, ViewGroup parent){
        ViewHolder myHolder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(view == null){
            view = inflater.inflate(R.layout.activity_item, parent, false);
            myHolder = new ViewHolder(view);
            view.setTag(myHolder);
        }
        else{
            myHolder = (ViewHolder) view.getTag();
        }
        String str2= String.valueOf(getItemPuntaje(position));
        myHolder.nombre.setText(getItem(position).getNombre());
        myHolder.puntaje.setText(str2);


        myHolder.nombre.setTextSize(20);
        myHolder.puntaje.setTextSize(20);
        myHolder.nombre.setTextColor(Color.RED);
        myHolder.puntaje.setTextColor(Color.RED);


        return view;

    }

    /**
        Metodo auxiliar de viewholder
     */
    private static class ViewHolder {
        private TextView nombre;
        private TextView puntaje;

        ViewHolder(View view) {
            nombre = (TextView) view.findViewById(R.id.tv_nombre);
            puntaje = (TextView) view.findViewById(R.id.tv_puntaje);
        }
    }

}

