package br.com.algaworks.algafood.domain.exception;

public class PermissaoNaoEncontradaExcepion extends EntidadeNaoEncontradaExcepion{

	private static final long serialVersionUID = 1L;
	
	public PermissaoNaoEncontradaExcepion (String mensagem) {
		super(mensagem);
	}
	
	public PermissaoNaoEncontradaExcepion(Long permissaoId) {
		this(String.format("Não existe cadastro de permissao com id %d", permissaoId));
	}

}
