package com.example.satiro.dados;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


/**
 *    Esta actividad se inicia al arrancar la aplicacion
 * @author Mauro Montano
 * @version Febrero-2019
 */


public class MainActivity extends AppCompatActivity {

    /**
     *   Variables locales
     */

    Button bt_empezar;
    ImageView bt_salir;

    /**
     * Es llamado cuando la actividad esta iniciando
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        bt_empezar= (Button) findViewById(R.id.bt_empezar);
        bt_salir= (ImageView) findViewById(R.id.bt_salir);

        bt_empezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Partida.class));
            }
        });


        bt_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
