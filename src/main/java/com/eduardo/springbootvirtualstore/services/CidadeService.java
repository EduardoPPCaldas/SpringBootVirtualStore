package com.eduardo.springbootvirtualstore.services;

import java.util.List;

import com.eduardo.springbootvirtualstore.domain.Cidade;
import com.eduardo.springbootvirtualstore.repositories.CidadeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {
    
    @Autowired
    private CidadeRepository repo;

    public List<Cidade> findCidades(Integer estadoId){
        return repo.findCidades(estadoId);
    }
}