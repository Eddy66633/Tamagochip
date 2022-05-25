package com.example.tamagotchi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText txtID, txtEstado, txtComida;
    Button btnEstadistica, btnComer, btnBaño;
    int contadorComidas;
    String estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contadorComidas = 1;

        txtID = findViewById(R.id.txtID);
        txtEstado = findViewById(R.id.txtEstado);
        txtComida = findViewById(R.id.txtComidas);
        btnEstadistica = findViewById(R.id.btnEstadistica);
        btnComer = findViewById(R.id.btnComer);
        btnBaño = findViewById(R.id.btnBaño);

        btnEstadistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //leerWS();
                enviarWS(txtComida.getText().toString(), txtEstado.getText().toString(), txtID.getText().toString());
            }
        });

        btnComer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contadorComidas++;
                txtComida.setText("Número de comidas: "+Integer.toString(contadorComidas));
                if(contadorComidas >= 0 && contadorComidas <= 3) {
                    estado = "Tamahochi débil";
                    txtEstado.setText(estado);
                }else if(contadorComidas >= 4 && contadorComidas <= 6) {
                    estado = "Tamagochi hambriento";
                    txtEstado.setText(estado);
                }else if (contadorComidas >= 7 && contadorComidas <= 9){
                    estado = "Tamahochi estable";
                    txtEstado.setText(estado);
                }else if (contadorComidas >= 10) {
                    estado = "Tamahochi Exelente";
                    txtEstado.setText(estado);
                }else {
                    estado = "Tamahochi Muerto";
                    txtEstado.setText(estado);
                }

            }
        });


        btnBaño.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    contadorComidas--;
                    txtComida.setText("Número de comidas: "+Integer.toString(contadorComidas));
                    if(contadorComidas >= 0 && contadorComidas <= 3) {
                        estado = "Tamahochi débil";
                        txtEstado.setText(estado);
                    }else if(contadorComidas >= 4 && contadorComidas <= 6) {
                        estado = "Tamagochi hambriento";
                        txtEstado.setText(estado);
                    }else if (contadorComidas >= 7 && contadorComidas <= 9){
                        estado = "Tamahochi estable";
                        txtEstado.setText(estado);
                    }else if (contadorComidas >= 10) {
                        estado = "Tamahochi Exelente";
                        txtEstado.setText(estado);
                    }else {
                        estado = "Tamahochi Muerto";
                        txtEstado.setText(estado);
                    }
            }
        });
        txtComida.setText(Integer.toString(contadorComidas));
        txtEstado.setText(estado);


    }

    private void leerWS() {

        String url = "https://jsonplaceholder.typicode.com/posts/11";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtID.setText(jsonObject.getString("userId"));
                    String comida = jsonObject.getString("title");
                    txtComida.setText(comida);
                    txtEstado.setText(jsonObject.getString("body"));
                    Toast.makeText(MainActivity.this, "ID = " + jsonObject.getString("id"), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());

            }
        });
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void enviarWS(final String title, final String body, final String id) {

        String url = "https://jsonplaceholder.typicode.com/posts";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    /*txtID.setText(jsonObject.getString("userId"));
                    String comida = jsonObject.getString("title");
                    txtComida.setText(comida);
                    txtEstado.setText(jsonObject.getString("body"));
                    */
                    Toast.makeText(MainActivity.this, "Resultado = " + response, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());

            }
        })
        {
            protected Map<String, String> getParams() {
               Map<String,String> params = new HashMap<>();
               params.put("title", title);
               params.put("body", body);
               params.put("userId", id);
               return params;
            }
        }
                ;
        Volley.newRequestQueue(this).add(postRequest);
    }


}