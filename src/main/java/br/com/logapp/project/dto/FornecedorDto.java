package br.com.logapp.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FornecedorDto {

	private String nome;
	
	public FornecedorDto(String nome) {
		this.nome = nome;
	}
}
