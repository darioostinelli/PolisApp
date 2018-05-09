package com.example.dario.polisapp;

import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dario on 09/05/2018.
 */

public class Log {
    public List<Pair<String, String>> logs = new ArrayList<>();
    public Log(JSONArray json){
        try {
            for (int i = 0; i < json.length(); i++) {
                JSONObject obj = json.getJSONObject(i);
                String timestamp = obj.getString("time_stamp");
                String value = obj.getString("value");
                logs.add(new Pair<String, String>(timestamp, value));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
