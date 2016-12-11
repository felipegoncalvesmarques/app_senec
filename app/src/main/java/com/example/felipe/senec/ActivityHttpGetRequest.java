package com.example.felipe.senec;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Felipe on 10/12/2016.
 */

public class ActivityHttpGetRequest extends AsyncTask<Integer, Void, Activity> {

    private Activity getActivityFromJson(String response) {
        Activity activity = null;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        try {
            JSONObject activityJson = new JSONObject(response);
            try {
                activity = new Activity(activityJson.getInt("id"),
                            activityJson.getString("titulo"),
                            activityJson.getString("descricao"),
                            activityJson.getString("local"),
                            activityJson.getInt("numero_de_vagas"),
                            ft.parse(activityJson.getString("data_horario")));
                JSONArray inscritosJson = activityJson.getJSONArray("inscritos");
                JSONArray presentesJson = activityJson.getJSONArray("presentes");
                for (int i = 0; i < inscritosJson.length(); i++) {
                    JSONObject inscrito = inscritosJson.getJSONObject(i);
                    activity.addInscrito(new Participant(inscrito.getInt("id"),
                                                        inscrito.getString("nome"),
                                                        inscrito.getString("email")));
                }
                for (int i = 0; i < presentesJson.length(); i++) {
                    JSONObject presente = presentesJson.getJSONObject(i);
                    activity.addPresente(new Participant(presente.getInt("id"),
                                                        presente.getString("nome"),
                                                        presente.getString("email")));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return activity;
    }

    @Override
    protected Activity doInBackground(Integer... ids) {
        Activity activity = null;
        try {
            URL url = new URL("http://35.162.24.72:3000/activities/" + ids[0] + ".json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int responseCode = conn.getResponseCode();
            InputStream is = conn.getInputStream();
            Reader reader = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(reader);
            String response;
            StringBuilder sb = new StringBuilder();
            while ((response = br.readLine()) != null) {
                sb.append(response);
            }
            response = sb.toString();
            activity = getActivityFromJson(response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return activity;
    }
}
