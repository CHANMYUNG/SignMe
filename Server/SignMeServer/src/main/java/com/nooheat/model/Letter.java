package com.nooheat.model;

import com.nooheat.support.Category;
import io.vertx.core.json.JsonObject;

/**
 * Created by NooHeat on 23/09/2017.
 */
public class Letter implements Comparable<Letter> {
    String openDate;
    Category type;

    public JsonObject toJson() {
        return null;
    }

    @Override
    public int compareTo(Letter o) {
        return openDate.compareTo(o.openDate) * -1;
    }
}
