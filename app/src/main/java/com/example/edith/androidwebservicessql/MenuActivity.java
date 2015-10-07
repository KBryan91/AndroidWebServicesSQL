package com.example.edith.androidwebservicessql;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class MenuActivity extends AppCompatActivity {
    VariablesPublicas variablesPublicas = new VariablesPublicas();
    TextView tvUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        tvUsuario = (TextView) findViewById(R.id.tvUsuario);
        tvUsuario.setText(variablesPublicas.usuario);
    }
}
