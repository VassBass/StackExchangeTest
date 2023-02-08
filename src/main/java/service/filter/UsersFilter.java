package service.filter;

import model.UserEntry;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class UsersFilter {
    private final Collection<UserEntry> buffer;

    private UsersFilter(Collection<UserEntry> entries){
        this.buffer = entries == null ? new ArrayList<>() : new ArrayList<>(entries);
    }

    public static UsersFilter createFilter(Collection<UserEntry> entries) {
        return new UsersFilter(entries);
    }

    public UsersFilter filterByLocations(final String ... locations) {
        if (locations.length > 0) {
            buffer.removeIf(entry -> {
                String location = entry.getLocation();
                return location == null ||
                        Arrays.stream(locations)
                                .filter(Objects::nonNull)
                                .map(String::toLowerCase)
                                .noneMatch(location.toLowerCase()::contains);
            });
        }
        return this;
    }

    public UsersFilter filterByMinAnswersCount(final int minAnswersCount) {
        buffer.removeIf(entry -> entry.getAnswer_count() < minAnswersCount);
        return this;
    }

    public UsersFilter filterByTags(UserRepository repository,
                                    final String ... tags) {
        for (String tag : tags) {
            repository.fillTagIfUsersHasIt(tag, buffer.toArray(new UserEntry[0]));
        }
        buffer.removeIf(entry -> entry.getTags() == null || entry.getTags().isEmpty());

        return this;
    }

    public Collection<UserEntry> getResult() {
        return buffer;
    }
}
