package util;

import model.UserEntry;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class TagsRequestBuilder {
    private static final String PREFIX = "https://api.stackexchange.com/2.3/users/";
    private static final String SITE = "site=stackoverflow";
    private static final String FILTER = "filter=!*MKI39u1wuwFySgd";
    private static final String SUFFIX = SITE + '&' + FILTER;

    /**
     * Create request to find tags by users ids and tag name
     * <p/>
     * params it necessary to has:
     *      key("ids") -> value(users ids in String {@link StringHelper#intArrayToString(int...)})
     *      key("min") -> value(tag name)
     *      key("max") -> value(tag name)
     * min.equals(max) - condition necessary for the correct result
     * <p/>
     * Example:
     * <pre>{@code
     * int[] usersIds = { 1, 2, 3, 4 };
     * Map<String, String> params = new HashMap<>();
     * params.put("ids", StringHelper.intArrayToString(usersIds));
     * params.put("min", "java");
     * params.put("max", "java");
     * }</pre>
     *
     * @param params to request
     *
     * @return GET request for getting {@code CommonWrapper<Tag>()}
     */
    public static String createRequest(Map<String, String> params) {
        if (!params.containsKey("ids")) return EMPTY;

        StringBuilder builder = new StringBuilder(PREFIX);

        builder.append(params.get("ids")).append("/tags?");
        params.remove("ids");
        for (Map.Entry<String,String> param : params.entrySet()) {
            builder.append(param.getKey())
                    .append('=')
                    .append(param.getValue())
                    .append("&");
        }
        builder.append(SUFFIX);

        return builder.toString();
    }
}
