package service.api;

import java.util.Map;

public class UserRequestBuilder implements RequestBuilder {
    private static final String PREFIX = "https://api.stackexchange.com/2.3/";
    private static final String SITE = "site=stackoverflow";
    private static final String FILTER = "filter=!P)usXvvTVLsqpq0WqvGhjAVUOoK_rNtWLcJ-hvWIVig";
    private static final String METHOD = "users";

    @Override
    public String createRequest(Map<String,String> params) {

        StringBuilder builder = new StringBuilder(PREFIX);

        builder.append(METHOD).append('?');
        for (Map.Entry<String,String> param : params.entrySet()) {
            builder.append(param.getKey())
                    .append('=')
                    .append(param.getValue())
                    .append('&');
        }
        builder.append(SITE).append('&').append(FILTER);

        return builder.toString();
    }
}
