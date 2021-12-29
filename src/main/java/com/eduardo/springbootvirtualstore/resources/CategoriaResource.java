package com.eduardo.springbootvirtualstore.resources;

import java.util.ArrayList;
import java.util.List;

import com.eduardo.springbootvirtualstore.domain.Categoria;
import com.eduardo.springbootvirtualstore.services.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id){
        Categoria obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Categoria categoria){
        Categoria obj = service.create(categoria);
        return ResponseEntity.ok().body(obj);
    }
}