package com.example.dario.polisapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dario on 09/05/2018.
 */

public class Thing {
    private String tag;
    private String name;
    private String family;
    private List<Metric> metricList = new ArrayList<Metric>();

    public Thing(JSONObject _json) throws JSONException {
        this.tag = _json.getString("tag");
        this.name = _json.getString("name");
        this.family = _json.getString("family_tag");
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public String getTag() {
        return tag;
    }

    public List<Metric> getMetricList(ApiHandler api) throws JSONException {
        JSONObject json;
        JSONArray list = null;
        if(!this.metricList.isEmpty()){
            return this.metricList;
        }
        json = api.getMetricsList(this.tag);
        String status = json.get("status").toString();
        if(status.equals("success")){
            list = (JSONArray) json.get("metrics");
            initMetricList(list);
            return this.metricList;
        }
        else{
            return null;
        }

    }

    private void initMetricList(JSONArray array){
        for(int i = 0; i < array.length(); i++){
            try {
                Metric m = new Metric(array.getJSONObject(i));
                this.metricList.add(m);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Metric> getLogs(ApiHandler api) throws JSONException {
        List<Metric> list = new ArrayList<Metric>();
        JSONArray array = api.getMetricLogs(this.tag);
        if(array == null){
            return null;
        }
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            JSONArray logs = obj.getJSONArray("list");
            Metric m = new Metric(obj, logs);
            list.add(m);
        }
        return list;
    }
}
