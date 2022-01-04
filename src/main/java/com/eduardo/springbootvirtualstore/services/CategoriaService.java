package com.eduardo.springbootvirtualstore.services;

import java.util.List;
import java.util.Optional;

import com.eduardo.springbootvirtualstore.domain.Categoria;
import com.eduardo.springbootvirtualstore.repositories.CategoriaRepository;
import com.eduardo.springbootvirtualstore.services.exceptions.DataIntegrityException;
import com.eduardo.springbootvirtualstore.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Integer id){
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto nao encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()
        ));
    }

    public List<Categoria> list(){
        List<Categoria> list = repo.findAll();
        return list;
    }

    public Categoria insert(Categoria categoria){
        categoria.setId(null);
        return repo.save(categoria);
    }

    public Categoria update(Categoria categoria){
        find(categoria.getId());
        return repo.save(categoria);
    }

    public void delete(Integer id){
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que contem produtos");
        }
        
    }
}