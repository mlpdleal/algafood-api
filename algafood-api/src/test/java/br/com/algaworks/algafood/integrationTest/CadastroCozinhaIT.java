package br.com.algaworks.algafood.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import br.com.algaworks.algafood.AlgafoodApiApplication;
import br.com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import br.com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaExcepion;
import br.com.algaworks.algafood.domain.model.Cozinha;
import br.com.algaworks.algafood.domain.model.Restaurante;
import br.com.algaworks.algafood.domain.repository.CozinhaRepository;
import br.com.algaworks.algafood.domain.service.CadastroCozinhaService;
import br.com.algaworks.algafood.domain.service.CadastroRestauranteService;

@SpringBootTest(classes = AlgafoodApiApplication.class)
@TestPropertySource("application-test.properties")
public class CadastroCozinhaIT {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Test
	public void cadastroCozinhaComSucesso() {
		
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
		
	}

	@Test()
	public void cadastroCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		
		Assertions.assertThrows(ConstraintViolationException.class,
				() -> cadastroCozinha.salvar(novaCozinha));
	}
	
	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Brasileira");
		
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		Long idCozinha = novaCozinha.getId();
		
		Restaurante novoRestaurante = new Restaurante();
		novoRestaurante.setCozinha(novaCozinha);
		novoRestaurante.setNome("Fogo de chão");
		novoRestaurante.setTaxaFrete(BigDecimal.valueOf(10.90));
		
		novoRestaurante = cadastroRestaurante.salvar(novoRestaurante);
		
		Assertions.assertThrows(EntidadeEmUsoException.class,
				() -> cadastroCozinha.excluir(idCozinha));
		
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		
		List<Cozinha> cozinhas = cozinhaRepository.findAll();
		Cozinha ultimaCozinha = cozinhas.get(cozinhas.size() - 1);
		
		Assertions.assertThrows(EntidadeNaoEncontradaExcepion.class, 
				() -> cadastroCozinha.excluir(ultimaCozinha.getId() + 10L));
		
	}

}
