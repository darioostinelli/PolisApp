package com.example.dario.polisapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dario on 09/05/2018.
 */

public class Metric {
    private String name;
    private String unit;
    private String tag;

    public Metric(JSONObject _json) throws JSONException {
        this.tag = _json.getString("metric_tag");
        this.name = _json.getString("name");
        this.unit = _json.getString("unit");
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
}
