package service.api.json;

import lombok.NonNull;
import model.Tag;
import model.UserEntry;

import java.util.Collection;

public interface JsonMapper {
    /**
     * Put users from api json response to collection of users
     * @param json response from api
     * @param users collection
     * @return true if more users can be retrieved
     */
    boolean putUsersFromAPIJson(@NonNull String json, @NonNull Collection<UserEntry> users);

    /**
     * Put tags from api json response to collection of tags
     * @param json response from api
     * @param tags collection
     * @return true if more tags can be retrieved
     */
    boolean putTagsFromAPIJson(@NonNull String json, @NonNull Collection<Tag> tags);

    String objectToJson(@NonNull Object o);
}
