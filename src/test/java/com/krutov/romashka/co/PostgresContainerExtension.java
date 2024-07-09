package com.krutov.romashka.co;


import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainerExtension implements Extension, BeforeAllCallback, ExtensionContext.Store.CloseableResource{

    private static PostgreSQLContainer<?> container;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (container == null) {
            container = new PostgreSQLContainer<>("postgres:16.1-alpine");
            container.start();

            System.setProperty("POSTGRES_HOST", container.getHost());
            System.setProperty("POSTGRES_PORT", String.valueOf(container.getMappedPort(5432)));
            System.setProperty("POSTGRES_NAME", String.valueOf(container.getDatabaseName()));
            System.setProperty("POSTGRES_USERNAME", String.valueOf(container.getUsername()));
            System.setProperty("POSTGRES_PASSWORD", String.valueOf(container.getPassword()));
        }
    }

    @Override
    public void close() throws Throwable {
        container.stop();
    }
}
