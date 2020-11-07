package br.com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoExcepion extends EntidadeNaoEncontradaExcepion{

	private static final long serialVersionUID = 1L;
	
	public RestauranteNaoEncontradoExcepion (String mensagem) {
		super(mensagem);
	}
	
	public RestauranteNaoEncontradoExcepion(Long restauranteId) {
		this(String.format("Não existe cadastro de restaunrante com id %d", restauranteId));
	}

}
