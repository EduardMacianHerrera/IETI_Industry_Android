package com.example.ieti_industry_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button loginButton = findViewById(R.id.loginButton);
        final EditText server = findViewById(R.id.Server);
        final EditText user = findViewById(R.id.User);
        final EditText password = findViewById(R.id.Password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SERVER", server.getText().toString());
                Log.i("USER", user.getText().toString());
                Log.i("PASS", password.getText().toString());
            }
        });
    }



}