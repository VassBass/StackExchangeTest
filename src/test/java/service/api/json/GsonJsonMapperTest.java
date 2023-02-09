package service.api.json;

import model.Tag;
import model.UserEntry;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GsonJsonMapperTest {
    private final JsonMapper jsonMapper = GsonJsonMapper.getInstance();

    @Test
    public void testPutUsersFromAPIJson() {
        String json = "{\"items\":[" +
                "{" +
                "\"answer_count\":35550," +
                "\"question_count\":53," +
                "\"reputation\":1384798," +
                "\"user_id\":22656," +
                "\"location\":\"Reading, United Kingdom\"," +
                "\"link\":\"https://stackoverflow.com/users/22656/jon-skeet\"," +
                "\"profile_image\":\"https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=256&d=identicon&r=PG\"," +
                "\"display_name\":\"Jon Skeet\"" +
                "}," +
                "{" +
                "\"answer_count\":85120," +
                "\"question_count\":0," +
                "\"reputation\":1225984," +
                "\"user_id\":1144035," +
                "\"location\":\"New York, United States\"," +
                "\"link\":\"https://stackoverflow.com/users/1144035/gordon-linoff\"," +
                "\"profile_image\":\"https://www.gravatar.com/avatar/e514b017977ebf742a418cac697d8996?s=256&d=identicon&r=PG\"," +
                "\"display_name\":\"Gordon Linoff\"" +
                "}" +
                "]," +
                "\"has_more\": true" +
                "}";
        List<UserEntry> expected = List.of(
                UserEntry.builder()
                        .answer_count(35550)
                        .question_count(53)
                        .reputation(1384798)
                        .user_id(22656)
                        .location("Reading, United Kingdom")
                        .link("https://stackoverflow.com/users/22656/jon-skeet")
                        .profile_image("https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=256&d=identicon&r=PG")
                        .display_name("Jon Skeet")
                        .build(),
                UserEntry.builder()
                        .answer_count(85120)
                        .question_count(0)
                        .reputation(1225984)
                        .user_id(1144035)
                        .location("New York, United States")
                        .link("https://stackoverflow.com/users/1144035/gordon-linoff")
                        .profile_image("https://www.gravatar.com/avatar/e514b017977ebf742a418cac697d8996?s=256&d=identicon&r=PG")
                        .display_name("Gordon Linoff")
                        .build()
        );

        Collection<UserEntry> actual = new ArrayList<>();

        assertTrue(jsonMapper.putUsersFromAPIJson(json, actual));
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void testPutTagsFromAPIJson() {
        String json = "{\"items\":[" +
                "{" +
                "\"user_id\":1," +
                "\"name\":\"c#\"" +
                "}," +
                "{" +
                "\"user_id\":1," +
                "\"name\":\".net\"" +
                "}" +
                "]," +
                "\"has_more\": true" +
                "}";
        List<Tag> expected = List.of(
                new Tag(1, "c#"), new Tag(1, ".net")
        );

        Collection<Tag> actual = new ArrayList<>();

        assertTrue(jsonMapper.putTagsFromAPIJson(json, actual));
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void testObjectToJsonUsers() {
        String json = """
                [
                  {
                    "user_id": 22656,
                    "display_name": "Jon Skeet",
                    "reputation": 1384798,
                    "location": "Reading, United Kingdom",
                    "link": "https://stackoverflow.com/users/22656/jon-skeet",
                    "profile_image": "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=256&d=identicon&r=PG",
                    "question_count": 53,
                    "answer_count": 35550
                  },
                  {
                    "user_id": 1144035,
                    "display_name": "Gordon Linoff",
                    "reputation": 1225984,
                    "location": "New York, United States",
                    "link": "https://stackoverflow.com/users/1144035/gordon-linoff",
                    "profile_image": "https://www.gravatar.com/avatar/e514b017977ebf742a418cac697d8996?s=256&d=identicon&r=PG",
                    "question_count": 0,
                    "answer_count": 85120
                  }
                ]""";
        List<UserEntry> users = List.of(
                UserEntry.builder()
                        .answer_count(35550)
                        .question_count(53)
                        .reputation(1384798)
                        .user_id(22656)
                        .location("Reading, United Kingdom")
                        .link("https://stackoverflow.com/users/22656/jon-skeet")
                        .profile_image("https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=256&d=identicon&r=PG")
                        .display_name("Jon Skeet")
                        .build(),
                UserEntry.builder()
                        .answer_count(85120)
                        .question_count(0)
                        .reputation(1225984)
                        .user_id(1144035)
                        .location("New York, United States")
                        .link("https://stackoverflow.com/users/1144035/gordon-linoff")
                        .profile_image("https://www.gravatar.com/avatar/e514b017977ebf742a418cac697d8996?s=256&d=identicon&r=PG")
                        .display_name("Gordon Linoff")
                        .build()
        );

        assertEquals(json, jsonMapper.objectToJson(users));
    }

    @Test
    public void testObjectToJsonTags() {
        String json = """
                [
                  {
                    "user_id": 1,
                    "name": "c#"
                  },
                  {
                    "user_id": 1,
                    "name": ".net"
                  }
                ]""";
        List<Tag> tags = List.of(
                new Tag(1, "c#"), new Tag(1, ".net")
        );

        assertEquals(json, jsonMapper.objectToJson(tags));
    }

    @Test
    public void testObjectToJsonFile() {
        List<Tag> tags = List.of(
                new Tag(3, "c#"), new Tag(1, ".net")
        );

        jsonMapper.objectToJsonFile(tags, "testJson.json");
    }
}