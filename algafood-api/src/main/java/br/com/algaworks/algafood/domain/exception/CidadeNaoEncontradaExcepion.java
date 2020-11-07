package br.com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaExcepion extends EntidadeNaoEncontradaExcepion{

	private static final long serialVersionUID = 1L;
	
	public CidadeNaoEncontradaExcepion (String mensagem) {
		super(mensagem);
	}
	
	public CidadeNaoEncontradaExcepion(Long cidadeId) {
		this(String.format("Não existe cadastro de cidade com id %d", cidadeId));
	}

}
