package com.sutema.apps.alumnitracker.view;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sutema.apps.alumnitracker.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private String urlApi = "http://192.168.1.100/be.alumni/index.php/api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void openSignUp(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void tryLogin(View view){
        final TextView email = findViewById(R.id.emailInputText);
        final TextView password = findViewById(R.id.passwordInputText);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = this.urlApi + "/users/verify";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ObjectMapper mapper = new ObjectMapper();

                try{
                    JsonNode resp = mapper.readTree(response);

                    boolean isSuccess = resp.get("success").asBoolean();

                    if (isSuccess) {
                        Toast.makeText(getApplicationContext(), "Autentikasi berhasil", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Autentikasi gagal", Toast.LENGTH_LONG).show();
                    }


                }catch(IOException e){
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Didnt work");
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> myData = new HashMap<String, String>();
                myData.put("email", email.getText().toString());
                myData.put("password", password.getText().toString());
                return myData;
            }
        };

        requestQueue.add(stringRequest);

    }
}
