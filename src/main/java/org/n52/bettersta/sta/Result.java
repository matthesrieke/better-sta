package org.n52.bettersta.sta;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.beans.ConstructorProperties;
import java.util.Objects;

public class Result {
    private static final String FIELD_NAME = "urlPathVariable";
    private final String urlPathVariable;

    @ConstructorProperties({FIELD_NAME})
    public Result(@JsonProperty String urlPathVariable) {
        this.urlPathVariable = urlPathVariable;
    }

    @JsonGetter(FIELD_NAME)
    public String getUrlPathVariable() {
        return urlPathVariable;
    }

    @Override
    public String toString() {
        return String.format("Result{urlPathVariable='%s'}", urlPathVariable);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof Result &&
                             Objects.equals(getUrlPathVariable(), ((Result) o).getUrlPathVariable()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrlPathVariable());
    }
}
