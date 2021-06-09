package br.com.logapp.project.nonmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vogal {

	private String string;
	private Character vogal;
	private String tempoTotal;
	
	public Vogal(String string, String tempoTotal) {
		this.string = string;
		this.tempoTotal = tempoTotal;
	}
}
