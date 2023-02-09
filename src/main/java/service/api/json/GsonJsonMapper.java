package service.api.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.NonNull;
import model.CommonWrapper;
import model.Tag;
import model.UserEntry;

import java.lang.reflect.Type;
import java.util.Collection;

public class GsonJsonMapper implements JsonMapper {
    private static volatile GsonJsonMapper instance;
    private GsonJsonMapper(Gson gson) {
        this.gson = gson;
    }
    public static GsonJsonMapper getInstance() {
        if (instance == null) {
            synchronized (GsonJsonMapper.class) {
                if (instance == null) {
                    Gson g = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
                    instance = new GsonJsonMapper(g);
                }
            }
        }
        return instance;
    }

    private final Gson gson;

    @Override
    public boolean putUsersFromAPIJson(@NonNull String json, @NonNull Collection<UserEntry> users) {
        Type dataType = new TypeToken<CommonWrapper<UserEntry>>() {}.getType();
        CommonWrapper<UserEntry> usersWrap = gson.fromJson(json, dataType);
        Collection<UserEntry> result = usersWrap.getItems();

        if (result != null) users.addAll(result);
        return usersWrap.isHas_more();
    }

    @Override
    public boolean putTagsFromAPIJson(@NonNull String json, @NonNull Collection<Tag> tags) {
        Type dataType = new TypeToken<CommonWrapper<Tag>>() {}.getType();
        CommonWrapper<Tag> tagsWrap = gson.fromJson(json, dataType);
        Collection<Tag> result = tagsWrap.getItems();

        if (result != null) tags.addAll(result);
        return tagsWrap.isHas_more();
    }

    @Override
    public String objectToJson(@NonNull Object o) {
        return gson.toJson(o);
    }
}
