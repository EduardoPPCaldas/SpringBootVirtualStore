package com.eduardo.springbootvirtualstore.repositories;

import com.eduardo.springbootvirtualstore.domain.Estado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    
}