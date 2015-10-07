package com.example.edith.androidwebservicessql;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;


public class BarraActivity extends AppCompatActivity {
    // Declaramos elementos del formulario
    private ProgressBar pbBarra;
    private TextView tvPorcentaje;

    int progreso; // Integer, que indica la cantidad de segundos que he pasado desde el inicio del Login


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barra);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("Mensaje 22", "Pasa2");
        pbBarra = (ProgressBar) findViewById(R.id.pbBarra);
        tvPorcentaje = (TextView) findViewById(R.id.tvPorcentaje);

        Log.d("Mensaje 77", "Pasa7");
        pbBarra.setMax(10); // El maximo de la barra de progreso son 10, de los 10 segundos que durara la tarea en segundo plano
        pbBarra.setProgress(0); // Ponemos la barra a 0

        Log.d("Mensaje 11", "Pasa1");
        // Iniciamos la tarea en segundo plano
        new TareaSegundoPlano().execute(5);
     }

    // Ejecutara el codigo en segundo plano
    class TareaSegundoPlano extends AsyncTask<Integer, Integer, String>{
        @Override
        protected void onPreExecute() {
            Log.d("Mensaje 66", "Pasa6");
        }

        @Override
        protected String doInBackground(Integer... params) {
            Log.d("Mensaje 44", "Pasa4");
            for (progreso = 1; progreso <= 5; progreso++){
                try {
                    // Relantiza el proceso en un año (el tiempo es en milisegundos)
                    Thread.sleep(1000);
                    publishProgress(progreso);
                } catch (InterruptedException e) {
                    // Actualizamos el progreso, es decir llamamo al metodo OnProgressUpdate
                    e.printStackTrace();
                }
            }

            // Al llegar aqui, no devolvemos nada y acaba la tarea en segundo plano llamando al proceso onPostExecute
            return  null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("Mensaje 33", "Pasa3");
            // Actualizamos la barra de progreso con los segundos que vayan avanzando
            pbBarra.setProgress(progreso);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("Mensaje 55", "Pasa5");
            Intent intent = new Intent("android.intent.action.MenuActivity");
            startActivity(intent);
            finish();
        }
    }
}