package br.com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradoExcepion extends EntidadeNaoEncontradaExcepion{

	private static final long serialVersionUID = 1L;
	
	public FormaPagamentoNaoEncontradoExcepion (String mensagem) {
		super(mensagem);
	}
	
	public FormaPagamentoNaoEncontradoExcepion(Long estadoId) {
		this(String.format("Não existe cadastro de estado com id %d", estadoId));
	}

}
