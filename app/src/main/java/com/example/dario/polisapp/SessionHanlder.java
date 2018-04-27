package com.example.dario.polisapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Dario on 26/04/2018.
 */

public class SessionHanlder {

    private String sessionCookie = null;
    private static final String serverPath = "http://polis.inno-school.org";
    private static final String apiPath = serverPath + "/polis/php/api/";
    private static final String loginPath = apiPath + "login.php";

    private void establishConnession() throws MalformedURLException, ExecutionException, InterruptedException {
        HttpHandler http = new HttpHandler(loginPath, this.sessionCookie);
        String rawResult = http.EstablishSession();
        if(rawResult != null) {
            int splitPos = rawResult.indexOf(";");
            this.sessionCookie = rawResult.substring(0, splitPos);
        }
    }

    /**
     * Inizializza la sessione sul server, autentica l'utente e stabilisce i parametri per il mantenimento della connessione tra applicazione e server
     *
     * @param username
     * @param password
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws MalformedURLException
     */
    public String login(String username, String password)  {
        String json = "{\"user\":\"" + username + "\",\"pass\":\"" + password + "\"}";
        try {
            this.establishConnession();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(this.sessionCookie == null){
            return "{\"status\":\"error\",\"error\":\"Unable to establish connection\"}";
        }
        HttpHandler http = null;
        try {
            http = new HttpHandler(loginPath, this.sessionCookie);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String result = http.Post(json);
        return result;
    }

    /***
     * Invia richieste HTTP verso il servizio specificato
     *
     * @param _service String Nome del file con servizio api senza estensione (es. "login")
     * @param _json    String JSON con i dati necessari
     * @param _method  String "POST" o "GET"
     * @return String Risultato della richiesta http
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws MalformedURLException
     */
    public String apiCall(String _service, String _method, String _json)  {
        String servicePath = apiPath + _service + ".php";
        HttpHandler http = null;
        try {
            http = new HttpHandler(servicePath, this.sessionCookie);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String result;
        if (this.sessionCookie == null) { //connessione non settata
            return "{\"status\":\"error\",\"error\":\"Please log in\"}";
        }
        if (_method == HttpRequest.POST) {
            result = http.Post(_json);
        } else if (_method == HttpRequest.GET) {
            result = http.Get(_json);
        } else { //metodo non riconosciuto
            return "{\"status\":\"error\",\"error\":\"Api call method error\"}";
        }
        return result;
    }
    public boolean isSessionSet(){
        if(this.sessionCookie == null){
            return false;
        }
        return true;
    }

    public void disconnect() throws InterruptedException, ExecutionException, MalformedURLException {
        this.apiCall("logout", HttpRequest.POST, "{}");
        this.sessionCookie = null;
    }
}
