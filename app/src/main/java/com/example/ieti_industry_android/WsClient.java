package com.example.ieti_industry_android;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class WsClient {
    int port = 8888;
    static String location;
    WebSocketClient client;
    static Activity currentActivity;



    public void connecta() throws URISyntaxException {
        String uri = "ws://" + location + ":" + port;
            System.out.println("connecting");
            client = new WebSocketClient(new URI(uri), (Draft) new Draft_6455()) {
                @Override
                public void onMessage(ByteBuffer message) {
                    System.out.println("Array recibido: "+message);
                    Object obj = bytesToObject(message);
                    String[] cambio = (String[]) obj;
                    for (String s: cambio) {
                        System.out.println(s);
                    }
                    ((ScreenControls) currentActivity).updateInterfaz(cambio);
                }

                public void onMessage(String message) {
                    System.out.println("Mensaje: "+message);
                    if ((message.equalsIgnoreCase("true") || message.equalsIgnoreCase("false")) && currentActivity instanceof MainActivity) {
                        ((MainActivity) currentActivity).login(Boolean.parseBoolean(message));
                    } else if (message.contains("block")){
                        ((MainActivity) currentActivity).loadModel(message);
                    }
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Connected to: " + getURI());
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    if (currentActivity instanceof ScreenControls) {
                        ((ScreenControls) currentActivity).connectionLost();
                    }
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };
            client.connect();
    }


    public static Object bytesToObject(ByteBuffer arr) {
        Object result = null;
        try {
            // Transforma el ByteButter en byte[]
            byte[] bytesArray = new byte[arr.remaining()];
            arr.get(bytesArray, 0, bytesArray.length);

            // Transforma l'array de bytes en objecte
            ByteArrayInputStream in = new ByteArrayInputStream(bytesArray);
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] objToBytes (Object obj) {
        byte[] result = null;
        try {
            // Transforma l'objecte a bytes[]
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            result = bos.toByteArray();
        } catch (IOException e) { e.printStackTrace(); }
        return result;
    }

    public void change(String[] arrayDades) {
        client.send(objToBytes(arrayDades));
    }


}
