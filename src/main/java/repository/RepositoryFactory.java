package repository;

import repository.config.DefaultRepositoryConfigHolder;
import repository.user.DefaultUserRepository;
import repository.user.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class RepositoryFactory {
    private static volatile RepositoryFactory instance;
    private RepositoryFactory(){}
    public static RepositoryFactory getInstance() {
        if (instance == null) {
            synchronized (RepositoryFactory.class) {
                if (instance == null) instance = new RepositoryFactory();
            }
        }
        return instance;
    }

    private final Map<Class<?>, Object> buffer = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T createRepository(Class<T> clazz) {
        T i = (T) buffer.get(clazz);

        if (i == null) {
            if (clazz.isAssignableFrom(UserRepository.class))
                i = (T) new DefaultUserRepository(DefaultRepositoryConfigHolder.getInstance());

            if (i == null) System.err.printf("Can't find implementation for %s%n", clazz.getName());
            else buffer.put(clazz, i);
        }

        return i;
    }
}
