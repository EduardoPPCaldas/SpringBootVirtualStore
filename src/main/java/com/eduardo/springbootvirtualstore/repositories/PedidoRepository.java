package com.eduardo.springbootvirtualstore.repositories;

import com.eduardo.springbootvirtualstore.domain.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    
}