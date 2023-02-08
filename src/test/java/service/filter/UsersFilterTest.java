package service.filter;

import model.UserEntry;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UsersFilterTest {

    @Test
    void testFilterByRomaniaAndMoldova() {
        final String ukraine = "Ukraine", romania = "Romania", moldova = "moldova", usa = "USA";

        UserEntry userFromUkraine1 = UserEntry.builder().location(ukraine).build();
        UserEntry userFromRomania1 = UserEntry.builder().location(romania).build();
        UserEntry userFromRomania2 = UserEntry.builder().location(romania).build();
        UserEntry userFromMoldova1 = UserEntry.builder().location(moldova).build();
        UserEntry userFromMoldova2 = UserEntry.builder().location(moldova).build();
        UserEntry userFromMoldova3 = UserEntry.builder().location(moldova).build();
        UserEntry userFromUsa1 = UserEntry.builder().location(usa).build();
        UserEntry userFromUsa2 = UserEntry.builder().location(usa).build();
        UserEntry userFromUsa3 = UserEntry.builder().location(usa).build();

        List<UserEntry> expected = Arrays.asList(
                userFromRomania1, userFromRomania2, userFromMoldova1, userFromMoldova2, userFromMoldova3);
        Collection<UserEntry> actual = Arrays.asList(
                userFromUkraine1, userFromRomania1, userFromRomania2, userFromMoldova1, userFromMoldova2,
                userFromMoldova3, userFromUsa1, userFromUsa2, userFromUsa3
        );


        actual = UsersFilter.createFilter(actual).filterByLocations(romania, moldova).getResult();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void testFilterByEmpty() {
        final String ukraine = "Ukraine", romania = "Romania", moldova = "moldova", usa = "USA";

        UserEntry userFromUkraine1 = UserEntry.builder().location(ukraine).build();
        UserEntry userFromRomania1 = UserEntry.builder().location(romania).build();
        UserEntry userFromRomania2 = UserEntry.builder().location(romania).build();
        UserEntry userFromMoldova1 = UserEntry.builder().location(moldova).build();
        UserEntry userFromMoldova2 = UserEntry.builder().location(moldova).build();
        UserEntry userFromMoldova3 = UserEntry.builder().location(moldova).build();
        UserEntry userFromUsa1 = UserEntry.builder().location(usa).build();
        UserEntry userFromUsa2 = UserEntry.builder().location(usa).build();
        UserEntry userFromUsa3 = UserEntry.builder().location(usa).build();

        Collection<UserEntry> expected = Arrays.asList(
                userFromUkraine1, userFromRomania1, userFromRomania2, userFromMoldova1, userFromMoldova2,
                userFromMoldova3, userFromUsa1, userFromUsa2, userFromUsa3
        );
        Collection<UserEntry> actual = new ArrayList<>(expected);

        actual = UsersFilter.createFilter(actual).filterByLocations().getResult();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void testFilterByNotExisted() {
        final String ukraine = "Ukraine", romania = "Romania", moldova = "moldova", usa = "USA";

        UserEntry userFromUkraine1 = UserEntry.builder().location(ukraine).build();
        UserEntry userFromRomania1 = UserEntry.builder().location(romania).build();
        UserEntry userFromRomania2 = UserEntry.builder().location(romania).build();
        UserEntry userFromMoldova1 = UserEntry.builder().location(moldova).build();
        UserEntry userFromMoldova2 = UserEntry.builder().location(moldova).build();
        UserEntry userFromMoldova3 = UserEntry.builder().location(moldova).build();
        UserEntry userFromUsa1 = UserEntry.builder().location(usa).build();
        UserEntry userFromUsa2 = UserEntry.builder().location(usa).build();
        UserEntry userFromUsa3 = UserEntry.builder().location(usa).build();

        Collection<UserEntry> actual = Arrays.asList(
                userFromUkraine1, userFromRomania1, userFromRomania2, userFromMoldova1, userFromMoldova2,
                userFromMoldova3, userFromUsa1, userFromUsa2, userFromUsa3
        );

        actual = UsersFilter.createFilter(actual).filterByLocations("Not Existed").getResult();
        assertTrue(actual.isEmpty());
    }

    @Test
    void testFilterByMinAnswersCount() {
        Random random = new Random();
        UserEntry user1WithAnswerLess100 =
                UserEntry.builder().answer_count(random.nextInt( 100)).build();
        UserEntry user2WithAnswerLess100 =
                UserEntry.builder().answer_count(random.nextInt( 100)).build();
        UserEntry user1WithAnswerOver100 =
                UserEntry.builder().answer_count(random.nextInt( 101, 1_000)).build();
        UserEntry user2WithAnswerOver100 =
                UserEntry.builder().answer_count(random.nextInt( 101, 1_000)).build();
        UserEntry user3WithAnswerOver100 =
                UserEntry.builder().answer_count(random.nextInt( 101, 1_000)).build();
        UserEntry user4WithAnswerOver100 =
                UserEntry.builder().answer_count(random.nextInt( 101, 1_000)).build();

        Collection<UserEntry> actual = Arrays.asList(
                user1WithAnswerLess100, user2WithAnswerLess100, user1WithAnswerOver100,
                user2WithAnswerOver100, user3WithAnswerOver100, user4WithAnswerOver100
        );

        actual = UsersFilter.createFilter(actual).filterByMinAnswersCount(101).getResult();
        assertFalse(actual.stream().anyMatch(u -> u.getAnswer_count() < 100));
    }
}