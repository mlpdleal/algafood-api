package br.com.algaworks.algafood.domain.exception;

public class EstadoNaoEncontradoExcepion extends EntidadeNaoEncontradaExcepion{

	private static final long serialVersionUID = 1L;
	
	public EstadoNaoEncontradoExcepion (String mensagem) {
		super(mensagem);
	}
	
	public EstadoNaoEncontradoExcepion(Long estadoId) {
		this(String.format("Não existe cadastro de estado com id %d", estadoId));
	}

}
