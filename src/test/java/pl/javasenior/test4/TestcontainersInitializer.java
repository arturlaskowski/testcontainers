package pl.javasenior.test4;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

class TestcontainersInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16.1")
                    .withDatabaseName("horses")
                    .withUsername("username")
                    .withPassword("kon")
                    .withExposedPorts(5432);

    static {
        postgres.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
        TestPropertyValues.of(
                "spring.datasource.url=" + postgres.getJdbcUrl(),
                "spring.datasource.username=" + postgres.getUsername(),
                "spring.datasource.password=" + postgres.getPassword()
        ).applyTo(ctx.getEnvironment());
    }
}
