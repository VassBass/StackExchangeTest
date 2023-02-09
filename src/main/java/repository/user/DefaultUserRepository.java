package repository.user;

import lombok.NonNull;
import model.Tag;
import model.UserEntry;
import repository.config.RepositoryConfigHolder;
import service.api.GZIPResponseWorker;
import service.api.HttpConnection;
import service.api.ResponseWorker;
import service.api.json.GsonJsonMapper;
import service.api.json.JsonMapper;
import util.ParamsBuilder;
import util.TagsRequestBuilder;
import util.UserRequestBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultUserRepository implements UserRepository {
    private final RepositoryConfigHolder configHolder;
    private final JsonMapper jsonMapper = GsonJsonMapper.getInstance();

    public DefaultUserRepository(RepositoryConfigHolder configHolder) {
        this.configHolder = configHolder;
    }

    @Override
    public Collection<UserEntry> getUsersByMinReputation(int reputation) {
        Collection<UserEntry> buffer = new ArrayList<>();

        Map<String, String> params = new ParamsBuilder()
                .setPageSize(100)
                .setOrder(false)
                .setSort("reputation", String.valueOf(reputation), null)
                .build();
                new HashMap<>();

        int page = 1;
        boolean hasMore = true;
        while (hasMore) {
            if (page > configHolder.getMaxResponsePages()) break;

            String request = UserRequestBuilder.createRequest(params);
            System.out.printf("Sending request ... %s%n", request);
            try {
                HttpConnection connection = HttpConnection.createConnection(request);
                ResponseWorker responseWorker = new GZIPResponseWorker(connection);
                String json = responseWorker.getResponseJsonContent();

                if (!json.isEmpty()) {
                    hasMore = jsonMapper.putUsersFromAPIJson(json, buffer);
                    params.put("page", String.valueOf(++page));
                } else {
                    printErrorMessage(connection.getResponseCode());
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return buffer;
    }

    public void fillUserWithTags(@NonNull UserEntry user) {
        Map<String, String> params = new ParamsBuilder()
                .setPageSize(100)
                .setId(user.getUser_id())
                .setOrder(true)
                .setSort("name")
                .build();

        int page = 1;
        boolean hasMore = true;
        while (hasMore) {
            if (page > configHolder.getMaxResponsePages()) break;

            String request = TagsRequestBuilder.createRequest(params);
            System.out.printf("Sending request ... %s%n", request);
            try {
                HttpConnection connection = HttpConnection.createConnection(request);
                ResponseWorker responseWorker = new GZIPResponseWorker(connection);
                String json = responseWorker.getResponseJsonContent();

                if (!json.isEmpty()) {
                    Collection<Tag> buffer = new HashSet<>();
                    hasMore = jsonMapper.putTagsFromAPIJson(json, buffer);
                    user.addTags(buffer.stream().map(Tag::getName).collect(Collectors.toSet()));
                    params.put("page", String.valueOf(++page));
                } else {
                    printErrorMessage(connection.getResponseCode());
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void printErrorMessage(int responseCode) {
        if (responseCode != 200) {
            System.err.printf("Server returned response code = %s.%nThe last request was ignored%n", responseCode);
            System.err.println("Please try again later.");
        } else {
            System.err.println("Server returned empty response.");
        }
    }
}
