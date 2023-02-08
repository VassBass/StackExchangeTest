package repository.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.CommonWrapper;
import model.Tag;
import model.UserEntry;
import repository.config.RepositoryConfigHolder;
import service.api.GZIPResponseWorker;
import service.api.HttpConnection;
import service.api.ResponseWorker;
import util.StringHelper;
import util.TagsRequestBuilder;
import util.UserRequestBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class DefaultUserRepository implements UserRepository {
    private final RepositoryConfigHolder configHolder;
    private final Gson gson;

    public DefaultUserRepository(RepositoryConfigHolder configHolder, Gson gson) {
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

        return buffer;
    }

    /**
     * Finds tags by user IDs and, if found, adds them to the list of user tags.
     * @param tag to check
     * @param users to check. length <= 100
     */
    @Override
    public void fillTagIfUsersHasIt(String tag, UserEntry ... users) {
        if (users.length > 100) users = Arrays.copyOf(users, 100);

        int[] ids = Arrays.stream(users)
                .filter(Objects::nonNull)
                .mapToInt(UserEntry::getUser_id)
                .toArray();
        Map<String, String> params = new HashMap<>();
        params.put("ids", StringHelper.intArrayToString(ids));
        params.put("pagesize", "100");
        params.put("order", "desc");
        params.put("min", tag);
        params.put("max", tag);
        params.put("sort", "name");

        String request = TagsRequestBuilder.createRequest(params);
        System.out.printf("Sending request ... %s%n", request);
        try {
            HttpConnection connection = HttpConnection.createConnection(request);
            ResponseWorker responseWorker = new GZIPResponseWorker(connection);
            String json = responseWorker.getResponseJsonContent();

            if (!json.isEmpty()) {
                Type dataType = new TypeToken<CommonWrapper<Tag>>() {}.getType();
                CommonWrapper<Tag> usersWrap = gson.fromJson(json, dataType);
                Collection<Tag> tags = usersWrap.getItems();

                for (UserEntry user : users) {
                    if (tags.stream().anyMatch(t -> t.getUser_id() == user.getUser_id())) {
                        user.addTag(tag);
                    }
                }
            } else {
                int code = connection.getResponseCode();
                if (code != 200) {
                    System.err.printf("Server returned response code = %s.%nThe last request was ignored", code);
                    System.err.println("Please try again later or it's API bug with symbol '#'. Sometimes API thinks it's %23");
                } else {
                    System.err.println("Server returned empty response.");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
