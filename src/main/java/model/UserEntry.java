package model;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

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
    private Collection<Collectives>collectives;
}
