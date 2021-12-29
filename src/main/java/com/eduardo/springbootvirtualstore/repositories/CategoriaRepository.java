package com.eduardo.springbootvirtualstore.repositories;

import com.eduardo.springbootvirtualstore.domain.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    
}