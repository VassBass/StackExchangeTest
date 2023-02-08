import constant.Location;
import constant.Tag;
import model.UserEntry;
import repository.RepositoryFactory;
import repository.config.DefaultRepositoryConfigHolder;
import repository.user.UserRepository;
import service.filter.UsersFilter;

import java.util.Arrays;
import java.util.Collection;

public class App {
    private static final int MIN_REPUTATION = 223;
    private static final int MIN_ANSWERS_COUNT = 1;

    private static final String[] LOCATIONS = {
            Location.MOLDOVA, Location.ROMANIA
    };

    private static final String[] TAGS = {
            Tag.JAVA, Tag.DOCKER, Tag.NET, Tag.CSharp
    };

    public static void main(String[] args) {
        RepositoryFactory repositoryFactory = new RepositoryFactory();
        UserRepository userRepository = repositoryFactory.createRepository(UserRepository.class);

        Collection<UserEntry> usersByMinReputation = userRepository.getUsersByMinReputation(MIN_REPUTATION);
        Collection<UserEntry> filteredByLocation = UsersFilter.createFilter(usersByMinReputation)
                .filterByLocations(LOCATIONS).getResult();
        Collection<UserEntry> filteredByMinAnswersCount = UsersFilter.createFilter(filteredByLocation)
                .filterByMinAnswersCount(MIN_ANSWERS_COUNT).getResult();
        Collection<UserEntry> filteredByTags = UsersFilter.createFilter(filteredByMinAnswersCount)
                        .filterByTags(TAGS).getResult();

        System.err.printf("Max number of pages to search : %s%n", DefaultRepositoryConfigHolder.getInstance().getMaxResponsePages());
        System.err.printf("Found users with min reputation = %s : %s%n", MIN_REPUTATION, usersByMinReputation.size());
        usersByMinReputation.forEach(System.out::println);
        System.err.printf("Of which with location %s : %s%n", Arrays.toString(LOCATIONS), filteredByLocation.size());
        filteredByLocation.forEach(System.out::println);
        System.err.printf("Of which with min answers count = %s : %s%n", MIN_ANSWERS_COUNT, filteredByMinAnswersCount.size());
        filteredByMinAnswersCount.forEach(System.out::println);
        System.err.printf("Of which with tags = %s : %s%n", Arrays.toString(TAGS), filteredByTags.size());
        filteredByTags.forEach(System.out::println);
    }
}
