package util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TagsRequestBuilderTest {

    @Test
    void testCreateRequestWithValidParams() {
        Map<String, String> params = new HashMap<>();
        params.put("ids", "1;2;3;4;5;6;7;8");
        params.put("pagesize", "100");
        params.put("order", "desc");
        params.put("min", "java");
        params.put("max", "java");
        params.put("sort", "name");

        String expected = "https://api.stackexchange.com/2.3/users/1;2;3;4;5;6;7;8/tags?min=java&max=java&pagesize=100&sort=name&order=desc&site=stackoverflow&filter=!*MKI39u1wuwFySgd";
        String actual = TagsRequestBuilder.createRequest(params);
        assertEquals(expected, actual);
    }

    @Test
    void testCreateRequestWithoutIds() {
        Map<String, String> params = new HashMap<>();
        params.put("pagesize", "100");
        params.put("order", "desc");
        params.put("min", "java");
        params.put("max", "java");
        params.put("sort", "name");

        String actual = TagsRequestBuilder.createRequest(params);
        assertTrue(actual.isEmpty());
    }
}