package repository.user;

import lombok.NonNull;
import model.UserEntry;

import java.util.Collection;

public interface UserRepository {
    /**
     * @return Collection of users with min reputation
     *
     * @param reputation min reputation
     */
    Collection<UserEntry> getUsersByMinReputation(int reputation);

    /**
     * Finds tags by user IDs and, if found, adds them to the list of user tags.
     *
     * @param user for search
     */
    void fillUserWithTags(@NonNull UserEntry user);
}
