package br.com.logapp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.logapp.project.nonmodel.Vogal;
import br.com.logapp.project.service.VogalService;

@RestController
public class VogalController {
	
	@Autowired
	private VogalService vogalService;
	
	@PostMapping("/vogal")
	public Vogal getVogalFromString(@RequestParam String input) {
		return vogalService.getVogalFromString(input);
	}
}
