package com.example.spotper.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Time;

@Entity
@Table(name = "faixa")
public class FaixaModel {
    @Id
    private Long faixa_id;
    private int album_id;
    private int numero_faixa;
    private String descricao;
    private int tipo_composicao_id;
    private Time tempo_execucao;

    public Long getFaixa_id() {
        return faixa_id;
    }

    public void setFaixa_id(Long faixa_id) {
        this.faixa_id = faixa_id;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public int getNumero_faixa() {
        return numero_faixa;
    }

    public void setNumero_faixa(int numero_faixa) {
        this.numero_faixa = numero_faixa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTipo_composicao_id() {
        return tipo_composicao_id;
    }

    public void setTipo_composicao_id(int tipo_composicao_id) {
        this.tipo_composicao_id = tipo_composicao_id;
    }

    public Time getTempo_execucao() {
        return tempo_execucao;
    }

    public void setTempo_execucao(Time tempo_execucao) {
        this.tempo_execucao = tempo_execucao;
    }
}
