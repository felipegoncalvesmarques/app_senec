package com.example.felipe.senec;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tituloAtividade;
    TextView descricaoAtividade;
    TextView localAtividade;
    TextView dataHorarioAtividade;
    Switch inscritosHeadline, presentesHeadline;
    ParticipantAdapter inscritosAdapter, presentesAdapter;
    ListView inscritosList, presentesList;
    Activity activity;
    DetailActivity runningActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        int activityId = intent.getIntExtra("activity_id",1);
        inscritosList = (ListView) findViewById(R.id.subscribed_list);
        presentesList = (ListView) findViewById(R.id.presents_list);

        ActivityHttpGetRequest getRequest = new ActivityHttpGetRequest();
        activity = null;

        try {
            activity = getRequest.execute(activityId).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        runningActivity = this;
        inscritosAdapter = new ParticipantAdapter(activity.getInscritos(), this.getApplicationContext(), this, false);
        inscritosList.setAdapter(inscritosAdapter);
        presentesAdapter = new ParticipantAdapter(activity.getPresentes(), this.getApplicationContext(), this, true);
        presentesList.setAdapter(presentesAdapter);

        tituloAtividade = (TextView) this.findViewById(R.id.titulo_atividade);
        descricaoAtividade = (TextView) this.findViewById(R.id.descricao_atividade);
        localAtividade = (TextView) this.findViewById(R.id.local_atividade);
        dataHorarioAtividade = (TextView) this.findViewById(R.id.data_horario_atividade);
        inscritosHeadline = (Switch) this.findViewById(R.id.headline_inscritos);
        presentesHeadline = (Switch) this.findViewById(R.id.headline_presentes);

        tituloAtividade.setText(activity.getTitulo());
        descricaoAtividade.setText(activity.getDescricao());
        localAtividade.setText(activity.getLocal());
        dataHorarioAtividade.setText(activity.getDataHorarioFormated());
        inscritosHeadline.setOnClickListener(this);
        presentesHeadline.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.headline_inscritos) {
            if (inscritosList.getVisibility() == View.VISIBLE) {
                inscritosList.setVisibility(View.INVISIBLE);
                presentesHeadline.setVisibility(View.VISIBLE);
                if (presentesHeadline.isChecked()) {
                    presentesList.setVisibility(View.VISIBLE);
                }
            } else {
                inscritosList.setVisibility(View.VISIBLE);
                presentesHeadline.setVisibility(View.INVISIBLE);
                if (presentesList.getVisibility() == View.VISIBLE) {
                    presentesList.setVisibility(View.INVISIBLE);
                }
            }
        } else if (view.getId() == R.id.headline_presentes) {
            if (presentesList.getVisibility() == View.VISIBLE) {
                presentesList.setVisibility(View.INVISIBLE);
            } else {
                presentesList.setVisibility(View.VISIBLE);
            }
        }
    }

    public void darPresenca(final Participant participant) {
        new AlertDialog.Builder(this)
                .setTitle("Dar Presença")
                .setMessage("Deseja dar presença para "+ participant.getNome() +"?")
                .setIcon(android.R.drawable.ic_menu_edit)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        PresenceHttpPostRequest postRequest = new PresenceHttpPostRequest();
                        postRequest.execute(activity.getId(), participant.getId());
                        ActivityHttpGetRequest getRequest = new ActivityHttpGetRequest();

                        try {
                            activity = getRequest.execute(activity.getId()).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        inscritosAdapter = new ParticipantAdapter(activity.getInscritos(), runningActivity.getApplicationContext(), runningActivity, false);
                        inscritosList.setAdapter(inscritosAdapter);
                        presentesAdapter = new ParticipantAdapter(activity.getPresentes(), runningActivity.getApplicationContext(), runningActivity, true);
                        presentesList.setAdapter(presentesAdapter);

                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }
}
