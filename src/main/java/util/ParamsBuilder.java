package util;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class ParamsBuilder {
    private final Map<String, String> params;

    public ParamsBuilder() {
        params = new HashMap<>();
    }

    public ParamsBuilder(@NonNull Map<String, String> params) {
        this.params = params;
    }

    public ParamsBuilder setPageSize(int size) {
        params.put("pagesize", String.valueOf(size));
        return this;
    }

    public ParamsBuilder setOrder(boolean asc) {
        String order = asc ? "asc" : "desc";
        params.put("order", order);
        return this;
    }

    public ParamsBuilder setSort(@NonNull String sortBy) {
        params.put("sort", sortBy);
        return this;
    }

    public ParamsBuilder setSort(@NonNull String sortBy, String minValue, String maxValue) {
        params.put("sort", sortBy);
        if (minValue != null && !minValue.isEmpty()) params.put("min", minValue);
        if (maxValue != null && !maxValue.isEmpty()) params.put("max", maxValue);
        return this;
    }

    public ParamsBuilder setId(int id) {
        params.put("ids", String.valueOf(id));
        return this;
    }

    public ParamsBuilder setIds(int ... ids) {
        params.put("ids", StringHelper.intArrayToString(ids));
        return this;
    }

    public Map<String, String> build() {
        return params;
    }
}
