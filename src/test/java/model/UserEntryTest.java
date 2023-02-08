package model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserEntryTest {

    @Test
    void getTags() {
        UserEntry userEntry = UserEntry.builder().build();
        assertNotNull(userEntry.getTags());

        List<String> expected = Arrays.asList("java", "c#");
        userEntry = UserEntry.builder().tags(expected).build();

        Collection<String> actual = userEntry.getTags();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void addTag() {
        UserEntry userEntry = UserEntry.builder().build();
        String tag = "java";
        userEntry.addTag(tag);

        Collection<String> expected = Collections.singleton(tag);
        Collection<String> actual = userEntry.getTags();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void setTags() {
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