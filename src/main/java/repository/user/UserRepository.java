package repository.user;

import model.UserEntry;

import java.util.Collection;

public interface UserRepository {
    /**
     * @return Collection of users with min reputation and without tags
     *
     * @param reputation min reputation
     */
    Collection<UserEntry> getUsersByMinReputation(int reputation);

    /**
     * If the user has a tag, it will be added to the UserEntry
     * @param users to check
     * @param tag to check
     */
    void fillTagIfUsersHasIt(String tag, UserEntry ... users);
}
