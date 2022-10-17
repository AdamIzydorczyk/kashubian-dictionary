package tk.aizydorczyk.kashubian;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
@ComponentScan(value = "tk.aizydorczyk.kashubian")
public class TestConfig {
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14.5")
            .withDatabaseName("test-db")
            .withUsername("test")
            .withPassword("test");

    public static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            postgreSQLContainer.start();
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                    "spring.flyway.enabled=true",
                    "spring.jpa.show-sql=false",
                    "spring.flyway.baseline-on-migrate=true",
                    "initializer.size=0"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
