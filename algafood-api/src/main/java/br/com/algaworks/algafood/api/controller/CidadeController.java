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

import br.com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import br.com.algaworks.algafood.api.assembler.CidadeModelDisassembler;
import br.com.algaworks.algafood.api.model.CidadeDTO;
import br.com.algaworks.algafood.api.model.input.CidadeInput;
import br.com.algaworks.algafood.domain.exception.EstadoNaoEncontradoExcepion;
import br.com.algaworks.algafood.domain.exception.NegocioException;
import br.com.algaworks.algafood.domain.model.Cidade;
import br.com.algaworks.algafood.domain.repository.CidadeRepository;
import br.com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CidadeModelAssembler cidadeAssembler;

	@Autowired
	private CidadeModelDisassembler cidadeDisassembler;
	
	@GetMapping
	public List<CidadeDTO> listar() {
		return cidadeAssembler.toCollectList(cidadeRepository.findAll());
	}

	@GetMapping("{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId) {
		return cidadeAssembler.toModel(cadastroCidadeService.buscar(cidadeId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeDisassembler.toDomainModel(cidadeInput);
			return cidadeAssembler.toModel(cadastroCidadeService.salvar(cidade));
		} catch (EstadoNaoEncontradoExcepion ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}

	}

	@PutMapping("{cidadeId}")
	public CidadeDTO alterar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {

		Cidade cidadeAlterada = cadastroCidadeService.buscar(cidadeId);

		try {
			cidadeDisassembler.copyToDomainModel(cidadeInput, cidadeAlterada);
			return cidadeAssembler.toModel(cadastroCidadeService.salvar(cidadeAlterada));
		} catch (EstadoNaoEncontradoExcepion ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}

	}

	@DeleteMapping("{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long cidadeId) {
		cadastroCidadeService.remover(cidadeId);
	}



}
