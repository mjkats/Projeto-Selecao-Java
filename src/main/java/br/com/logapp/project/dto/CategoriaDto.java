package br.com.logapp.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaDto {

	private String nome;
	
	public CategoriaDto(String nome) {
		this.nome = nome;
	}
}
