package com.example.felipe.senec;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Felipe on 08/12/2016.
 */

public class ActivityAdapter extends ArrayAdapter<Activity> implements View.OnClickListener {
    private ArrayList<Activity> activities;
    Context context;
    AppCompatActivity activityRunning;
    private int lastPosition = -1;
    private final int view;

    @Override
    public void onClick(View view) {
        int position = (Integer) view.getTag();
        Activity activity = getItem(position);
        ((MainActivity) activityRunning).redirectToDetailActivity(activity);
    }


    private static class ViewHolder {
        TextView titulo;
        TextView local;
        TextView dataHorario;
    }

    public ActivityAdapter(ArrayList<Activity> activities, Context context, AppCompatActivity activityRunning) {
        //noinspection ResourceType
        super(context, R.layout.row_activity, activities);
        this.activityRunning = activityRunning;
        this.activities = activities;
        this.view = R.layout.row_activity;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = getItem(position);
        ViewHolder viewHolder;

        final View result;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(this.view, parent, false);
        }
        viewHolder = new ViewHolder();
        viewHolder.titulo = (TextView) convertView.findViewById(R.id.titulo);
        viewHolder.local = (TextView) convertView.findViewById(R.id.local);
        viewHolder.dataHorario = (TextView) convertView.findViewById(R.id.data_horario);


        lastPosition = position;
        viewHolder.titulo.setText(activity.getTitulo());
        viewHolder.local.setText(activity.getLocal());
        viewHolder.dataHorario.setText(activity.getDataHorarioFormated());
        convertView.setTag(position);
        convertView.setOnClickListener(this);

        return convertView;
    }

    @Override
    public Activity getItem(int postion) {
        return activities.get(postion);
    }

    @Override
    public int getCount() {
        return activities.size();
    }

    @Override
    public long getItemId(int position) {
        return activities.get(position).getId();
    }
}
