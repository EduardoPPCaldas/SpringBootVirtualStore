package com.eduardo.springbootvirtualstore.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import java.awt.image.BufferedImage;

import com.eduardo.springbootvirtualstore.domain.Cidade;
import com.eduardo.springbootvirtualstore.domain.Cliente;
import com.eduardo.springbootvirtualstore.domain.Endereco;
import com.eduardo.springbootvirtualstore.domain.enums.Perfil;
import com.eduardo.springbootvirtualstore.domain.enums.TipoCliente;
import com.eduardo.springbootvirtualstore.dto.ClienteDTO;
import com.eduardo.springbootvirtualstore.dto.ClienteNewDTO;
import com.eduardo.springbootvirtualstore.repositories.ClienteRepository;
import com.eduardo.springbootvirtualstore.repositories.EnderecoRepository;
import com.eduardo.springbootvirtualstore.security.UserSS;
import com.eduardo.springbootvirtualstore.services.exceptions.AuthorizationException;
import com.eduardo.springbootvirtualstore.services.exceptions.DataIntegrityException;
import com.eduardo.springbootvirtualstore.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repo;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Value("${img.profile.size}")
    private Integer size;

    public Cliente find(Integer id){
        UserSS user = UserService.authenticated();

        if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())){
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()
        ));
    }

    public List<Cliente> list(){
        List<Cliente> list = repo.findAll();
        return list;
    }

    public Cliente findByEmail(String email){
        UserSS user = UserService.authenticated();
        if(user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
            throw new AuthorizationException("Acesso negado");
        }

        Cliente obj = repo.findByEmail(email);

        if(obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id:" + user.getId() + ", Tipo: " + Cliente.class.getName());
        }

        return obj;
    }

    @Transactional
    public Cliente insert(Cliente cliente){
        cliente.setId(null);
        cliente = repo.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return cliente;
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
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO clienteNewDTO){
        Cliente cli = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipo()), pe.encode(clienteNewDTO.getSenha()));
        Cidade cid = new Cidade(clienteNewDTO.getCidadeId(), null, null);
        Endereco end = new Endereco(null,
        clienteNewDTO.getLogradouro(),
        clienteNewDTO.getNumero(),
        clienteNewDTO.getComplemento(),
        clienteNewDTO.getBairro(),
        clienteNewDTO.getCep(),
        cli,
        cid);

        cli.getEnderecos().add(end);
        cli.getTelefones().add(clienteNewDTO.getTelefone1());
        if(clienteNewDTO.getTelefone2() != null){
            cli.getTelefones().add(clienteNewDTO.getTelefone2());
        }
        if(clienteNewDTO.getTelefone3() != null){
            cli.getTelefones().add(clienteNewDTO.getTelefone3());
        }

        return cli;
    }

    private void updateData(Cliente newObj, Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile){
        UserSS user = UserService.authenticated();
        if(user == null){
            throw new AuthorizationException("Acesso negado");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);

        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);

        String fileName = prefix + user.getId() + ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }
}