package com.example.felipe.senec;

/**
 * Created by Felipe on 10/12/2016.
 */

public class Participant {
    private int id;
    private String nome;
    private String email;

    public Participant(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
