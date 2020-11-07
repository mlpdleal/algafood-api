package br.com.algaworks.algafood.apiTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import br.com.algaworks.algafood.AlgafoodApiApplication;
import br.com.algaworks.algafood.domain.model.Cozinha;
import br.com.algaworks.algafood.domain.repository.CozinhaRepository;
import br.com.algaworks.algafood.util.DatabaseCleaner;
import br.com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(classes = AlgafoodApiApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaApiTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private String jsonCorretoCozinhaChinesa = ResourceUtils.
			getContentFromResource("/json/correto/cozinha-chinesa.json");
	
	private static final Long COZINHA_INEXISTENTE = 1000L;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		databaseCleaner.clearTables();
		preparaMassaTeste();
	
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarRecursoCozinha_QuandoConsultarCozinha() {
		
		given()
			.pathParam("cozinhaId", 1)
			.accept(ContentType.JSON)
		.when()
			.get("{cozinhaId}")
		.then()
			.body("nome", is("Chinesa"));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoConsultarCozinhaInexistente() {
		given()
			.pathParam("cozinhaId", COZINHA_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
			
		given()
			.body(jsonCorretoCozinhaChinesa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("nome", is("Chinesa"));
	}
	
	private void preparaMassaTeste() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Chinesa");
		cozinhaRepository.save(cozinha);
	}

}
