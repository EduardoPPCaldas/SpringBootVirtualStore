package com.eduardo.springbootvirtualstore.resources;

import java.util.List;
import java.util.stream.Collectors;

import com.eduardo.springbootvirtualstore.domain.Cidade;
import com.eduardo.springbootvirtualstore.domain.Estado;
import com.eduardo.springbootvirtualstore.dto.CidadeDTO;
import com.eduardo.springbootvirtualstore.dto.EstadoDTO;
import com.eduardo.springbootvirtualstore.services.CidadeService;
import com.eduardo.springbootvirtualstore.services.EstadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    private EstadoService service;

    @Autowired
    private CidadeService cidadeService;
    
    @GetMapping
    public ResponseEntity<List<EstadoDTO>> findAllOrderByName(){
        List<Estado> estados = service.findAllOrderByName();
        List<EstadoDTO> estadosDTO = estados.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(estadosDTO);
    }

    @GetMapping(value = "/{estadoId}/cidades")
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId){
        List<Cidade> cidades = cidadeService.findCidades(estadoId);
        List<CidadeDTO> cidadesDTO = cidades.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(cidadesDTO);
    }
}