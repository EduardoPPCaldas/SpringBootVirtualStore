package com.eduardo.springbootvirtualstore.repositories;

import com.eduardo.springbootvirtualstore.domain.Cidade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    
}