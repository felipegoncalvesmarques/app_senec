package com.example.felipe.senec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Felipe on 10/12/2016.
 */

public class ParticipantAdapter extends ArrayAdapter<Participant> {

    private ArrayList<Participant> participants;
    private Context context;
    private int lastPosition = -1;
    private final int view;

    private static class ViewHolder {
        TextView nome;
        TextView email;
    }

    public ParticipantAdapter(ArrayList<Participant> participants, Context context) {
        super(context, R.layout.row_subscriber, participants);
        this.participants = participants;
        this.context = context;
        this.view = R.layout.row_subscriber;
    }

    @Override
    public Participant getItem(int position) {
        return participants.get(position);
    }

    @Override
    public int getCount() {
        return participants.size();
    }

    @Override
    public long getItemId(int position) {
        return participants.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Participant participant = getItem(position);
        ViewHolder viewHolder;

        final View result;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(this.view, parent, false);
        }
        viewHolder = new ViewHolder();
        viewHolder.nome = (TextView) convertView.findViewById(R.id.nome_subscriber);
        viewHolder.email = (TextView) convertView.findViewById(R.id.email_subscriber);
        lastPosition = position;
        viewHolder.nome.setText(participant.getNome());
        viewHolder.email.setText(participant.getEmail());
        convertView.setTag(position);

        return convertView;
    }
}
