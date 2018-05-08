package com.example.dario.polisapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    public static TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);

        ApiHandler apiHandler = new ApiHandler("admin", "password");
        if (!apiHandler.sessionEstablished()) {
            text.setText("Errore di connessione");
        } else if (!apiHandler.loggedIn()) {
            text.setText("Errore login");
        } else {
            text.setText("OK");
        }

        JSONArray userThingList = apiHandler.getUserThingList();
        text.setText(userThingList.toString());


    }
}
