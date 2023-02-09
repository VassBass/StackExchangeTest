package service.filter;

import model.UserEntry;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UsersFilterTest {

    @Test
    void testFilterByExistedLocations() {
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

        List<UserEntry> expected = List.of(
                userFromRomania1, userFromRomania2, userFromMoldova1, userFromMoldova2, userFromMoldova3);
        Collection<UserEntry> actual = List.of(
                userFromUkraine1, userFromRomania1, userFromRomania2, userFromMoldova1, userFromMoldova2,
                userFromMoldova3, userFromUsa1, userFromUsa2, userFromUsa3
        );


        actual = UsersFilter.createFilter(actual).filterByLocations(romania, moldova).getResult();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void testFilterByEmptyLocation() {
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

        Collection<UserEntry> expected = List.of(
                userFromUkraine1, userFromRomania1, userFromRomania2, userFromMoldova1, userFromMoldova2,
                userFromMoldova3, userFromUsa1, userFromUsa2, userFromUsa3
        );
        Collection<UserEntry> actual = new ArrayList<>(expected);

        actual = UsersFilter.createFilter(actual).filterByLocations().getResult();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void testFilterByNotExistedLocation() {
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

        Collection<UserEntry> actual = List.of(
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
                UserEntry.builder().answer_count(random.nextInt(100)).build();
        UserEntry user2WithAnswerLess100 =
                UserEntry.builder().answer_count(random.nextInt(100)).build();
        UserEntry user1WithAnswerOver100 =
                UserEntry.builder().answer_count(random.nextInt(101, 1_000)).build();
        UserEntry user2WithAnswerOver100 =
                UserEntry.builder().answer_count(random.nextInt(101, 1_000)).build();
        UserEntry user3WithAnswerOver100 =
                UserEntry.builder().answer_count(random.nextInt(101, 1_000)).build();
        UserEntry user4WithAnswerOver100 =
                UserEntry.builder().answer_count(random.nextInt(101, 1_000)).build();

        Collection<UserEntry> actual = List.of(
                user1WithAnswerLess100, user2WithAnswerLess100, user1WithAnswerOver100,
                user2WithAnswerOver100, user3WithAnswerOver100, user4WithAnswerOver100
        );

        actual = UsersFilter.createFilter(actual).filterByMinAnswersCount(101).getResult();
        assertFalse(actual.stream().anyMatch(u -> u.getAnswer_count() < 100));
    }

    @Test
    void testFilterByExistedTags() {
        final String java = "java", maven = "maven", cSharp = "c#", docker = "docker";
        String[] tagsForFilter = { java, maven };

        UserEntry userWithAllTags1 = UserEntry.builder().tags(List.of(java, maven, cSharp, docker)).build();
        UserEntry userWithAllTags2 = UserEntry.builder().tags(List.of(java, maven, docker)).build();
        UserEntry userWithAllTags3 = UserEntry.builder().tags(List.of(java, maven)).build();
        UserEntry userWithNotAllTags1 = UserEntry.builder().tags(List.of(java, cSharp, docker)).build();
        UserEntry userWithNotAllTags2 = UserEntry.builder().tags(List.of(maven, cSharp, docker)).build();
        UserEntry userWithNotAllTags3 = UserEntry.builder().tags(List.of(java, docker)).build();
        UserEntry userWithNotAllTags4 = UserEntry.builder().tags(List.of(maven, cSharp)).build();
        UserEntry userWithNotAllTags5 = UserEntry.builder().tags(List.of(maven)).build();
        UserEntry userWithNotAllTags6 = UserEntry.builder().tags(List.of(java)).build();
        UserEntry userWithoutTags1 = UserEntry.builder().tags(List.of(cSharp)).build();
        UserEntry userWithoutTags2 = UserEntry.builder().tags(List.of(docker)).build();
        UserEntry userWithoutTags3 = UserEntry.builder().build();


        List<UserEntry> expected = List.of(
                userWithAllTags1, userWithAllTags2, userWithAllTags3,
                userWithNotAllTags1, userWithNotAllTags2, userWithNotAllTags3,
                userWithNotAllTags4, userWithNotAllTags5, userWithNotAllTags6);
        Collection<UserEntry> actual = List.of(
                userWithAllTags1, userWithAllTags2, userWithAllTags3,
                userWithNotAllTags1, userWithNotAllTags2, userWithNotAllTags3,
                userWithNotAllTags4, userWithNotAllTags5, userWithNotAllTags6,
                userWithoutTags1, userWithoutTags2, userWithoutTags3);

        actual = UsersFilter.createFilter(actual).filterByTags(tagsForFilter).getResult();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void testFilterByEmptyTags() {
        final String java = "java", maven = "maven", cSharp = "c#", docker = "docker";

        UserEntry userWithAllTags1 = UserEntry.builder().tags(List.of(java, maven, cSharp, docker)).build();
        UserEntry userWithAllTags2 = UserEntry.builder().tags(List.of(java, maven, docker)).build();
        UserEntry userWithAllTags3 = UserEntry.builder().tags(List.of(java, maven)).build();
        UserEntry userWithNotAllTags1 = UserEntry.builder().tags(List.of(java, cSharp, docker)).build();
        UserEntry userWithNotAllTags2 = UserEntry.builder().tags(List.of(maven, cSharp, docker)).build();
        UserEntry userWithNotAllTags3 = UserEntry.builder().tags(List.of(java, docker)).build();
        UserEntry userWithNotAllTags4 = UserEntry.builder().tags(List.of(maven, cSharp)).build();
        UserEntry userWithNotAllTags5 = UserEntry.builder().tags(List.of(maven)).build();
        UserEntry userWithNotAllTags6 = UserEntry.builder().tags(List.of(java)).build();
        UserEntry userWithoutTags1 = UserEntry.builder().tags(List.of(cSharp)).build();
        UserEntry userWithoutTags2 = UserEntry.builder().tags(List.of(docker)).build();
        UserEntry userWithoutTags3 = UserEntry.builder().build();

        Collection<UserEntry> expected = List.of(
                userWithAllTags1, userWithAllTags2, userWithAllTags3,
                userWithNotAllTags1, userWithNotAllTags2, userWithNotAllTags3,
                userWithNotAllTags4, userWithNotAllTags5, userWithNotAllTags6,
                userWithoutTags1, userWithoutTags2, userWithoutTags3);
        Collection<UserEntry> actual = new ArrayList<>(expected);

        actual = UsersFilter.createFilter(actual).filterByTags().getResult();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void testFilterByNotExistedTags() {
        final String java = "java", maven = "maven", cSharp = "c#", docker = "docker";

        UserEntry userWithAllTags1 = UserEntry.builder().tags(List.of(java, maven, cSharp, docker)).build();
        UserEntry userWithAllTags2 = UserEntry.builder().tags(List.of(java, maven, docker)).build();
        UserEntry userWithAllTags3 = UserEntry.builder().tags(List.of(java, maven)).build();
        UserEntry userWithNotAllTags1 = UserEntry.builder().tags(List.of(java, cSharp, docker)).build();
        UserEntry userWithNotAllTags2 = UserEntry.builder().tags(List.of(maven, cSharp, docker)).build();
        UserEntry userWithNotAllTags3 = UserEntry.builder().tags(List.of(java, docker)).build();
        UserEntry userWithNotAllTags4 = UserEntry.builder().tags(List.of(maven, cSharp)).build();
        UserEntry userWithNotAllTags5 = UserEntry.builder().tags(List.of(maven)).build();
        UserEntry userWithNotAllTags6 = UserEntry.builder().tags(List.of(java)).build();
        UserEntry userWithoutTags1 = UserEntry.builder().tags(List.of(cSharp)).build();
        UserEntry userWithoutTags2 = UserEntry.builder().tags(List.of(docker)).build();
        UserEntry userWithoutTags3 = UserEntry.builder().build();

        Collection<UserEntry> actual = List.of(
                userWithAllTags1, userWithAllTags2, userWithAllTags3,
                userWithNotAllTags1, userWithNotAllTags2, userWithNotAllTags3,
                userWithNotAllTags4, userWithNotAllTags5, userWithNotAllTags6,
                userWithoutTags1, userWithoutTags2, userWithoutTags3);

        actual = UsersFilter.createFilter(actual).filterByTags("Not Existed").getResult();
        assertTrue(actual.isEmpty());
    }
}