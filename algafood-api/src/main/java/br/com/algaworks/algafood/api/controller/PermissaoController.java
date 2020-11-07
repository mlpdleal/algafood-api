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
import br.com.algaworks.algafood.domain.model.Permissao;
import br.com.algaworks.algafood.domain.repository.PermissaoRepository;
import br.com.algaworks.algafood.domain.service.CadastroPermissaoService;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private CadastroPermissaoService cadastroPermissaoService;
	
	@GetMapping
	public List<Permissao> listar(){
		return permissaoRepository.findAll();
	}
	
	@GetMapping("{permissaoId}")
	public ResponseEntity<Permissao> buscar(@PathVariable Long permissaoId){
		Optional<Permissao> permissao = permissaoRepository.findById(permissaoId);
		
		if(permissao.isPresent()) {
			return ResponseEntity.ok().body(permissao.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Permissao incluir(@RequestBody Permissao permissao) {
		return cadastroPermissaoService.salvar(permissao);
	}
	
	@PutMapping("{permissaoId}")
	public ResponseEntity<Permissao> alterar(@PathVariable Long permissaoId,
			@RequestBody Permissao permissao){
		
		Optional<Permissao> permissaoAlterada = permissaoRepository.findById(permissaoId);
		
		if(permissaoAlterada.isPresent()) {
			permissaoAlterada.get().setNome(permissao.getNome());
			permissaoAlterada.get().setDescricao(permissao.getDescricao());
			Permissao permissaoSalva = cadastroPermissaoService.salvar(permissaoAlterada.get());
			return ResponseEntity.ok().body(permissaoSalva);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{permissaoId}")
	public ResponseEntity<?> excluir(@PathVariable Long permissaoId){
		try {
			cadastroPermissaoService.excluir(permissaoId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaExcepion ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (EntidadeEmUsoException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}
	
}
