package model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Collection;
import java.util.HashSet;

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

    public void addTags(@NonNull Collection<String> tags) {
        if (this.tags == null) {
            this.tags = new HashSet<>(tags);
        } else this.tags.addAll(tags);
    }
}
