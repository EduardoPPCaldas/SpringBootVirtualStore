package com.eduardo.springbootvirtualstore.services;

import java.util.Optional;

import com.eduardo.springbootvirtualstore.domain.Categoria;
import com.eduardo.springbootvirtualstore.repositories.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Integer id){
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElse(null);
    }

    public Categoria create(Categoria categoria){
        return repo.save(categoria);
    }
}