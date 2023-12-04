package com.example.spotper.dtos;

public class AlbumDto {
    private Long album_id;
    private String descricao;
    private String gravadora;
    private String preco_compra;
    private String data_compra;
    private String data_gravacao;
    private int meio_fisico_id;

    public Long getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(Long album_id) {
        this.album_id = album_id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getGravadora() {
        return gravadora;
    }

    public void setGravadora(String gravadora) {
        this.gravadora = gravadora;
    }

    public String getPreco_compra() {
        return preco_compra;
    }

    public void setPreco_compra(String preco_compra) {
        this.preco_compra = preco_compra;
    }

    public String getData_compra() {
        return data_compra;
    }

    public void setData_compra(String data_compra) {
        this.data_compra = data_compra;
    }

    public String getData_gravacao() {
        return data_gravacao;
    }

    public void setData_gravacao(String data_gravacao) {
        this.data_gravacao = data_gravacao;
    }

    public int getMeio_fisico_id() {
        return meio_fisico_id;
    }

    public void setMeio_fisico_id(int meio_fisic) {
        this.meio_fisico_id = meio_fisic;
    }
}
