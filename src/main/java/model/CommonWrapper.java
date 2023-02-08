package model;

import lombok.Getter;

import java.util.Collection;

@Getter
public class CommonWrapper<O> {
    private boolean has_more;
    private Collection<O> items;
}
