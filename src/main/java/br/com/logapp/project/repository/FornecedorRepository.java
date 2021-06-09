package br.com.logapp.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.logapp.project.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>{
	Optional<Fornecedor> findByNome(String nome);
}
