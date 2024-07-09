package com.krutov.romashka.co.dao.DB;

import com.google.common.collect.ImmutableList;
import io.micrometer.common.util.StringUtils;
import lombok.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Value
public class SqlFilters {

    SqlParameterSource params;
    String predicate;

    private SqlFilters(SqlParameterSource params, String predicate) {
        this.params = params;
        this.predicate = predicate;
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(predicate);
    }

    public String makeWhereClause() {
        return StringUtils.isNotEmpty(predicate) ? "\nWHERE " + predicate : "";
    }

    public static Builder builder() {
        return new Builder();
    }

    public  static class Builder {
        private final MapSqlParameterSource params = new MapSqlParameterSource();
        private final ImmutableList.Builder<String> predicateBuilder = ImmutableList.builder();

        private Builder() {
        }

        public Builder eq(String columnName, Number value) {
            if (null != value) {
                var paramName = columnName + "Eq";
                predicateBuilder.add(columnName + " = " + param(paramName));
                params.addValue(paramName, value);
            }
            return this;
        }

        public Builder eq(String columnName, Boolean value) {
            if (null != value) {
                var paramName = columnName + "Eq";
                predicateBuilder.add(columnName + " = " + param(paramName));
                params.addValue(paramName, value);
            }
            return this;
        }
        public Builder priceFilter(String columnName, Number value, String filterSign) {
            if (null != value && isNotBlank(filterSign)) {
                if (filterSign.equals("eq")) {
                    eq(columnName, value);
                } else if(filterSign.equals("lt")) {
                    lt(columnName,value);
                } else {
                    gt(columnName, value);
                }
            }
            return this;
        }
        public Builder like(String columnName, String value) {
            if (isNotBlank(value)) {
                var paramName = columnName + "Like";
                predicateBuilder.add(columnName + " LIKE " + param(paramName));
                params.addValue(paramName, "%" + value + "%");
            }
            return this;
        }

        public Builder lt(String columnName, Number value) {
            if (null != value) {
                var paramName = columnName + "Lt";
                predicateBuilder.add(columnName + " < " + param(paramName));
                params.addValue(paramName, value);
            }
            return this;
        }

        public Builder gt(String columnName, Number value) {
            if (null != value) {
                var paramName = columnName + "Gt";
                predicateBuilder.add(columnName + " > " + param(paramName));
                params.addValue(paramName, value);
            }
            return this;
        }


        public Builder isNull(String columnName) {
            predicateBuilder.add(columnName + " IS NULL");
            return this;
        }

        public Builder isNotNull(String columnName) {
            predicateBuilder.add(columnName + " IS NOT NULL");
            return this;
        }

        public SqlFilters build() {
            var predicate = String.join(" AND ", predicateBuilder.build());
            return new SqlFilters(params, predicate);
        }

        private String param(String columnName) {
            return ":" + columnName;
        }
    }
}
