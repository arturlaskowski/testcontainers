package pl.javasenior.test2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.javasenior.Horse;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@ActiveProfiles({"test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HorseRestIntegrationTest4 {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.1")
            .withDatabaseName("horses")
            .withUsername("username")
            .withPassword("kon")
            .withExposedPorts(5432);

    @DynamicPropertySource
    static void setEnv(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void addAndGetHorseTest() {
        //add
        Horse newHorse = new Horse("Kary", "Arab klasyczyny", true);
        ResponseEntity<Long> postResponse = restTemplate.postForEntity(getBaseUrl(), newHorse, Long.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long horseId = postResponse.getBody();
        assertThat(horseId).isNotNull();

        //get
        ResponseEntity<Horse> getResponse = restTemplate.getForEntity(getBaseUrl() + "/" + horseId, Horse.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getName()).isEqualTo("Kary");
        assertThat(getResponse.getBody().getBreed()).isEqualTo("Arab klasyczyny");
        assertThat(getResponse.getBody().isShod()).isTrue();
    }

    private String getBaseUrl() {
        return "http://localhost:" + port + "/horses";
    }
}
