package repository.user;

import model.UserEntry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import repository.config.DefaultRepositoryConfigHolder;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DefaultUserRepositoryTest {
    private static final String PROPERTIES_FILE_PATH = "application_test.properties";

    private static UserRepository repository;

    @BeforeAll
    public static void init() {
        repository = new DefaultUserRepository(
                new DefaultRepositoryConfigHolder(PROPERTIES_FILE_PATH));
    }

    /**
     * Polling for changes should be done sparingly in any case,
     * and polling at a rate faster than once a minute (for semantically identical requests)
     * is considered abusive.
     *
     * @see <a href=https://api.stackexchange.com/docs></a>
     */
    private void pleaseWait() {
        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Disabled("Disabled because the number of API requests per day is limited")
    @Test
    void testGetUsersByMinReputation() {
        pleaseWait();

        final int reputation = 1_000_000;
        Collection<UserEntry> actual = repository.getUsersByMinReputation(reputation);

        actual.forEach(System.out::println);

        assertFalse(actual.stream().anyMatch(u -> u.getReputation() < reputation));
    }
}