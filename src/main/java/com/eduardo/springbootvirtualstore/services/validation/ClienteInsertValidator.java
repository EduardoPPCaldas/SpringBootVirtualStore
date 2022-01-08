package com.eduardo.springbootvirtualstore.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.eduardo.springbootvirtualstore.domain.Cliente;
import com.eduardo.springbootvirtualstore.domain.enums.TipoCliente;
import com.eduardo.springbootvirtualstore.dto.ClienteNewDTO;
import com.eduardo.springbootvirtualstore.repositories.ClienteRepository;
import com.eduardo.springbootvirtualstore.resources.exceptions.FieldMessage;
import com.eduardo.springbootvirtualstore.services.validation.utils.BR;

import org.springframework.beans.factory.annotation.Autowired;


public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClienteInsert clienteInsert){

    }

    @Override
    public boolean isValid(ClienteNewDTO obj, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(obj.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(obj.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
        } 

        if(obj.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(obj.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido"));
        }

        Cliente aux = repo.findByEmail(obj.getEmail());

        if (aux != null) {
            list.add(new FieldMessage("email", "Email já existente"));
        }

        // Get list of errors and make the constraints
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
    
}