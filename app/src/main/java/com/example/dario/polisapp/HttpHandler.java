package com.example.dario.polisapp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Dario on 26/04/2018.
 */

public class HttpHandler {
    private URL url;
    private String sessionCookie;
    public HttpHandler(String _urlString, String _sessionCookie) throws MalformedURLException {
        URL _url = new URL(_urlString);
        this.url = _url;
        this.sessionCookie = _sessionCookie;
    }

    public String Post(String _json)  {
        String result = null;
        try {
            result = new HttpRequest(this.url, this.sessionCookie, HttpRequest.POST, _json).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String EstablishSession() throws ExecutionException, InterruptedException {
        String result = null;
        String json = "{}";
        result = new HttpRequest(this.url, this.sessionCookie, HttpRequest.CONNECT, json).execute().get();
        return result;
    }

    public String Get(String _json)  {
        String result = null;
        try {
            result = new HttpRequest(this.url,this.sessionCookie, HttpRequest.GET, _json).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }
}
