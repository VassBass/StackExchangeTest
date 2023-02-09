package util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class ParamsBuilderTest {
    private final Map<String, String> expected = new HashMap<>();

    @AfterEach
    public void clear() {
        expected.clear();
    }

    @Test
    void testSetPageSize() {
        expected.put("pagesize", "5");
        Map<String, String> actual = new ParamsBuilder()
                .setPageSize(5)
                .build();
        assertIterableEquals(expected.entrySet(), actual.entrySet());

        expected.put("pagesize", "10");
        actual = new ParamsBuilder()
                .setPageSize(10)
                .build();
        assertIterableEquals(expected.entrySet(), actual.entrySet());
    }

    @Test
    void testSetOrder() {
        expected.put("order", "desc");
        Map<String, String> actual = new ParamsBuilder()
                .setOrder(false)
                .build();
        assertIterableEquals(expected.entrySet(), actual.entrySet());

        expected.put("order", "asc");
        actual = new ParamsBuilder()
                .setOrder(true)
                .build();
        assertIterableEquals(expected.entrySet(), actual.entrySet());
    }

    @Test
    void testSetSort() {
        expected.put("sort", "random");
        Map<String, String> actual = new ParamsBuilder()
                .setSort("random")
                .build();
        assertIterableEquals(expected.entrySet(), actual.entrySet());

        expected.put("sort", "name");
        actual = new ParamsBuilder()
                .setSort("name")
                .build();
        assertIterableEquals(expected.entrySet(), actual.entrySet());
    }

    @Test
    void testSetSortWithRange() {
        expected.put("sort", "random");
        expected.put("min", "0");
        Map<String, String> actual = new ParamsBuilder()
                .setSort("random", "0", null)
                .build();
        assertIterableEquals(expected.entrySet(), actual.entrySet());

        expected.clear();
        expected.put("sort", "random");
        expected.put("max", "0");
        actual = new ParamsBuilder()
                .setSort("random", null, "0")
                .build();
        assertIterableEquals(expected.entrySet(), actual.entrySet());

        expected.clear();
        expected.put("sort", "random");
        expected.put("min", "0");
        expected.put("max", "z");
        actual = new ParamsBuilder()
                .setSort("random", "0", "z")
                .build();
        assertIterableEquals(expected.entrySet(), actual.entrySet());

        expected.clear();
        expected.put("sort", "random");
        actual = new ParamsBuilder()
                .setSort("random", null, null)
                .build();
        assertIterableEquals(expected.entrySet(), actual.entrySet());
    }

    @Test
    void testSetId() {
        expected.put("ids", "19523");
        Map<String, String> actual = new ParamsBuilder()
                .setId(19523)
                .build();
        assertIterableEquals(expected.entrySet(), actual.entrySet());

        expected.put("ids", "0");
        actual = new ParamsBuilder()
                .setId(0)
                .build();
        assertIterableEquals(expected.entrySet(), actual.entrySet());
    }

    @Test
    void testSetIds() {
        expected.put("ids", "1;2;3;4;5;6");
        Map<String, String> actual = new ParamsBuilder()
                .setIds(1, 2, 3, 4, 5, 6)
                .build();
        assertIterableEquals(expected.entrySet(), actual.entrySet());

        expected.put("ids", "0");
        actual = new ParamsBuilder()
                .setIds(0)
                .build();
        assertIterableEquals(expected.entrySet(), actual.entrySet());
    }
}