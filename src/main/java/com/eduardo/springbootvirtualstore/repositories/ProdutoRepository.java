package com.eduardo.springbootvirtualstore.repositories;

import com.eduardo.springbootvirtualstore.domain.Produto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    
}