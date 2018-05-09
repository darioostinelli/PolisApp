package com.example.dario.polisapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dario on 09/05/2018.
 */

public class Metric {
    private String name;
    private String unit;
    private String tag = null;
    private Log logs = null;

    public Metric(JSONObject _json) throws JSONException {
        this.tag = _json.getString("metric_tag");
        this.name = _json.getString("name");
        this.unit = _json.getString("unit");
    }
    public Metric(JSONObject _json, JSONArray _logs) throws JSONException{
        this.name = _json.getString("metric");
        this.unit = _json.getString("unit");
        this.logs = new Log(_logs);
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public String getTag() {
        return tag;
    }

    public Log getLogs() {
        return logs;
    }
}
