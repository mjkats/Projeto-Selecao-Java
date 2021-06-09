package br.com.logapp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.logapp.project.service.CategoriaService;

@RestController
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
}
