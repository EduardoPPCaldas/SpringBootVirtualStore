package com.eduardo.springbootvirtualstore.repositories;

import java.util.List;

import com.eduardo.springbootvirtualstore.domain.Categoria;
import com.eduardo.springbootvirtualstore.domain.Produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Transactional(readOnly = true)
    Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome,List<Categoria> categorias, Pageable pageRequest);
}