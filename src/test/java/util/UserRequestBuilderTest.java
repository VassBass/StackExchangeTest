package util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestBuilderTest {

    @Test
    void testCreateRequest() {
        Map<String, String> params = new HashMap<>();
        params.put("pagesize",  "100");
        params.put("order", "desc");
        params.put("min", "2000");
        params.put("sort", "reputation");

        String expected = "https://api.stackexchange.com/2.3/users?min=2000&pagesize=100&sort=reputation&order=desc&site=stackoverflow&filter=!*VD3*0vFuWPCL70BjHOSyzll-N";
        String actual = UserRequestBuilder.createRequest(params);
        assertEquals(expected, actual);
    }
}