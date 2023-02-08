package repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.config.DefaultRepositoryConfigHolder;
import repository.config.RepositoryConfigHolder;

import static org.junit.jupiter.api.Assertions.*;

class DefaultRepositoryConfigHolderTest {
    private static final String PROPERTIES_FILE_PATH = "application_test.properties";

    private RepositoryConfigHolder configHolder;

    @BeforeEach
    public void setUp() {
        configHolder = new DefaultRepositoryConfigHolder(PROPERTIES_FILE_PATH);
    }

    @AfterEach
    public void tearDown() {
        configHolder = null;
    }

    @Test
    void getMaxResponsePages() {
        assertEquals(1, configHolder.getMaxResponsePages());
    }
}