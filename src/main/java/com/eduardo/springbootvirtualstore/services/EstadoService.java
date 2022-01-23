package com.eduardo.springbootvirtualstore.services;

import java.util.List;

import com.eduardo.springbootvirtualstore.domain.Estado;
import com.eduardo.springbootvirtualstore.repositories.EstadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {
    @Autowired
    private EstadoRepository repo;

    public List<Estado> findAllOrderByName(){
        return repo.findAllByOrderByNome();
    }
}