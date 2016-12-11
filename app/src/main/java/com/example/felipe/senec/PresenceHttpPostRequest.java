package com.example.felipe.senec;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Felipe on 11/12/2016.
 */

public class PresenceHttpPostRequest extends AsyncTask<Integer, Void, Void> {
    private String getPostDataString(int participantId) {
        StringBuilder result = new StringBuilder();
        try {
            result.append(URLEncoder.encode("presence[participant_id]", "UTF-8"));
            result.append(URLEncoder.encode(String.valueOf(participantId), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
    @Override
    protected Void doInBackground(Integer... ids) {
        String data = "commit=Marcar+presen%C3%A7a&presence%5Bparticipant_id%5D="+ ids[1] +"&utf8=%E2%9C%93";
        try {
            URL url = new URL("http://35.162.24.72:3000/activities/" + ids[0] + "/presences");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            os.close();
            conn.connect();
            int responseCode = conn.getResponseCode();
            Log.d("Response code", String.valueOf(responseCode));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
