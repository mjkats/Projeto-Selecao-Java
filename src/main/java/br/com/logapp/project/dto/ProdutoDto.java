package br.com.logapp.project.dto;

import java.math.BigDecimal;

import br.com.logapp.project.model.Categoria;
import br.com.logapp.project.model.Fornecedor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProdutoDto {
	
	private String nome;
	private BigDecimal valor;
	private int quantidade;
	private CategoriaDto categoria; 
	private FornecedorDto fornecedor;
	
	public ProdutoDto(String nome, BigDecimal valor, int quantidade, Categoria categoria, Fornecedor fornecedor) {
		this.nome = nome;
		this.valor = valor;
		this.categoria = categoria.toDto();
		this.quantidade = quantidade;
		this.fornecedor = fornecedor.toDto();
	}
}
