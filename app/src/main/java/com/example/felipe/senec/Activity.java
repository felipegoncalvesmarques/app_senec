package com.example.felipe.senec;

import android.icu.text.MessagePattern;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Felipe on 08/12/2016.
 */

public class Activity {
    private int id;
    private String titulo, descricao, local;
    private int numeroDeVagas;
    private Date dataHorario;
    private ArrayList<Participant> inscritos, presentes;

    public Activity(int id, String titulo, String descricao, String local, int numeroDeVagas, Date dataHorario) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.local = local;
        this.dataHorario = dataHorario;
        this.inscritos = new ArrayList<Participant>();
        this.presentes = new ArrayList<Participant>();
    }

    public void addInscrito(Participant inscrito) {
        this.inscritos.add(inscrito);
    }

    public void addPresente(Participant presente) {
        this.presentes.add(presente);
    }

    public ArrayList<Participant> getInscritos() {
        return inscritos;
    }

    public ArrayList<Participant> getPresentes() {
        return  presentes;
    }
    public int getId() {
        return this.id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public String getLocal() {
        return this.local;
    }

    public int getNumeroDeVagas() {
        return this.numeroDeVagas;
    }

    public Date getDataHorario() {
        return this.dataHorario;
    }

    public String getDataHorarioFormated() {
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        return ft.format(this.dataHorario);
    }
}
