package repository.user;

import model.UserEntry;

import java.util.Collection;

public interface UserRepository {
    Collection<UserEntry> getAllUsers();
    Collection<UserEntry> getUsersByMinReputation(int reputation);
}
