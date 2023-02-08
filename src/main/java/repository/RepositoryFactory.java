package repository;

import com.google.gson.Gson;
import repository.config.DefaultRepositoryConfigHolder;
import repository.user.DefaultUserRepository;
import repository.user.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class RepositoryFactory {
    private final Map<Class<?>, Object> buffer = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T createRepository(Class<T> clazz) {
        T i = (T) buffer.get(clazz);

        if (i == null) {
            if (clazz.isAssignableFrom(UserRepository.class))
                i = (T) new DefaultUserRepository(DefaultRepositoryConfigHolder.getInstance(), new Gson());

            if (i == null) System.err.printf("Can't find implementation for %s%n", clazz.getName());
            else buffer.put(clazz, i);
        }

        return i;
    }
}
