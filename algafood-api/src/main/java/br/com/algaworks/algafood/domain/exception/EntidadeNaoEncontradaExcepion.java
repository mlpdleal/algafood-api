package br.com.algaworks.algafood.domain.exception;

public abstract class EntidadeNaoEncontradaExcepion extends NegocioException{

	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaExcepion (String mensagem) {
		super(mensagem);
	}

}
