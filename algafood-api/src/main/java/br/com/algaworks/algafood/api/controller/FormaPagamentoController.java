package br.com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import br.com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaExcepion;
import br.com.algaworks.algafood.domain.model.FormaPagamento;
import br.com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import br.com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@GetMapping
	public List<FormaPagamento> listar(){
		return formaPagamentoRepository.findAll(); 
	}
	
	@GetMapping("{formaPagamentoId}")
	public ResponseEntity<FormaPagamento> buscar(@PathVariable Long formaPagamentoId){
		Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(formaPagamentoId);
		
		if(formaPagamento.isPresent()) {
			return ResponseEntity.ok().body(formaPagamento.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamento incluir(@RequestBody FormaPagamento formaPagamento){
		return cadastroFormaPagamentoService.salvar(formaPagamento);
	}
	
	@PutMapping("{formaPagamentoId}")
	public ResponseEntity<FormaPagamento> alterar(@PathVariable Long formaPagamentoId, 
			@RequestBody FormaPagamento formaPagamento){
		Optional<FormaPagamento> formaPagamentoAlterada = formaPagamentoRepository.findById(formaPagamentoId);
		
		if(formaPagamentoAlterada.isPresent()) {
			formaPagamentoAlterada.get().setDescricao(formaPagamento.getDescricao());
			FormaPagamento formaPagamentoSalva = cadastroFormaPagamentoService.salvar(formaPagamentoAlterada.get());
			return ResponseEntity.ok().body(formaPagamentoSalva);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{formaPagamentoId}")
	public ResponseEntity<FormaPagamento> excluir(@PathVariable Long formaPagamentoId){
		try {
			cadastroFormaPagamentoService.excluir(formaPagamentoId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaExcepion ex) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
}
