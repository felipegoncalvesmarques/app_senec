package com.example.felipe.senec;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tituloAtividade;
    TextView descricaoAtividade;
    TextView localAtividade;
    TextView dataHorarioAtividade;
    Switch inscritosHeadline;
    ParticipantAdapter inscritosAdapter, presentes;
    ListView inscritosList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        int activityId = intent.getIntExtra("activity_id",1);
        inscritosList = (ListView) findViewById(R.id.subscribed_list);

        ActivityHttpGetRequest getRequest = new ActivityHttpGetRequest();
        Activity activity = null;

        try {
            activity = getRequest.execute(activityId).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        inscritosAdapter = new ParticipantAdapter(activity.getInscritos(), this.getApplicationContext());
        inscritosList.setAdapter(inscritosAdapter);
        tituloAtividade = (TextView) this.findViewById(R.id.titulo_atividade);
        descricaoAtividade = (TextView) this.findViewById(R.id.descricao_atividade);
        localAtividade = (TextView) this.findViewById(R.id.local_atividade);
        dataHorarioAtividade = (TextView) this.findViewById(R.id.data_horario_atividade);
        inscritosHeadline = (Switch) this.findViewById(R.id.headline_inscritos);
        inscritosHeadline.setOnClickListener(this);
        tituloAtividade.setText(activity.getTitulo());
        descricaoAtividade.setText(activity.getDescricao());
        localAtividade.setText(activity.getLocal());
        dataHorarioAtividade.setText(activity.getDataHorarioFormated());
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.headline_inscritos) {
            if (inscritosList.getVisibility() == View.VISIBLE) {
                inscritosList.setVisibility(View.INVISIBLE);
            } else {
                inscritosList.setVisibility(View.VISIBLE);
            }
        }
    }
}
