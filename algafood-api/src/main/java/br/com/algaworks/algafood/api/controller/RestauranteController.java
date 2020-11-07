package br.com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import br.com.algaworks.algafood.api.assembler.RestauranteModelDisassembler;
import br.com.algaworks.algafood.api.model.RestauranteDTO;
import br.com.algaworks.algafood.api.model.input.RestauranteInput;
import br.com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaExcepion;
import br.com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaExcepion;
import br.com.algaworks.algafood.domain.exception.NegocioException;
import br.com.algaworks.algafood.domain.model.Restaurante;
import br.com.algaworks.algafood.domain.repository.RestauranteRepository;
import br.com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteModelDisassembler restauranteModelDisassembler;

	@GetMapping
	public List<RestauranteDTO> listar() {
		return restauranteModelAssembler.toCollectList(restauranteRepository.findAll());
	}

	@GetMapping("/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome) {

		return restauranteRepository.findComFreteGratis(nome);
	}

	@GetMapping("/por-taxa")
	public List<Restaurante> listarTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}

	@GetMapping("/por-nome")
	public List<Restaurante> listarPorNome(String nome, Long cozinhaId) {
		return restauranteRepository.consultarPorNome(nome, cozinhaId);
	}

	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable Long restauranteId) {
		
		return restauranteModelAssembler.toModel(cadastroRestauranteService.buscar(restauranteId));
		
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(
			@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			Restaurante restaurante = restauranteModelDisassembler.toDomainModel(restauranteInput);
			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
		} catch (CozinhaNaoEncontradaExcepion ex) {
			throw new NegocioException(ex.getMessage());
		}
		
			
	}


	@PutMapping("{restauranteId}")
	public RestauranteDTO alterar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {

		Restaurante restauranteAtual = cadastroRestauranteService.buscar(restauranteId);
		
		try {
			
			restauranteModelDisassembler.copyToDomainModel(restauranteInput, restauranteAtual);
			
			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
		} catch (EntidadeNaoEncontradaExcepion ex) {
			throw new NegocioException(ex.getMessage());
		}
		

	}

	/*
	 * @PatchMapping("{restauranteId}") public ResponseEntity<?>
	 * atualizarParcial(@PathVariable Long restauranteId,
	 * 
	 * @RequestBody Map<String, Object> campos) {
	 * 
	 * Optional<Restaurante> restauranteAtual =
	 * restauranteRepository.findById(restauranteId);
	 * 
	 * if (restauranteAtual.isEmpty()) { return ResponseEntity.notFound().build(); }
	 * 
	 * merge(campos, restauranteAtual.get());
	 * 
	 * return alterar(restauranteId, restauranteAtual.get()); }
	 */

	/*
	 * private void merge(Map<String, Object> camposOrigem, Restaurante
	 * restauranteDestino) {
	 * 
	 * ObjectMapper objectMapper = new ObjectMapper(); Restaurante restauranteOrigem
	 * = objectMapper.convertValue(camposOrigem, Restaurante.class);
	 * 
	 * camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
	 * 
	 * Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
	 * field.setAccessible(true);
	 * 
	 * Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
	 * 
	 * ReflectionUtils.setField(field, restauranteDestino, novoValor);
	 * 
	 * }); }
	 */

	@DeleteMapping("{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long restauranteId) {
		cadastroRestauranteService.remover(restauranteId);
	}
	

	
}
