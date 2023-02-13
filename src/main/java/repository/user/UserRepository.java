package repository.user;

import lombok.NonNull;
import model.UserEntry;

import java.util.Collection;

public interface UserRepository {
    /**
     * @return Collection of users with min reputation
     *
     * @param reputation min reputation
     * @param order order of result
     * @see util.ParamsBuilder#ASC
     * @see util.ParamsBuilder#DESC
     */
    Collection<UserEntry> getUsersByMinReputation(int reputation, String order);

    /**
     * Finds tags by user IDs and, if found, adds them to the list of user tags.
     *
     * @param user for search
     */
    void fillUserWithTags(@NonNull UserEntry user);
}
