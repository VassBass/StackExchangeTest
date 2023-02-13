package service;

import model.UserEntry;
import repository.user.UserRepository;
import service.filter.UsersFilter;
import util.ParamsBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class UsersSearcher {
    private final UserRepository repository;

    public UsersSearcher(UserRepository repository) {
        this.repository = repository;
    }

    private Collection<UserEntry> findUsersByOptions(Options options) {
        Collection<UserEntry> result = new HashSet<>();

        int reputationOnEnd = options.reputation;
        while (true) {
            String order = ParamsBuilder.ASC;
            Collection<UserEntry> resultOfSession = repository.getUsersByMinReputation(reputationOnEnd, order);
            if (resultOfSession.isEmpty()) {
                break;
            } else {
                result.addAll(resultOfSession);
                int r = resultOfSession.stream()
                        .max(Comparator.comparing(UserEntry::getReputation))
                        .map(UserEntry::getReputation)
                        .get();
                if (r == reputationOnEnd) break;
                reputationOnEnd = r;
            }
        }
        return UsersFilter.createFilter(result)
                .filterByLocations(options.locations.toArray(new String[0]))
                .filterByMinAnswersCount(options.answersCount)
                .filterByTags(options.tags.toArray(new String[0]))
                .getResult();
    }

    public Options options() {
        return new Options(this);
    }

    public static class Options {
        private final UsersSearcher searcher;

        private int reputation;
        private final Collection<String> locations;
        private final Collection<String> tags;
        private int answersCount;

        private Options(UsersSearcher searcher) {
            this.searcher = searcher;

            locations = new HashSet<>();
            tags = new HashSet<>();
        }

        public Options setMinReputation(int reputation) {
            this.reputation = reputation;
            return this;
        }

        public Options setMinAnswersCount(int count) {
            answersCount = count;
            return this;
        }

        public Options addLocations(String ... locations) {
            this.locations.addAll(Arrays.stream(locations)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet()));
            return this;
        }

        public Options addTags(String ... tags) {
            this.tags.addAll(Arrays.stream(tags)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet()));
            return this;
        }

        public Collection<UserEntry> search() {
            return searcher.findUsersByOptions(this);
        }
    }
}
