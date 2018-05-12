package com.sutema.apps.alumnitracker.view;

import android.content.Intent;
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

public class SignupActivity extends AppCompatActivity {
    private String urlApi = "http://192.168.1.100/be.alumni/index.php/api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void trySignUp(View view){
        final TextView fullname = findViewById(R.id.fullnameInputText);
        final TextView email = findViewById(R.id.emailInputText);
        final TextView password = findViewById(R.id.passwordInputText);
        final TextView passwordConfirm = findViewById(R.id.passwordConfirmInputText);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = this.urlApi+"/users/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    JsonNode json = mapper.readTree(response);
                    boolean isSuccess = json.get("success").asBoolean();

                    if(isSuccess){
                        Toast.makeText(getApplicationContext(), "Registrasi sukses, silahkan login...", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Snap! Ada yang salah, coba lagi!", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                }){
            protected Map<String, String> getParams(){
                Map<String, String> myData = new HashMap<>();
                myData.put("fullname", fullname.getText().toString());
                myData.put("email", email.getText().toString());
                myData.put("password", password.getText().toString());
                return myData;
            }
        };

        try{
            if(!password.getText().toString().equals(passwordConfirm.getText().toString())){
                Toast.makeText(getApplicationContext(), "Konfirmasi password dengan benar...", Toast.LENGTH_LONG).show();
            }else{
                requestQueue.add(stringRequest);
            }
        }catch (Exception e){

        }
    }
}
