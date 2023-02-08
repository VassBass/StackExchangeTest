package repository.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.CommonWrapper;
import model.UserEntry;
import repository.config.RepositoryConfigHolder;
import service.api.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DefaultUserRepository implements UserRepository {
    private final RequestBuilder requestBuilder;
    private final RepositoryConfigHolder configHolder;
    private final Gson gson;

    public DefaultUserRepository(RepositoryConfigHolder configHolder, Gson gson) {
        this.requestBuilder = new UserRequestBuilder();
        this.configHolder = configHolder;
        this.gson = gson;
    }

    @Override
    public Collection<UserEntry> getUsersByMinReputation(int reputation) {
        Collection<UserEntry> buffer = new ArrayList<>();

        Map<String, String> params = new HashMap<>();
        params.put("pagesize",  "100");
        params.put("order", "desc");
        params.put("min", String.valueOf(reputation));
        params.put("sort", "reputation");

        fillBufferByResponseContent(params, buffer);

        return buffer;
    }

    @Override
    public Collection<UserEntry> getAllUsers() {
        Collection<UserEntry> buffer = new ArrayList<>();

        Map<String, String> params = new HashMap<>();
        params.put("pagesize",  "100");
        params.put("order", "asc");
        params.put("sort", "reputation");

        fillBufferByResponseContent(params, buffer);

        return buffer;
    }

    private void fillBufferByResponseContent(Map<String, String> params,
                                             Collection<UserEntry>buffer) {
        int page = 1;
        boolean hasMore = true;
        while (hasMore) {
            if (page > configHolder.getMaxResponsePages()) break;

            String request = requestBuilder.createRequest(params);
            System.out.printf("Sending request ... %s%n", request);
            try {
                HttpConnection connection = HttpConnection.createConnection(request);
                ResponseWorker responseWorker = new GZIPResponseWorker(connection);
                String json = responseWorker.getResponseJsonContent();

                if (!json.isEmpty()) {
                    Type dataType = new TypeToken<CommonWrapper<UserEntry>>() {}.getType();
                    CommonWrapper<UserEntry> usersWrap = gson.fromJson(json, dataType);

                    buffer.addAll(usersWrap.getItems());
                    hasMore = usersWrap.isHas_more();
                    params.put("page", String.valueOf(++page));
                } else {
                    int code = connection.getResponseCode();
                    if (code != 200) {
                        System.err.printf("Server returned response code = %s. Please try again later.%n", code);
                    } else {
                        System.err.println("Server returned empty response.");
                    }
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
