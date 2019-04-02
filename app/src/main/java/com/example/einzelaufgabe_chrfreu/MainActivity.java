package com.example.einzelaufgabe_chrfreu;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private EditText edit; //Eingabefeld der Matrikelnummer
    private TextView text; //Ausgabefeld Server

    private Socket socket;
    private DataOutputStream toServer;
    private BufferedReader fromServer;
    private String send;

    private static final int SERVERPORT = 53212;
    private static final String SERVER_IP = "se2-isys.aau.at";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //verweist auf das UserInterface

        edit = findViewById(R.id.matrikelnr); //Verbindung zum Eingabefeld, wo die Matrikelnummer eingegeben wird
        text = findViewById(R.id.antwserver); //Verbindung zum Textfeld, wo die Antwort vom Server angezeigt wird
    }


    public void OnClick (View v){

        new ServerRequest().execute();
    }



    class ServerRequest extends AsyncTask<Void, Void, String>{


        @Override
        protected String doInBackground(Void... voids) {


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

            //runOnUiThread -->helps to return the updated UI to Main Class
            final String finalAnswer = answer;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText(finalAnswer);
                }
            });

            return null;
        }
    }


    //AUFGABE 2

    //01454434 & 7 = 2

    public void Primzahlen_streichen(View v){


        send = edit.getText().toString();
        String result = "";


        //Ziffern der Größe nach sortieren
        char[] array = send.toCharArray();
        Arrays.sort(array);






        //Zahlen ausgeben
        for (int i = 0; i<array.length; i++)

            //Wenn die Eingabe die Zahlen 0,1,4,6,8 oder 9 enthält, gib diese Zahlen aus
            //Dadurch werden die Primzahlen 2,3,5,7 ignoriert und nicht ausgegeben
            if (array[i] == '0' || array[i] == '1' || array[i] == '4' || array[i] == '6' || array[i] == '8' || array[i] == '9') {
                result = result + array[i];
            }



        //Ergebnis ausgeben
        text.setText(result);
    }


    //


}
