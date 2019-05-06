package com.luan.cobranca.model;

public enum StatusTitulo
{
    PENDENTE("Pendente"), RECEBIDO("Recebido");

    private String descricao;

    StatusTitulo(String pDescricao) {
        this.descricao = pDescricao;

    }

    public String getDescricao()
    {
        return descricao;
    }
}
