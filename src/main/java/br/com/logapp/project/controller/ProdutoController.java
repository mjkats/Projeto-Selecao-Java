package br.com.logapp.project.controller;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.logapp.project.dto.ProdutoDto;
import br.com.logapp.project.model.Produto;
import br.com.logapp.project.service.ProdutoService;

@RestController
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@PostMapping("/produtos")
	public ResponseEntity<String> createProduto(@RequestBody Produto produto) {
		return produtoService.createProduto(produto);
	}
	
	@PutMapping("/produtos/{id}")
	public ResponseEntity<String> editProduto(@RequestParam(required = false) BigDecimal valor,
			@RequestParam(required = false) Integer quantidade,
			@PathVariable long id) {
		return produtoService.editProduto(valor, quantidade, id);
	}
	
	@GetMapping("produtos/estoque-faltante")
	public Set<ProdutoDto> getProdutosSemEstoque() {
		return produtoService.getProdutosSemEstoque();
	}
	
	@GetMapping("fornecedores/estoque-faltante")
	public Map<String, Set<String>> getFornecedoresEstoqueVazio() {
		return produtoService.getFornecedoresEstoqueVazio();
	}
	
	@GetMapping("categorias/produto/quantidade")
	public Map<String, Map<String, Integer>> getCategoriasComProdutos() {
		return produtoService.getCategoriasComProdutos();
	}
}
