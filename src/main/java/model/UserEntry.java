package model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class UserEntry {
    private int user_id;
    private String display_name;
    private int reputation;
    private String location;
    private String link;
    private String profile_image;
    private int question_count;
    private int answer_count;
    private Collection<String> tags;

    public Collection<String> getTags() {
        if (tags == null) tags = new HashSet<>();
        return tags;
    }

    public void addTag(String tag) {
        if (tags == null) tags = new HashSet<>();
        tags.add(tag);
    }
}
