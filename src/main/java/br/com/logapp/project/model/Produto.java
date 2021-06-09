package br.com.logapp.project.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.logapp.project.dto.ProdutoDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 128, nullable = false, unique = true)
	private String nome;
	
	@Column(scale = 2, precision = 7, nullable = false)
	private BigDecimal valor;
	
	@Column(nullable = false)
	private int quantidade;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "categoria_id", referencedColumnName = "id")
	private Categoria categoria; 
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fornecedor_id", referencedColumnName = "id")
	private Fornecedor fornecedor;
	
	public Produto(String nome, BigDecimal valor, int quantidade, Categoria categoria, Fornecedor fornecedor) {
		this.nome = nome;
		this.valor = valor;
		this.categoria = categoria;
		this.quantidade = quantidade;
		this.fornecedor = fornecedor;
	}
	
	public ProdutoDto toDto() {
		return new ProdutoDto(
				this.getNome(),
				this.getValor(),
				this.getQuantidade(),
				this.getCategoria(),
				this.getFornecedor()
		);
	}
}
