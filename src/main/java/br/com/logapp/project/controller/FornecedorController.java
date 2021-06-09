package br.com.logapp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.logapp.project.repository.FornecedorRepository;

@RestController
public class FornecedorController {

	@Autowired
	private FornecedorRepository fornecedorRepository;
}
