package com.example.dario.polisapp;

import android.os.AsyncTask;
import android.util.Pair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by Dario on 26/04/2018.
 */

public class HttpRequest extends AsyncTask<Objects, Void, String> {

    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String CONNECT = "LOGIN";
    private String json = "{}";
    private URL url;
    private String method;
    private String sessionCookie;
    public HttpRequest(URL _url, String _sessionCookie,  String _method, String _json){
        super();
        this.json = _json;
        this.url = _url;
        this.method = _method;
        this.sessionCookie = _sessionCookie;
    }
    @Override
    protected String doInBackground(Objects... params) {
        URL searchUrl = this.url;
        String result = null;
        List<Pair<String, String>> requestParams = new ArrayList<>();
        requestParams.add(new Pair<>("data", this.json));
        try {
            if(this.method == HttpRequest.POST) {
                result = this.POST(searchUrl, this.sessionCookie, requestParams);
            }
            else if(this.method == HttpRequest.GET){
                result = this.GET(searchUrl, this.sessionCookie, this.json);
            }
            else{
                result = this.establishConnection(searchUrl, requestParams);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String POST(URL url, String cookie, List<Pair<String, String>> params) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(15000);
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Cookie", cookie);
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);

        OutputStream os = urlConnection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

        writer.write(getQuery(params));
        writer.flush();
        writer.close();
        os.close();
        urlConnection.connect();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
    public static String GET(URL _url, String cookie, String json) throws IOException {
        String stringUrl = _url.toString();
        stringUrl += "?data=" + json;
        URL url = new URL(stringUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Cookie", cookie);
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
           urlConnection.disconnect();
        }
    }
    public static String establishConnection(URL url,  List<Pair<String, String>> params) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(15000);
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);

        OutputStream os = urlConnection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

        writer.write(getQuery(params));
        writer.flush();
        writer.close();
        os.close();
        urlConnection.connect();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return urlConnection.getHeaderField("Set-Cookie");
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    private static String getQuery(List<Pair<String, String>> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Pair<String, String> pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.first, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.second, "UTF-8"));
        }

        return result.toString();
    }
}
