package br.com.algaworks.algafood.api.controller;

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

import br.com.algaworks.algafood.api.assembler.EstadoModelAssembler;
import br.com.algaworks.algafood.api.assembler.EstadoModelDisassembler;
import br.com.algaworks.algafood.api.model.EstadoDTO;
import br.com.algaworks.algafood.api.model.input.EstadoInput;
import br.com.algaworks.algafood.domain.model.Estado;
import br.com.algaworks.algafood.domain.repository.EstadoRepository;
import br.com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@Autowired
	private EstadoModelAssembler estadoAssembler;
	
	@Autowired
	private EstadoModelDisassembler estadoDisassembler;

	@GetMapping
	public List<EstadoDTO> listar() {
		return estadoAssembler.toCollectList(estadoRepository.findAll());
	}

	@GetMapping("{estadoId}")
	public EstadoDTO buscar(@PathVariable Long estadoId) {
		return estadoAssembler.toModel(cadastroEstadoService.buscar(estadoId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		
		Estado estado = estadoDisassembler.toDomainModel(estadoInput);
		
		return estadoAssembler.toModel(cadastroEstadoService.salvar(estado));
	}

	@PutMapping("{estadoId}")
	public EstadoDTO alterar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {

		Estado estadoAlterado = cadastroEstadoService.buscar(estadoId);
		
		estadoDisassembler.copyToDomainModel(estadoInput, estadoAlterado);
		
		return estadoAssembler.toModel(cadastroEstadoService.salvar(estadoAlterado));

	}

	@DeleteMapping("{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable long estadoId) {
		cadastroEstadoService.excluir(estadoId);
	}
}
