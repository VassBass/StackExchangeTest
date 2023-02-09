package model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserEntryTest {

    @Test
    void testGetTags() {
        UserEntry userEntry = UserEntry.builder().build();
        assertNotNull(userEntry.getTags());

        List<String> expected = List.of("java", "c#");
        userEntry = UserEntry.builder().tags(expected).build();

        Collection<String> actual = userEntry.getTags();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void testAddTag() {
        UserEntry userEntry = UserEntry.builder().build();
        String tag = "java";
        userEntry.addTag(tag);

        Collection<String> expected = Collections.singleton(tag);
        Collection<String> actual = userEntry.getTags();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void testAddTags() {
        UserEntry userEntry = UserEntry.builder().build();
        Collection<String> firstAdd = List.of("java", "maven", "c#");
        Set<String> expected = new HashSet<>(firstAdd);
        userEntry.addTags(firstAdd);

        Collection<String> actual = userEntry.getTags();
        assertEquals(actual.size(), expected.size());
        assertTrue(expected.containsAll(actual));

        List<String> secondAdd = List.of("c++", "java", "python");
        expected.addAll(secondAdd);
        userEntry.addTags(secondAdd);

        assertEquals(actual.size(), expected.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void testSetTags() {
        String tag = "java";

        UserEntry userEntry = UserEntry.builder().build();
        userEntry.setTags(null);
        assertNotNull(userEntry.getTags());

        List<String> expected = Collections.singletonList(tag);
        userEntry.setTags(expected);
        Collection<String> actual = userEntry.getTags();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }
}