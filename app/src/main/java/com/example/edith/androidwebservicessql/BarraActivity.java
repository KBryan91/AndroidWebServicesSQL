package com.example.edith.androidwebservicessql;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;


public class BarraActivity extends AppCompatActivity {
    // Declaramos elementos del formulario
    ProgressBar pbBarra;
    TextView tvPorcentaje;

    int progreso; // Integer, que indica la cantidad de segundos que he pasado desde el inicio del Login
    int porcentaje = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pbBarra = (ProgressBar) findViewById(R.id.pbBarra);
        tvPorcentaje = (TextView) findViewById(R.id.tvPorcentaje);

        // Iniciamos la tarea en segundo plano
        new TareaSegundoPlano().execute();
    }

    // Ejecutara el codigo en segundo plano
    public class TareaSegundoPlano extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            pbBarra.setProgress(0); // Ponemos la barra a 0
            pbBarra.setMax(10); // El maximo de la barra de progreso son 10, de los 10 segundos que durara la tarea en segundo plano
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int progreso = 1; progreso <= 10; progreso++){

                try {
                    // Relantiza el proceso en un año (el tiempo es en milisegundos)
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Actualizamos el progreso, es decir llamamo al metodo OnProgressUpdate
                    publishProgress();
                }
            }

            // Al llegar aqui, no devolvemos nada y acaba la tarea en segundo plano llamando al proceso onPostExecute
            return  null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // Actualizamos la barra de progreso con los segundos que vayan avanzando
            pbBarra.setProgress(progreso);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(BarraActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }
}