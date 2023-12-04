package com.example.spotper.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "playlist")
public class PlaylistsModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlist_id;
    private String nome;
    private Date data_criacao;
    private Time tempo_total_execucao;

    public Long getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(Long playlist_id) {
        this.playlist_id = playlist_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }

    public Time getTempo_total_execucao() {
        return tempo_total_execucao;
    }

    public void setTempo_total_execucao(Time tempo_total_execucao) {
        this.tempo_total_execucao = tempo_total_execucao;
    }
}
