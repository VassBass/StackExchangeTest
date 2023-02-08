package util;

import java.util.Map;

public class UserRequestBuilder {
    private static final String PREFIX = "https://api.stackexchange.com/2.3/users?";
    private static final String SITE = "site=stackoverflow";
    private static final String FILTER = "filter=!*VD3*0vFuWPCL70BjHOSyzll-N";
    private static final String SUFFIX = SITE + '&' + FILTER;

    public static String createRequest(Map<String,String> params) {

        StringBuilder builder = new StringBuilder(PREFIX);

        for (Map.Entry<String,String> param : params.entrySet()) {
            builder.append(param.getKey())
                    .append('=')
                    .append(param.getValue())
                    .append('&');
        }
        builder.append(SUFFIX);

        return builder.toString();
    }
}
