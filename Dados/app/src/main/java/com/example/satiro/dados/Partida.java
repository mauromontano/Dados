package com.example.satiro.dados;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 *    Esta actividad se usa para representar la partida del jugador vs cpu
 * @author Mauro Montano
 * @version Febrero-2019
 */

public class Partida extends AppCompatActivity {

    /**
     *   Variables locales
     */
    public static final Random RANDOM = new Random();
    Button bt_lanzar;
    ImageView dado_cpu, dado_jug;
    TextView t_cpu, t_jug;
    int cont_cpu, cont_jug;
    int tiros;
    static int aux;
    ImageView boton_atras;

    /**
     * Es llamado cuando la actividad esta iniciando
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        getSupportActionBar().hide();

        // inicializo los componentes

        bt_lanzar= (Button) findViewById(R.id.bt_lanzar);
        dado_cpu= (ImageView) findViewById(R.id.dado_cpu);
        dado_jug= (ImageView) findViewById(R.id.dado_jug);
        t_cpu= (TextView) findViewById(R.id.t_cpu);
        t_jug= (TextView) findViewById(R.id.t_jug);
        boton_atras= (ImageView) findViewById(R.id.boton_atras);

        t_cpu.setTextSize(TypedValue.COMPLEX_UNIT_SP,t_cpu.getTextSize()-1);
        t_jug.setTextSize(TypedValue.COMPLEX_UNIT_SP,t_jug.getTextSize()-1);

        bt_lanzar.setBackgroundColor(Color.RED);

        // boton atras

        boton_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // hago que el botton reaccione al apretarlo llamando a valorRandom()

        bt_lanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Vibrator vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(0100);

                int v1= valorRandom();
                int v2= valorRandom();

                int resu1= getResources().getIdentifier("dice"+ v1,"drawable", "com.example.satiro.dados");
                int resu2= getResources().getIdentifier("dice"+ v2,"drawable", "com.example.satiro.dados");

                dado_cpu.setImageResource(resu1);
                dado_jug.setImageResource(resu2);

                tiros ++;

                if (v1 > v2){
                    cont_cpu++;
                    t_cpu.setText("CPU: "+cont_cpu);
                }
                else if (v2 > v1) {
                    cont_jug++;
                    t_jug.setText("Jugador: " + cont_jug);
                    }
                    else if (v1 == v2)
                            // hago toast para avisar q se empato
                            Toast.makeText(Partida.this, "Empate!", Toast.LENGTH_SHORT).show();

                // Termino el juego
                if((cont_jug == 5) || (cont_cpu == 5) ){
                    if(cont_cpu == 5){ // Perdio el jugador, avisar y reiniciar numeros
                        Toast.makeText(Partida.this, "Has Perdido!", Toast.LENGTH_SHORT).show();
                        cont_cpu= 0;
                        cont_jug= 0;
                        t_cpu.setText("CPU: "+cont_cpu);
                        t_jug.setText("Jugador: " + cont_jug);
                        tiros= 0;
                    }
                    if(cont_jug == 5){
                       ///String str = String.valueOf(tiros);
                         aux= tiros;

                        startActivity(new Intent(Partida.this, Resultado.class));

                        cont_cpu= 0;
                        cont_jug= 0;
                        t_cpu.setText("CPU: "+cont_cpu);
                        t_jug.setText("Jugador: " + cont_jug);
                        tiros= 0;
                    }

                }

            }
        });

        // Agrego boton para ir atras
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }
    // Boton atras
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();   // cierro activity partida
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *  Se utiliza para que los dados sean aleatorios del 1 al 6
     */
    public static int valorRandom(){
        return RANDOM.nextInt(6)+1;
    }




}
