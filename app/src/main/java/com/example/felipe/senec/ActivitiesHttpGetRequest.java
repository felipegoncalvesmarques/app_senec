package com.example.felipe.senec;

import android.os.AsyncTask;
import android.util.JsonReader;
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

public class ActivitiesHttpGetRequest extends AsyncTask<Void, Void, ArrayList<Activity>> {

    private ArrayList<Activity> getActivitiesFromJson(String response) {
        ArrayList<Activity> activities = new ArrayList<Activity>();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        try {
            JSONArray activitiesJSON = new JSONArray(response);
            for (int i = 0; i < activitiesJSON.length(); i++) {
                JSONObject activity = activitiesJSON.getJSONObject(i);
                try {
                    activities.add(new Activity(activity.getInt("id"),
                                                activity.getString("titulo"),
                                                activity.getString("descricao"),
                                                activity.getString("local"),
                                                activity.getInt("numero_de_vagas"),
                                                ft.parse(activity.getString("data_horario"))
                                )
                            );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return activities;
    }

    @Override
    protected ArrayList<Activity> doInBackground(Void... voids) {
        ArrayList<Activity> activities = new ArrayList<Activity>();
        try {
            URL url = new URL("http://35.162.24.72:3000/activities.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int responseCode = conn.getResponseCode();
            Log.d("Activities Request", "The response code is: " + responseCode);
            InputStream is = conn.getInputStream();
            Reader reader = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(reader);
            String response;
            StringBuilder sb = new StringBuilder();
            while ((response = br.readLine()) != null) {
                sb.append(response);
            }
            response = sb.toString();
            activities = getActivitiesFromJson(response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return activities;
    }

}
