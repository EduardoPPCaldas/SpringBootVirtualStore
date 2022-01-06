package com.eduardo.springbootvirtualstore.services;

import java.util.List;
import java.util.Optional;

import com.eduardo.springbootvirtualstore.domain.Cliente;
import com.eduardo.springbootvirtualstore.dto.ClienteDTO;
import com.eduardo.springbootvirtualstore.repositories.ClienteRepository;
import com.eduardo.springbootvirtualstore.services.exceptions.DataIntegrityException;
import com.eduardo.springbootvirtualstore.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repo;

    public Cliente find(Integer id){
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()
        ));
    }

    public List<Cliente> list(){
        List<Cliente> list = repo.findAll();
        return list;
    }

    public Cliente insert(Cliente cliente){
        cliente.setId(null);
        return repo.save(cliente);
    }

    public Cliente update(Cliente cliente){
        Cliente newObj = find(cliente.getId());
        updateData(newObj, cliente);
        return repo.save(newObj);
    }

    public void delete(Integer id){
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Cliente que contem pedidos");
        }
        
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }
    
    public Cliente fromDTO(ClienteDTO clienteDTO){
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
    }

    private void updateData(Cliente newObj, Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}