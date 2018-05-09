package com.example.dario.polisapp;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Dario on 27/04/2018.
 */

public class ApiHandler {
    private SessionHanlder session = new SessionHanlder();
    private boolean logged = false;
    private String username = null;
    private String password = null;

    /**
     * Stabilisce la connessione, inizializza SessionHandler e il parametro boolean logged
     * @param _username
     * @param _password
     */
    public ApiHandler(String _username, String _password)  {
        this.username = _username;
        this.password = _password;
        String response = session.login(this.username, this.password);        
        try {
            JSONObject obj = new JSONObject(response);
            String status = obj.getString("status");
            if(status.compareTo("success") == 0){
                this.logged = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Indica se la sessione è stata creata dal SessionHandler
     * @return
     */
    public boolean sessionEstablished(){
        return this.session.isSessionSet();
    }

    /**
     * Indica se le credeziali inserite per la login sono corrette
     * @return
     */
    public boolean loggedIn(){
        return this.logged;
    }

    /**
     * Effettua il logout lato server con chiamata al servizio api, distrugge il SessionHandler, rinizializza i parametri
     */
    public void logout() {
        try {
            this.session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.session = new SessionHanlder();
        this.logged = false;
    }

    public JSONArray getUserThingList() {
        String result = this.session.apiCall("getUserThingList", HttpRequest.GET, "true");
        JSONArray array = null;
        try {
            array = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public JSONObject getMetricsList(String thingTag) {
        String result = this.session.apiCall("getMetrics", HttpRequest.POST, "{\"thingTag\":\"" + thingTag + "\"}");
        JSONObject object = null;
        try {
            object = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
