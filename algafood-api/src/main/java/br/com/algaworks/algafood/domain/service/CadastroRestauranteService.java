package br.com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import br.com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoExcepion;
import br.com.algaworks.algafood.domain.model.Cozinha;
import br.com.algaworks.algafood.domain.model.Restaurante;
import br.com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_EM_USO = "O restaurante de id %d não pode ser excluído, pois está em uso";

	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "O restaurante de id %d não foi encontrado";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cadastroCozinha.buscar(cozinhaId);
		
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			restauranteRepository.deleteById(id);
			restauranteRepository.flush();
		} catch(EmptyResultDataAccessException ex) {
			throw new RestauranteNaoEncontradoExcepion(
					String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id));
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(
					String.format(MSG_RESTAURANTE_EM_USO, id));
		}
	}
	
	public Restaurante buscar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
									.orElseThrow(() -> new RestauranteNaoEncontradoExcepion(
									String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
	}
	
	
}
