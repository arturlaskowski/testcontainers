package pl.javasenior.test3;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

@ActiveProfiles({"test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

    static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:16.1")
            .withDatabaseName("horses")
            .withUsername("username")
            .withPassword("kon")
            .withExposedPorts(5432);

    static {
        POSTGRESQL_CONTAINER.start();

        System.setProperty("spring.datasource.url", POSTGRESQL_CONTAINER.getJdbcUrl());
        System.setProperty("spring.datasource.username", POSTGRESQL_CONTAINER.getUsername());
        System.setProperty("spring.datasource.password", POSTGRESQL_CONTAINER.getPassword());
    }
}

