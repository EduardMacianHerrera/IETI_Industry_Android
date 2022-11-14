package com.example.ieti_industry_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    int port = 8888;
    String location = "10.0.2.2";
    String uri = "ws://" + location + ":" + port;
    WsClient socket = new WsClient();


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
                /*
                Log.i("SERVER", server.getText().toString());
                Log.i("USER", user.getText().toString());
                Log.i("PASS", password.getText().toString());
                 */
                socket.connecta();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HashMap<String, String> users = socket.getUsers();
                for (String key : users.keySet()) {
                    if (user.getText().toString().equals(key) && password.getText().toString().equals(users.get(user.getText().toString()))) {
                        /*
                        Intent intent = new Intent(MainActivity.this, ScreenControls.class);
                        ScreenControls.socket = socket;
                        startActivity(intent);

                         */
                        Toast.makeText(MainActivity.this, "LOGIN CORRECTE", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }


}