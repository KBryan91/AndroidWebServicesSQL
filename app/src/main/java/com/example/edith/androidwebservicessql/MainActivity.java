package com.example.edith.androidwebservicessql;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Declaramos elementos del formulario
    Button btnIngresar;
    EditText etUsuario, etContrasena;

    public final int dialog_alert = 0;
    public String mensaje = "";

    // Instancia a la clase VariablesPublicas
    VariablesPublicas variablesPublicas = new VariablesPublicas();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etContrasena = (EditText) findViewById(R.id.etContrasena);

        btnIngresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String NAMESPACE = "http://wsPruebaSQL.org/";
        String URL = "http://" + variablesPublicas.ip_adress + "/Service1.asmx";
        String METHOD_NAME = "LoginUsuario";
        String SOAP_ACTION = "http://wsPruebaSQL.org/LoginUsuario";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("usuario", etUsuario.getText().toString());
        request.addProperty("contrasena", etContrasena.getText().toString());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.implicitTypes = true;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        Log.d("Mensaje 11", "HttpTransportSE");
        HttpTransportSE transportSE = new HttpTransportSE(URL);

        try {
            transportSE.debug = true;
            transportSE.call(SOAP_ACTION, envelope);

            String ss = transportSE.requestDump;

            Log.d("Mensaje 22", "request: " + transportSE.requestDump);
            Log.d("Mensaje 33", "response: " + transportSE.responseDump);

            SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
            mensaje = resultado_xml.toString().toUpperCase();


            if (mensaje.equals("GRACIAS POR INICIAR SESION")){
                Log.d("Mensaje 44", "Inicio sesion");
                variablesPublicas.usuario = etUsuario.getText().toString();
                etUsuario.setText("");
                etContrasena.setText("");

                // Pasar a otro activiy
                Intent intent = new Intent(MainActivity.this, BarraActivity.class);
                startActivity(intent);
                finish();

            }
        } catch (IOException e) {
            Log.d("Mensaje 55", "IOException " + e.toString());
        } catch (XmlPullParserException e) {
            Log.d("Mensaje 66", "XmlPullParserException " + e.toString());
        } catch (Exception e) {
            Log.d("Mensaje 77", "Exception " + e.toString());
        }
    }
}
