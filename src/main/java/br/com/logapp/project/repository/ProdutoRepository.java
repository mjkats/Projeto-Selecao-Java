package br.com.logapp.project.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.logapp.project.model.Categoria;
import br.com.logapp.project.model.Fornecedor;
import br.com.logapp.project.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	Optional<Produto> findByNome(String nome);
	Set<Produto> findByQuantidadeIs(int quantidade);
	Set<Produto> findByCategoriaIs(Categoria categoria);
	Set<Produto> findByFornecedorIsAndQuantidadeIs(Fornecedor fornecedor, int quantidade);
}
