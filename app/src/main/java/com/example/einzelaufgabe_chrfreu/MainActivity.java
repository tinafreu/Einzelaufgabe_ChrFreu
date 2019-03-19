package com.example.einzelaufgabe_chrfreu;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private EditText edit; //Eingabefeld der Matrikelnummer
    private TextView text; //Ausgabefeld Server

    private Socket socket;
    private DataOutputStream toServer; //um Nachricht an Server zu senden
    private BufferedReader fromServer; //um Nachricht vom Server zu bekommen
    private String send; //wird für Textfeld mit Matrikelnummer benötigt

    private static final int SERVERPORT = 53212;
    private static final String SERVER_IP = "se2-isys.aau.at";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = findViewById(R.id.matrikelnr); //Verbindung zum Eingabefeld, wo die Matrikelnummer eingegeben wird
        text = findViewById(R.id.antwserver); //Verbindung zum Textfeld, wo die Antwort vom Server angezeigt wird
    }

    class ServerRequest extends AsyncTask<Void, Void, String>{


        @Override
        protected String doInBackground(Void... voids) {

            //"EditText" (Matrikelnummer) verbinden und in String umwandeln
            send = edit.getText().toString();
            String answer = "";

            try {
                //Create client socket and connect to server
                socket = new Socket(SERVER_IP, SERVERPORT);

                //Create output stream attached to socket
                toServer = new DataOutputStream(socket.getOutputStream());

                //Create input stream attached to socket
                fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //Send line to server
                toServer.writeBytes(send + '\n');

                //Read line from server
                answer = fromServer.readLine();

                //Close connection
                socket.close();

            } catch (IOException ioe){
                ioe.printStackTrace();
            }

            return null;
        }
    }

}
