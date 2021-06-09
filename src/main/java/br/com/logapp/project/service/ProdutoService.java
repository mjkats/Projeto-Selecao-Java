package br.com.logapp.project.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.logapp.project.dto.ProdutoDto;
import br.com.logapp.project.model.Categoria;
import br.com.logapp.project.model.Fornecedor;
import br.com.logapp.project.model.Produto;
import br.com.logapp.project.repository.CategoriaRepository;
import br.com.logapp.project.repository.FornecedorRepository;
import br.com.logapp.project.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Transactional
	public ResponseEntity<String> createProduto(Produto produto) {
		Optional<Produto> optProduto = produtoRepository.findByNome(produto.getNome());
		if (optProduto.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto with same name already exists");
		
		Optional<Categoria> optCategoria = categoriaRepository.findByNome(produto.getCategoria().getNome());
		if (optCategoria.isPresent())
			produto.setCategoria(optCategoria.get());
		
		Optional<Fornecedor> optFornecedor = fornecedorRepository.findByNome(produto.getFornecedor().getNome());
		if (optFornecedor.isPresent())
			produto.setFornecedor(optFornecedor.get());
		
		produtoRepository.save(produto);
		
		return new ResponseEntity<> ("Produto was created successfully", HttpStatus.CREATED);
	}
	
	@Transactional
	public ResponseEntity<String> editProduto(BigDecimal valor, Integer quantidade, long id) {
		Optional<Produto> optProduto = produtoRepository.findById(id);
		if (optProduto.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto wasn't found");
		
		if (quantidade == null && valor == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No changes for produto");
		}
		
		Produto produto = optProduto.get();
		
		if (quantidade == produto.getQuantidade() && valor == produto.getValor())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No changes for produto");
		
		if (quantidade != null)
			produto.setQuantidade(quantidade);
		
		if (valor != null)
			produto.setValor(valor);
		
		produtoRepository.save(optProduto.get());
		
		return new ResponseEntity<> ("Produto was altered successfully", HttpStatus.OK);
	}
	
	@Transactional(readOnly = true)
	public Set<ProdutoDto> getProdutosSemEstoque() {
		Set<Produto> produtos = produtoRepository.findByQuantidadeIs(0);
		return(produtos.stream().map(produto -> produto.toDto()).collect(Collectors.toSet()));
	}
	
	@Transactional(readOnly = true)
	public Map<String, Set<String>> getFornecedoresEstoqueVazio() {
		Map<String, Set<String>> fornecedorProdutosEstoqueVazio = new HashMap<>();
		
		List<Fornecedor> fornecedores = fornecedorRepository.findAll();
		for (Fornecedor fornecedor : fornecedores) {
			Set<Produto> produtos = produtoRepository.findByFornecedorIsAndQuantidadeIs(fornecedor, 0);
			Set<String> nomeProdutosSemEstoque = new HashSet<>();
			
			for (Produto produto : produtos) {
				nomeProdutosSemEstoque.add(produto.getNome());
				fornecedorProdutosEstoqueVazio.put(fornecedor.getNome(), nomeProdutosSemEstoque);
			}
			
		}
		
		return(fornecedorProdutosEstoqueVazio);
	}
	
	@Transactional(readOnly = true)
	public Map<String, Map<String, Integer>> getCategoriasComProdutos() {
		Map<String, Map<String, Integer>> categoriasProdutosNomeQuantidade = new HashMap<>();
		
		List<Categoria> categorias = categoriaRepository.findAll();
		for (Categoria categoria : categorias) {
			Set<Produto> produtos = produtoRepository.findByCategoriaIs(categoria);
			Map<String, Integer> produtosNomeQuantidade = new HashMap<>();
			
			for (Produto produto : produtos) {
				produtosNomeQuantidade.put(produto.getNome(), produto.getQuantidade());
				categoriasProdutosNomeQuantidade.put(categoria.getNome(), produtosNomeQuantidade);
			}
		}
		
		return categoriasProdutosNomeQuantidade;
	}
}
