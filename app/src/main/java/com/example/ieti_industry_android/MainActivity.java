package com.example.ieti_industry_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
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
                WsClient.location = server.getText().toString();
                String[] arrayUser = {user.getText().toString(), password.getText().toString()};
                try {
                    socket.connecta();
                    Thread.sleep(500);
                    socket.client.send(socket.objToBytes(arrayUser));
                } catch (URISyntaxException | WebsocketNotConnectedException e) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder popup = new AlertDialog.Builder(MainActivity.this);
                            popup.setTitle("ERROR: No s'ha trobat el servidor");
                            popup.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            popup.create();
                            popup.show();
                        }
                    });
                } catch (Exception e) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder popup = new AlertDialog.Builder(MainActivity.this);
                            popup.setTitle("ERROR INESPERAT");
                            popup.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            popup.create();
                            popup.show();
                        }
                    });
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
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
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
            });
        }
    }

    public void loadModel(String s) {
        ScreenControls.modelo = new Modelo(s);
    }

}