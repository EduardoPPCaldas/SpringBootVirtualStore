package com.eduardo.springbootvirtualstore.dto;

import java.io.Serializable;

import com.eduardo.springbootvirtualstore.domain.Cidade;

public class CidadeDTO implements Serializable {
    private static final long serialVersionUID = 1l;

    private Integer id;
    private String nome;
    
    public CidadeDTO(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public CidadeDTO(Cidade cidade){
        id = cidade.getId();
        nome = cidade.getNome();
    }

    public CidadeDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}