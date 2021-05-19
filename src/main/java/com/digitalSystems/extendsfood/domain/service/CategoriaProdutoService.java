package com.digitalSystems.extendsfood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.digitalSystems.extendsfood.domain.exception.CategoriaProdutoNaoEncontradoException;
import com.digitalSystems.extendsfood.domain.exception.EntidadeEmUsoException;
import com.digitalSystems.extendsfood.domain.model.CategoriaProduto;
import com.digitalSystems.extendsfood.domain.model.Restaurante;
import com.digitalSystems.extendsfood.domain.repository.CategoriaProdutoRepository;

@Service
public class CategoriaProdutoService {

	private static final String COZINHA_PRODUTO_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private CategoriaProdutoRepository categoriaRepository;
	
	@Autowired
	private RestauranteService restauranteService;

	@Transactional
	public CategoriaProduto salvar(CategoriaProduto categoriaProduto, Long restauranteId) {
		
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		
		categoriaProduto.setRestaurante(restaurante);
		
		return categoriaRepository.save(categoriaProduto);
	}

	@Transactional
	public void excluir(Long categoriaProdutoId) {
		try {
			categoriaRepository.deleteById(categoriaProdutoId);
			
			categoriaRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new CategoriaProdutoNaoEncontradoException(categoriaProdutoId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(COZINHA_PRODUTO_EM_USO, categoriaProdutoId));
		}
	}
	
	public List<CategoriaProduto> buscarCategoriasRestarante(Long restauranteId){
		
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		
		return categoriaRepository.findTodosByRestaurante(restaurante.getId());
	}
	
	public CategoriaProduto buscarOuFalhar(Long restauranteId, Long categoriaProdutoId) {
		
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		
		return categoriaRepository.findCategoriaByRestaurante(restaurante.getId(), categoriaProdutoId)
				.orElseThrow(() -> new CategoriaProdutoNaoEncontradoException(categoriaProdutoId, restauranteId));
	}

}
