package com.sutema.apps.alumnitracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.sutema.apps.alumnitracker.view.DisplayMessageActivity;
import com.sutema.apps.alumnitracker.view.LoginActivity;
import com.sutema.apps.alumnitracker.view.MapsActivity;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitBtn(View view){
        Log.i("clicked", "submitBtn: ");
        try{
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            TextInputLayout textInputLayout = findViewById(R.id.textInputLayout);
            String message = textInputLayout.getEditText().getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }catch(Exception e){
            Log.i("SubmitBtn", e.getMessage().toString());
        }

    }

    public void openMap(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
