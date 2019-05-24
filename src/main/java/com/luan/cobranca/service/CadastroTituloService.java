package com.luan.cobranca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.luan.cobranca.model.StatusTitulo;
import com.luan.cobranca.model.Titulo;
import com.luan.cobranca.repository.Titulos;

@Service
public class CadastroTituloService
{
    @Autowired
    private Titulos titulos;

    public void salvar(Titulo titulo)
    {
        try {
            titulos.save(titulo);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Formato de data inválido");
        }
    }

    public void excluir(Long codigo)
    {
        titulos.deleteById(codigo);

    }

    public void receber(Long codigo)
    {
        Titulo titulo = titulos.getOne(codigo);
        titulo.setStatusTitulo(StatusTitulo.RECEBIDO);
        titulos.save(titulo);
    }
}
