package com.example.felipe.senec;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ArrayList<Activity> activities;
    ListView listView;
    private static ActivityAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) this.findViewById(R.id.activity_list);

        ArrayList<Activity> activities = new ArrayList<Activity>();

        ActivitiesHttpGetRequest getRequest = new ActivitiesHttpGetRequest();
        try {
            activities = getRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adapter = new ActivityAdapter(activities, this.getApplicationContext(), this);
        listView.setAdapter(adapter);

    }

    protected void redirectToDetailActivity(Activity activity) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("activity_id",activity.getId());
        startActivity(intent);
    }
}
