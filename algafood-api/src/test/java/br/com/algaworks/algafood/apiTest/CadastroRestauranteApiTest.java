package br.com.algaworks.algafood.apiTest;

import static io.restassured.RestAssured.given;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import br.com.algaworks.algafood.AlgafoodApiApplication;
import br.com.algaworks.algafood.domain.model.Cozinha;
import br.com.algaworks.algafood.domain.model.Restaurante;
import br.com.algaworks.algafood.domain.repository.CozinhaRepository;
import br.com.algaworks.algafood.domain.repository.RestauranteRepository;
import br.com.algaworks.algafood.util.DatabaseCleaner;
import br.com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(classes = AlgafoodApiApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteApiTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private static final Long RESTAURANTE_INEXISTENTE = 1000L;
	private String jsonRestauranteCorreto = ResourceUtils.
			getContentFromResource("/json/correto/restaurante-correto.json");
	
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		
		databaseCleaner.clearTables();
		prepararMassaTeste();
	
	}
	
	@Test
	public void deveRetornarCodigo200_QuandoListarRestaurantes() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarRecursoRestaurante_QuandoConsultarRestaurante() {
		
		given()
			.pathParam("restauranteId", 1)
			.accept(ContentType.JSON)
		.when()
			.get("{restauranteId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", Matchers.is("Careca"));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoConsultarRestauranteInexistente() {
		
		given()
			.pathParam("restauranteId", RESTAURANTE_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("{restauranteId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestauranteComSucesso() {
		
		given()
			.body(jsonRestauranteCorreto)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("nome", Matchers.is("boteco do juca"))
			.body("cozinha.id", Matchers.is(1));
		
	}
	
	private void prepararMassaTeste() {
		
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Chinesa");
		cozinha = cozinhaRepository.save(cozinha);
		
		Restaurante restaurante = new Restaurante();
		restaurante.setNome("Careca");
		restaurante.setTaxaFrete(new BigDecimal(5.93));
		restaurante.setCozinha(cozinha);
		restaurante = restauranteRepository.save(restaurante);
		
	}

}
