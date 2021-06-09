package br.com.logapp.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.logapp.project.dto.FornecedorDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Fornecedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 128, nullable = false, unique = true)
	private String nome;
	
	public Fornecedor(String nome) {
		this.nome = nome;
	}
	
	public FornecedorDto toDto() {
		return new FornecedorDto(this.getNome());
	}
}
