package com.assessment.bank;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import lombok.RequiredArgsConstructor;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class DatabaseConnectionTest {

    private final DataSource dataSource;

    @Test
    void shouldConnectToDatabase() throws Exception {
        assertNotNull(dataSource, "Test Connection DataSource must not be null");

        try (Connection connection = dataSource.getConnection()) {
            assertFalse(connection.isClosed(), "Test Connection should be open");
            System.out.println("Test Connection successfully to MySQL: " + connection.getMetaData().getURL());
        }
    }
}
