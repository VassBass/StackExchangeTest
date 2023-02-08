package service.api;

import java.util.Map;

public interface RequestBuilder {
    String createRequest(Map<String, String> params);
}
