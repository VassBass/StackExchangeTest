package util;

import lombok.NonNull;
import model.Collective;
import model.Collectives;
import model.UserEntry;

import java.util.*;

/**
 * Util for simplify work with user tags
 *
 * @see UserEntry
 * @see Collectives
 * @see Collective
 */
public class TagsHelper {
    public static Collection<String> getTags(@NonNull UserEntry user) {
        Collection<String> result = new ArrayList<>();

        Collection<Collectives> collectives = user.getCollectives();
        if (collectives != null && !collectives.isEmpty()) {
            collectives.stream()
                    .filter(Objects::nonNull)
                    .forEach(c -> result.addAll(c.getCollective().getTags()));
        }

        return result;
    }

    public static Collection<Collectives> createTags(String ... tags) {
        return Collections.singletonList(new Collectives(new Collective(Arrays.asList(tags))));
    }

    public static void putTags(UserEntry user, String ... tags) {
        if (user.getCollectives() == null) user.setCollectives(new ArrayList<>());
        user.getCollectives().addAll(createTags(tags));
    }

    public static void clearTags(UserEntry user) {
        user.setCollectives(new ArrayList<>());
    }

    public static void changeTags(UserEntry user, String ... tags) {
        user.setCollectives(createTags(tags));
    }
}
