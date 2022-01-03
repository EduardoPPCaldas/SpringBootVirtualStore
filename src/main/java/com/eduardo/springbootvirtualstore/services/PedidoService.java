package com.eduardo.springbootvirtualstore.services;

import java.util.Optional;

import com.eduardo.springbootvirtualstore.domain.Pedido;
import com.eduardo.springbootvirtualstore.repositories.PedidoRepository;
import com.eduardo.springbootvirtualstore.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repo;

    public Pedido buscar(Integer id){
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + "Tipo: " + Pedido.class.getName()));
    }
}