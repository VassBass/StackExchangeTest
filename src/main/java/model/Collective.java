package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

/**
 * It's wrap element for user tags.Try don't use it.
 * Use {@link util.TagsHelper}
 */
@Data
@AllArgsConstructor
public class Collective {
    private Collection<String> tags;
}
