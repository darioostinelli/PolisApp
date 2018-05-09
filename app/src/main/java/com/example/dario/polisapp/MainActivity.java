package com.example.dario.polisapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    public static TextView text;
    private ApiHandler apiHandler;
    private List<Thing> thingList = new ArrayList<Thing>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        apiHandler = new ApiHandler(username,password);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);

        if (!apiHandler.sessionEstablished()) {
            text.setText("Errore di connessione");
        } else if (!apiHandler.loggedIn()) {
            text.setText("Errore login");
        } else {
            text.setText("OK");
        }

        JSONArray userThingList = apiHandler.getUserThingList();
        initThingList(userThingList);
        text.setText(userThingList.toString());
        Thing t = thingList.get(0);
        List<Metric> l = null;
        try {
           l =  t.getLogs(apiHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String a = "a";
        text.setText(l.toString());
    }

    private void initThingList(JSONArray array){
        for(int i = 0; i < array.length(); i++){
            try {
                Thing t = new Thing(array.getJSONObject(i));
                this.thingList.add(t);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
