package com.example.ieti_industry_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    static WsClient socket = new WsClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button loginButton = findViewById(R.id.loginButton);
        final EditText server = findViewById(R.id.Server);
        final EditText user = findViewById(R.id.User);
        final EditText password = findViewById(R.id.Password);

        WsClient.currentActivity = this;

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
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String[] arrayUser = {user.getText().toString(), password.getText().toString()};
                socket.client.send(socket.objToBytes(arrayUser));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public void login(boolean isCorrect) {
        if (isCorrect) {
            socket.client.send("getModel");
            ScreenControls.socket = MainActivity.socket;
            Intent intent = new Intent(MainActivity.this, ScreenControls.class);
            startActivity(intent);
        } else {
            AlertDialog.Builder popup = new AlertDialog.Builder(MainActivity.this);
            popup.setTitle("Login incorrecte, revisi els credencials");
            popup.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            popup.create();
            popup.show();
        }
    }

    public void loadModel(String s) {
        ScreenControls.modelo = new Modelo(s);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(ScreenControls.modelo);
    }

}