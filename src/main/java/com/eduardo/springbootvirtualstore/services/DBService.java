package com.eduardo.springbootvirtualstore.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import com.eduardo.springbootvirtualstore.domain.Categoria;
import com.eduardo.springbootvirtualstore.domain.Cidade;
import com.eduardo.springbootvirtualstore.domain.Cliente;
import com.eduardo.springbootvirtualstore.domain.Endereco;
import com.eduardo.springbootvirtualstore.domain.Estado;
import com.eduardo.springbootvirtualstore.domain.ItemPedido;
import com.eduardo.springbootvirtualstore.domain.Pagamento;
import com.eduardo.springbootvirtualstore.domain.PagamentoComBoleto;
import com.eduardo.springbootvirtualstore.domain.PagamentoComCartao;
import com.eduardo.springbootvirtualstore.domain.Pedido;
import com.eduardo.springbootvirtualstore.domain.Produto;
import com.eduardo.springbootvirtualstore.domain.enums.EstadoPagamento;
import com.eduardo.springbootvirtualstore.domain.enums.Perfil;
import com.eduardo.springbootvirtualstore.domain.enums.TipoCliente;
import com.eduardo.springbootvirtualstore.repositories.CategoriaRepository;
import com.eduardo.springbootvirtualstore.repositories.CidadeRepository;
import com.eduardo.springbootvirtualstore.repositories.ClienteRepository;
import com.eduardo.springbootvirtualstore.repositories.EnderecoRepository;
import com.eduardo.springbootvirtualstore.repositories.EstadoRepository;
import com.eduardo.springbootvirtualstore.repositories.ItemPedidoRepository;
import com.eduardo.springbootvirtualstore.repositories.PagamentoRepository;
import com.eduardo.springbootvirtualstore.repositories.PedidoRepository;
import com.eduardo.springbootvirtualstore.repositories.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {
    
    @Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired 
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

    public void instantiateTestDatabase() throws ParseException{
        
        Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletronicos");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritorio", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Tv true color", 1200.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5));
		cat4.getProdutos().addAll(Arrays.asList(p6));

		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().add(cat1);
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat4));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "eduardocaldas.dev@gmail.com", "36378912377", TipoCliente.PESSOAFISICA, pe.encode("130899duka"));
		Cliente cli2 = new Cliente(null, "Eduardo Caldas", "eduardo.p.p.caldas@gmail.com", "01967159548", TipoCliente.PESSOAFISICA, pe.encode("130899duka"));
		cli2.addPerfil(Perfil.ADMIN);
		cli1.getTelefones().addAll(Arrays.asList("34245252", "34208096"));
		cli2.getTelefones().addAll(Arrays.asList("999991284"));
		Endereco e1 = new Endereco(null, "Rua Flores", "29", "ap 102", "Centro", "45000315", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "sala 102", "Centro", "45000315", cli1, c2);
		Endereco e3 = new Endereco(null, "Avenida Floriano", "2106", null, "Centro", "45000315", cli2, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().add(e3);

		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().add(ip1);
		p2.getItens().add(ip3);
		p3.getItens().add(ip2);

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
    }
}