package br.com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CozinhaNaoEncontradaExcepion extends EntidadeNaoEncontradaExcepion{

	private static final long serialVersionUID = 1L;
	
	public CozinhaNaoEncontradaExcepion (String mensagem) {
		super(mensagem);
	}
	
	public CozinhaNaoEncontradaExcepion(Long cozinhaId) {
		this(String.format("Não existe cadastro de cozinha com id %d", cozinhaId));
	}

}
