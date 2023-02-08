package repository.user;

import model.UserEntry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.RepositoryFactory;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DefaultUserRepositoryTest {

    private static UserRepository repository;

    @BeforeAll
    public static void init() {
        repository = RepositoryFactory.getInstance().createRepository(UserRepository.class);
    }

    /**
     * Polling for changes should be done sparingly in any case,
     * and polling at a rate faster than once a minute (for semantically identical requests)
     * is considered abusive.
     *
     * @see <a href=https://api.stackexchange.com/docs></a>
     */
    @BeforeEach
    public void pleaseWait() throws InterruptedException {
        TimeUnit.MINUTES.sleep(1);
    }

    @Test
    void getUsersByMinReputation() {
        final int reputation = 1_000_000;
        Collection<UserEntry> actual = repository.getUsersByMinReputation(reputation);

        assertFalse(actual.stream().anyMatch(u -> u.getReputation() < reputation));
    }
}