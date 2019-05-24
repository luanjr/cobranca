package com.luan.cobranca.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Titulo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codigo;

    @NotEmpty(message = "Descrição é obrigatória!")
    @Size(max = 60, message = "A Descrição não pode conter mais que 60 caracteres!")
    private String descricao;

    @NotNull(message = "Data de vencimento é obrigatória!")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;

    @NotNull(message = "Valor é obrigatório!")
    @DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0,01")
    @DecimalMax(value = "999999999.99", message = "O valor não pode ser maior que 999.999.999,99")
    @NumberFormat(pattern = "#,##0.00")
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private StatusTitulo statusTitulo;

    public BigDecimal getValor()
    {
        return valor;
    }

    public void setValor(BigDecimal valor)
    {
        this.valor = valor;
    }

    public long getCodigo()
    {
        return codigo;
    }

    public void setCodigo(long codigo)
    {
        this.codigo = codigo;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public Date getDataVencimento()
    {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVenciemnto)
    {
        this.dataVencimento = dataVenciemnto;
    }

    public StatusTitulo getStatusTitulo()
    {
        return statusTitulo;
    }

    public void setStatusTitulo(StatusTitulo statusTitulo)
    {
        this.statusTitulo = statusTitulo;
    }

    public boolean isPendente()
    {
        return StatusTitulo.PENDENTE.equals(statusTitulo);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (codigo ^ (codigo >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Titulo other = (Titulo) obj;
        if (codigo != other.codigo)
            return false;
        return true;
    }

}
